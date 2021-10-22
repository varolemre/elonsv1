package com.artnft.artnft.service;

import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.NftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NftService {
    private final NftRepository nftRepository;
    private final UserService userService;

    public Page<Nft> getNfts(Pageable page) {
        return nftRepository.findAll(page);
    }

    @Transactional
    public Nft addNft(Nft nft) {
        User userS = userService.getUserById(1L);
        nft.setUser(userS);
        return nftRepository.save(nft);
    }


    public Optional<Nft> findById(Long nftId) {
        return nftRepository.findById(nftId);
    }

    @Transactional
    public Nft saveNft(Nft nft) {
        return nftRepository.save(nft);
    }
}
