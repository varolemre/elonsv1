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

    //Nft adına göre page ile son recordları getir
    @GetMapping({"/market/list/{nftName}","/market/list/{nftName}/{pageN}"})
    public Page<MarketDTO> getMarketItemsByPage(Pageable page, @PathVariable String nftName,@PathVariable(name="pageN", required = false) Long pageN){
        return  marketService.getMarketItemsByPage(page,nftName,pageN).map(MarketDTO::new);
    }

    @GetMapping("/market/getlist")
    public List<MarketDTO> getMarketList(Pageable page){
        return  marketService.findMarketList();
    }

    @GetMapping({"/market/last/{sort}","/market/last"})
    public List<MarketDTO> getMarketListLast(Pageable page,@PathVariable(name="sort",required = false) String sort){
        return  marketService.findMarketListLast(sort);
    }

    @GetMapping({"/market2/last/{sort}","/market2/last"})
    public Page<MarketDTO> getMarketListLast2(Pageable page,@PathVariable(name="sort",required = false) String sort){
        return  marketService.findMarketListLast2(sort,page);
    }


    @GetMapping({"/market/{title}/{sort}","/market/{title}"})
    public List<MarketDTO> getMarketByTitle(Pageable page, @PathVariable String title,@PathVariable(name="sort",required = false) String sort){
        return marketService.findMarketByTitle(title,sort);
    }


    @GetMapping("/market/details/{marketId}")
    public MarketDTO getNftById(@PathVariable Long marketId){
        return marketService.getNftById(marketId);
    }

    @GetMapping("/market/totalsale/{nftName}")
    public Long countNft(@PathVariable String nftName){
        return marketService.countNft(nftName);
    }

    @GetMapping("/market/{marketId}/changes")
    public Long getChangedValue(@PathVariable Long marketId){
        return marketService.getChangedValue(marketId);
    }
}
