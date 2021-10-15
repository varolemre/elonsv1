package com.artnft.artnft.service;

import com.artnft.artnft.dto.MarketDTO;
import com.artnft.artnft.dto.NftDto;
import com.artnft.artnft.entity.Market;
import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public String addMarketItem(Long userId, Long nftId , Long amount) {
        User currentUser = userService.getUserById(userId);
        Optional<Nft> currentNft = nftService.findById(nftId);
        Long idWhichSellNftUserId = currentNft.get().getUser().getId();

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
        return null;
    }

    public Page<Market> getMarketItems(Pageable page) {
        return marketRepository.findAll((org.springframework.data.domain.Pageable) page);
    }

    public List<MarketDTO> findMarketList(){
        List<MarketDTO> collect = marketRepository.findAll().stream().map(MarketDTO::new).collect(Collectors.toList());

        Collections.sort(collect, Comparator.comparingLong(MarketDTO::getAmount));
        return collect;
    }

    public Page<Market> getMarketItemsByCost(Pageable page) {
        Page<Market> allProductsSortedByName = (Page<Market>) marketRepository.findAll(Sort.by("amount"));
        return allProductsSortedByName;
    }

    public List<MarketDTO> findMarketListLast(){
        List<MarketDTO> collect = marketRepository.findAll().stream().map(MarketDTO::new).collect(Collectors.toList());

        Collections.sort(collect, Comparator.comparing(MarketDTO::getCreatedDate).reversed());
        return collect;
    }
}
