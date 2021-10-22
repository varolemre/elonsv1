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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
    private final UserService userService;
    private final NftService nftService;
    private final MarketDtoConverter marketDtoConverter;
    private final ChangedRepository changedRepository;


    public String addMarketItem(Long userId, Long nftId, Long amount) {
        User currentUser = userService.getUserById(userId);
        Optional<Nft> currentNft = nftService.findById(nftId);
        if (currentNft.isPresent()) {
            Long idWhichSellNftUserId = currentNft.get().getUser().getId();
            if (Objects.equals(userId, idWhichSellNftUserId) && !currentNft.get().isSellStatus()) {
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
        }
        return "Hata";
    }

    public Page<Market> getMarketItems(Pageable page) {
        return marketRepository.findAll(page);
    }

    public List<MarketDTO> findMarketList() {
        return marketRepository.findAll()
                .stream()
                .map(MarketDTO::new)
                .sorted(Comparator.comparingLong(MarketDTO::getAmount))
                .collect(Collectors.toList());
    }

    //NFT ADIYLA MARKETTEKİLERİ LİSTELEME SON EKLENENLER :)
    public Page<Market> getMarketItemsByPage(Pageable page, String nftName, Long pageNumber) {
        if (pageNumber == null) {
            pageNumber = 0L;
        }
        Pageable pageable = PageRequest.of(pageNumber.intValue(), 4, Sort.Direction.DESC, "id");
        return marketRepository.findAllByNftName(nftName, pageable);
    }

    public List<MarketDTO> findMarketListLast(String sort) {
        return marketRepository.findAll().stream()
                .map(MarketDTO::new)
                .sorted(Comparator.comparing(MarketDTO::getCreatedDate).reversed())
                .collect(Collectors.toList());
    }

    public List<MarketDTO> findMarketByTitle(String title, String sort) {
        List<MarketDTO> collect = marketRepository.findByNftName(title).stream().map(MarketDTO::new).collect(Collectors.toList());
        if (("lowtohigh").equals(sort)) {
            collect.sort(Comparator.comparing(MarketDTO::getAmount));
        } else if (sort.equals("rarity")) {
            collect.sort(Comparator.comparing(MarketDTO::getSerial));
        }
        return collect;
    }

    public Page<MarketDTO> findMarketListLast2(String sort, Pageable pageable) {
        List<MarketDTO> collect = marketRepository.findAll().stream().map(MarketDTO::new).collect(Collectors.toList());
        Page<MarketDTO> pagelist = new PageImpl<>(collect, pageable, collect.size());
        collect.sort(Comparator.comparing(MarketDTO::getCreatedDate).reversed());

        return pagelist;
    }


    public MarketDTO getNftById(Long marketId) {
        Market byId = marketRepository.getById(marketId);
        Long totalNft = countNft(byId.getNft().getName());
        Long floorPrice = findFloorPrice(marketId);
        return marketDtoConverter.convert(byId, totalNft, floorPrice);
    }

    public Long countNft(String nftName) {
        return (long) marketRepository.findByNftName(nftName).size();
    }

    public Long findFloorPrice(Long marketId) {
        Optional<Market> byId = marketRepository.findById(marketId);
        if (byId.isPresent()) {
            String nftName = byId.get().getNft().getName();
            String qtype = byId.get().getNft().getQtype();
            List<Market> byNftNameAndNftQtype = marketRepository.findByNftNameAndNftQtype(nftName, qtype);
            Optional<Market> min = byNftNameAndNftQtype.stream().min(Comparator.comparing(Market::getAmount));
            if(min.isPresent()){
                return min.get().getAmount();
            }else {
                //todo
            }
        }
        return 0L; //todo
    }

    public Long findFloorPriceByNameAndVariant(String nftName, String qtype) {
        List<Market> byNftNameAndNftQtype = marketRepository.findByNftNameAndNftQtype(nftName, qtype);
        Optional<Market> min = byNftNameAndNftQtype.stream().min(Comparator.comparing(Market::getAmount));
        if (min.isEmpty()) {
            return 0L;
        }
        return min.get().getAmount();

    }

    public List<Market> findByNftNameAndVariant(String nftName, String variant) {
        return marketRepository.findByNftNameAndNftQtype(nftName, variant);
    }

    public Long getChangedValue(Long marketId) {
        Optional<Market> byId = marketRepository.findById(marketId);
        if (byId.isPresent()) {
            String nftName = byId.get().getNft().getName();
            String variant = byId.get().getNft().getQtype();
            Changed byNftNameAndVariant = changedRepository.findByNftNameAndVariant(nftName, variant);
            return byNftNameAndVariant.getChangedValue();
        }
        return 0L; //todo
    }


}
