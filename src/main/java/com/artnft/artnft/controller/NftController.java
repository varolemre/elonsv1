package com.artnft.artnft.controller;

import com.artnft.artnft.dto.NftDto;
import com.artnft.artnft.dto.NftProjection;
import com.artnft.artnft.dto.Views;
import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.service.NftService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/nft")
@RequiredArgsConstructor
public class NftController {

    private final NftService nftService;


    @GetMapping
    public Page<NftDto> getNfts(Pageable page){
       return  nftService.getNfts(page).map(NftDto::new);
    }

    @PostMapping
    public Nft addNft(@Valid @RequestBody Nft nft){
        return nftService.addNft(nft);
    }
}
