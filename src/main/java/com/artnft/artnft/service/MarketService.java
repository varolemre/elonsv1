package com.artnft.artnft.service;

import com.artnft.artnft.convert.MarketDtoConverter;
import com.artnft.artnft.dto.MarketDTO;
import com.artnft.artnft.dto.NftDto;
import com.artnft.artnft.entity.Market;
import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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

    public Page<Market> getMarketItemsByPage(Pageable page,String nftName) {
        Page<Market> allProductsSortedByName = marketRepository.findAllByNftName(nftName,page);
        return allProductsSortedByName;
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
        MarketDTO convertedMarket = marketDtoConverter.convert(byId);
        return convertedMarket;
    }
}
