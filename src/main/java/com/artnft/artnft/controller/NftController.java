package com.artnft.artnft.controller;

import com.artnft.artnft.dto.NftDto;
import com.artnft.artnft.dto.NftSaveDto;
import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.response.ApiResponse;
import com.artnft.artnft.service.NftService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/nft")
@RequiredArgsConstructor
public class NftController {
    private final NftService nftService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<ApiResponse> getNftByPage(Pageable page) {
        return ApiResponse.responseOk(nftService.getNfts(page).map(NftDto::new));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addNft(@Valid @RequestBody NftSaveDto nftSaveDto) {
        Nft nft = modelMapper.map(nftSaveDto, Nft.class);
        return ApiResponse.responseCreated(nftService.addNft(nft));
    }
}
