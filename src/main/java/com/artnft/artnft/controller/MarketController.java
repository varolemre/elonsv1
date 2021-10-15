package com.artnft.artnft.controller;

import com.artnft.artnft.dto.MarketDTO;
import com.artnft.artnft.entity.Market;
import com.artnft.artnft.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;

    @PostMapping("/sell/{userId}/{nftId}/{amount}")
    public String addMarketItem(@PathVariable Long userId, @PathVariable Long nftId, @PathVariable Long amount){
        return marketService.addMarketItem(userId,nftId,amount);
    }

    @GetMapping("/market")
    public Page<MarketDTO> getMarketItems(Pageable page){
        return marketService.getMarketItems(page).map(MarketDTO::new);
    }

    @GetMapping("/market/sbp")
    public Page<MarketDTO> getMarketItemsByCost(Pageable page){
        return  marketService.getMarketItemsByCost(page).map(MarketDTO::new);
    }

    @GetMapping("/market/getlist")
    public List<MarketDTO> getMarketList(Pageable page){
        return  marketService.findMarketList();
    }

    @GetMapping("/market/last")
    public List<MarketDTO> getMarketListLast(Pageable page){
        return  marketService.findMarketListLast();
    }
}
