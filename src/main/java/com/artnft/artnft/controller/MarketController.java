package com.artnft.artnft.controller;

import com.artnft.artnft.dto.MarketDTO;
import com.artnft.artnft.entity.Market;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.response.ApiResponse;
import com.artnft.artnft.service.MarketService;
import com.artnft.artnft.valitor.annotations.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/market")
public class MarketController {
    private final MarketService marketService;

    @PostMapping("/sell/{nftId}/{amount}")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<ApiResponse> addMarketItem(
            @CurrentUser User user,
            @PathVariable Long nftId,
            @PathVariable Long amount) {
        Market marketItem = marketService.addMarketItem(user, nftId, amount);
        return ApiResponse.responseOk(marketItem);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getMarketItems(
            @Param("page") int page,
            @Param("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.responseOk(marketService.getMarketItems(pageable));
    }

    //Nft adına göre page ile son recordları getir
    @GetMapping("/list/{nftName}")
    public Page<MarketDTO> getMarketItemsByPage(
            @PathVariable String nftName,
            @RequestParam("page") int page,
            @RequestParam(value = "sort", defaultValue = "desc") String sort) {
        Pageable pageable;

        if (sort.equals("desc")) {
            pageable = PageRequest.of(page, 4).withSort(Sort.Direction.DESC, "id");
        } else {
            pageable = PageRequest.of(page, 4).withSort(Sort.Direction.ASC, "id");
        }
        return marketService.getMarketItemsByPage(pageable, nftName).map(MarketDTO::new);
    }

    @GetMapping("/getlist")
    public Page<MarketDTO> getMarketList(
            @RequestParam("page") int page,
            @RequestParam(value = "sort", defaultValue = "desc") String sort) {
        Pageable pageable;
        if (sort.equals("desc")) {
            pageable = PageRequest.of(page, 4).withSort(Sort.Direction.DESC, "id");
        } else {
            pageable = PageRequest.of(page, 4).withSort(Sort.Direction.ASC, "id");
        }
        return marketService.findMarketList(pageable);
    }

    @GetMapping({"/last/{sort}", "/last"})
    public List<MarketDTO> getMarketListLast(Pageable page, @PathVariable(name = "sort", required = false) String sort) {
        return marketService.findMarketListLast(sort);
    }

    @GetMapping({"/{title}/{sort}", "/{title}"})
    public List<MarketDTO> getMarketByTitle(Pageable page, @PathVariable String title, @PathVariable(name = "sort", required = false) String sort) {
        return marketService.findMarketByTitle(title, sort);
    }

    @GetMapping("/details/{marketId}")
    public MarketDTO getNftById(@PathVariable Long marketId) {
        return marketService.getNftById(marketId);
    }

    @GetMapping("/totalsale/{nftName}")
    public Long countNft(@PathVariable String nftName) {
        return marketService.countNft(nftName);
    }

    @GetMapping("/{marketId}/changes")
    public Long getChangedValue(@PathVariable Long marketId) {
        return marketService.getChangedValue(marketId);
    }
}
