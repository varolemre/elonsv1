package com.artnft.artnft.service;

import com.artnft.artnft.entity.Collection;
import com.artnft.artnft.entity.Market;
import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.repository.CollectionRepository;
import com.artnft.artnft.repository.MarketRepository;
import com.artnft.artnft.repository.NftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final MarketRepository marketRepository;
    private final NftRepository nftRepository;


    public Collection addCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    public Collection getCollection(String name) {
        return collectionRepository.findByName(name);
    }

    public Collection getCollectionById(Long id){
        Optional<Market> byId = marketRepository.findById(id);
        Long collectionId = byId.get().getNft().getCollection().getId();
        return collectionRepository.findById(collectionId).get();
    }

    public Collection getCollectionBynftId(Long nftId) {
        Optional<Nft> byId = nftRepository.findById(nftId);
        Long collectionId = byId.get().getCollection().getId();
        return collectionRepository.findById(collectionId).get();
    }
}
