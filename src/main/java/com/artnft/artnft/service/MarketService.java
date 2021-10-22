package com.artnft.artnft.service;

import com.artnft.artnft.convert.MarketDtoConverter;
import com.artnft.artnft.dto.MarketDTO;
import com.artnft.artnft.entity.Changed;
import com.artnft.artnft.entity.Market;
import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.ChangedRepository;
import com.artnft.artnft.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
    private final  UserService userService;
    private final NftService nftService;
    private final MarketDtoConverter marketDtoConverter;
    private final ChangedRepository changedRepository;



    public String addMarketItem(Long userId, Long nftId , Long amount) {
        User currentUser = userService.getUserById(userId);
        Optional<Nft> currentNft = nftService.findById(nftId);
        Long idWhichSellNftUserId = currentNft.get().getUser().getId();
        System.out.println(idWhichSellNftUserId);
        System.out.println(userId);
        System.out.println(currentNft);
        if(userId==idWhichSellNftUserId && currentNft.get().isSellStatus()==false){
            Market market = new Market();
            currentNft.get().setSellStatus(true);
            nftService.saveNft(currentNft.get());
            market.setUser(currentUser);
            market.setNft(currentNft.get());
            market.setAmount(amount);
            market.setSerial(currentNft.get().getSerial());
            marketRepository.save(market);
            return "market";
        }
        return "Hata";
    }

    public Page<Market> getMarketItems(Pageable page) {
        return marketRepository.findAll((org.springframework.data.domain.Pageable) page);
    }

    public List<MarketDTO> findMarketList(){
        List<MarketDTO> collect = marketRepository.findAll().stream().map(MarketDTO::new).collect(Collectors.toList());

        Collections.sort(collect, Comparator.comparingLong(MarketDTO::getAmount));
        return collect;
    }

    //NFT ADIYLA MARKETTEKİLERİ LİSTELEME SON EKLENENLER :)
    public Page<Market> getMarketItemsByPage(Pageable page,String nftName,Long pageNumber) {
        System.out.println("ilk"+pageNumber);
        if(pageNumber==null){
            pageNumber=0l;
        }
        System.out.println(pageNumber);
        Pageable pageable =  PageRequest.of(pageNumber.intValue(), 4, Sort.Direction.DESC, "id");
        Page<Market> allNftsSortedByName = marketRepository.findAllByNftName(nftName,pageable);
        return allNftsSortedByName;
    }

    public List<MarketDTO> findMarketListLast(String sort){
        List<MarketDTO> collect = marketRepository.findAll().stream().map(MarketDTO::new).collect(Collectors.toList());

        Collections.sort(collect, Comparator.comparing(MarketDTO::getCreatedDate).reversed());

        return collect;
    }

    public List<MarketDTO> findMarketByTitle(String title,String sort) {
        List<MarketDTO> collect = marketRepository.findByNftName(title).stream().map(MarketDTO::new).collect(Collectors.toList());
        if(("lowtohigh").equals(sort)){
            Collections.sort(collect, Comparator.comparing(MarketDTO::getAmount));
        }else if(sort.equals("rarity")){
            Collections.sort(collect, Comparator.comparing(MarketDTO::getSerial));
        }
        return collect;
    }

    public Page<MarketDTO> findMarketListLast2(String sort,Pageable pageable) {
        List<MarketDTO> collect = marketRepository.findAll().stream().map(MarketDTO::new).collect(Collectors.toList());
        Page<MarketDTO> pagelist = new PageImpl<MarketDTO>(collect,pageable ,collect.size() );
        Collections.sort(collect, Comparator.comparing(MarketDTO::getCreatedDate).reversed());

        return pagelist;
    }




    public MarketDTO getNftById(Long marketId) {
        Market byId = marketRepository.getById(marketId);
        Long totalNft = countNft(byId.getNft().getName());
        Long floorPrice = findFloorPrice(marketId);
        MarketDTO convertedMarket = marketDtoConverter.convert(byId,totalNft,floorPrice);

        return convertedMarket;
    }

    public Long countNft(String nftName) {
        long size = marketRepository.findByNftName(nftName).size();
        return size;
    }

    public Long findFloorPrice(Long marketId){
        Optional<Market> byId = marketRepository.findById(marketId);
        String nftName = byId.get().getNft().getName();
        String qtype = byId.get().getNft().getQtype();
        List<Market> byNftNameAndNftQtype = marketRepository.findByNftNameAndNftQtype(nftName, qtype);
        Optional<Market> min = byNftNameAndNftQtype.stream().min(Comparator.comparing(Market::getAmount));
        return min.get().getAmount();

    }

    public Long findFloorPriceByNameAndVariant(String nftName, String qtype){
        List<Market> byNftNameAndNftQtype = marketRepository.findByNftNameAndNftQtype(nftName, qtype);
        Optional<Market> min = byNftNameAndNftQtype.stream().min(Comparator.comparing(Market::getAmount));
        if(min.isEmpty()){
            return 0L;
        }
        return min.get().getAmount();

    }

    public List<Market> findByNftNameAndVariant(String nftName,String variant){
        List<Market> byNftNameAndNftQtype = marketRepository.findByNftNameAndNftQtype(nftName, variant);
        return byNftNameAndNftQtype;
    }

    public Long getChangedValue(Long marketId) {
        Optional<Market> byId = marketRepository.findById(marketId);
        String nftName = byId.get().getNft().getName();
        String variant = byId.get().getNft().getQtype();
        Changed byNftNameAndVariant = changedRepository.findByNftNameAndVariant(nftName, variant);
        Long changedValue = byNftNameAndVariant.getChangedValue();
        System.out.println(changedValue);
        return changedValue;
    }


}
