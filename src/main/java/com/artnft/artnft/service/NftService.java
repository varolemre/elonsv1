package com.artnft.artnft.service;

import com.artnft.artnft.dto.NftProjection;
import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.NftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NftService {
    private final NftRepository nftRepository;
    private final UserService userService;

    public Page<Nft> getNfts(Pageable page) {
        return nftRepository.findAll((org.springframework.data.domain.Pageable) page);
    }

    public Nft addNft(Nft nft) {
        User userS = userService.getUserById(1L);
        nft.setUser(userS);
        System.out.println("nft"+nft);
        return nftRepository.save(nft);
    }


    public Optional<Nft> findById(Long nftId) {
        return nftRepository.findById(nftId);
    }

    public Nft saveNft(Nft nft) {
        return nftRepository.save(nft);
    }
}
