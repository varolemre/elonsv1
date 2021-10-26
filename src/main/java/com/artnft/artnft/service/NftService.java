package com.artnft.artnft.service;

import com.artnft.artnft.convert.NftDtoConverter;
import com.artnft.artnft.dto.NftDto;
import com.artnft.artnft.entity.Market;
import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.CollectionRepository;
import com.artnft.artnft.repository.MarketRepository;
import com.artnft.artnft.repository.NftRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NftService {
    private final NftRepository nftRepository;
    private final UserService userService;
    private final CollectionRepository collectionRepository;
    private final MarketRepository marketRepository;
    private final NftDtoConverter nftDtoConverter;

    public Page<Nft> getNfts(Pageable page) {
        return nftRepository.findAll(page);
    }

    @Transactional
    public Nft addNft(Nft nft, Long collectionId) {
        nft.setCollection(collectionRepository.findById(collectionId).get());
        User userS = userService.getUserById(11L);
        nft.setUser(userS);
        System.out.println(nft);
        return nftRepository.save(nft);
    }

    public Optional<Nft> findById(Long nftId) {
        return nftRepository.findById(nftId);
    }


    public NftDto findByIdDto(Long nftId) {
        Optional<Nft> byId = nftRepository.findById(nftId);

        Long floorPriceByNameAndVariant = findFloorPriceByNameAndVariant(byId.get().getName(), byId.get().getQtype());
        NftDto convertedNft = nftDtoConverter.convert(byId.get(), floorPriceByNameAndVariant);
        return convertedNft;
    }

    @Transactional
    public Nft saveNft(Nft nft) {
        return nftRepository.save(nft);
    }


    public Long findFloorPrice(Long marketId) {
        Optional<Market> byId = marketRepository.findById(marketId);
        if (byId.isPresent()) {
            String nftName = byId.get().getNft().getName();
            String qtype = byId.get().getNft().getQtype();
            List<Market> byNftNameAndNftQtype = marketRepository.findByNftNameAndNftQtype(nftName, qtype);
            Optional<Market> min = byNftNameAndNftQtype.stream().min(Comparator.comparing(Market::getAmount));
            if (min.isPresent()) {
                return min.get().getAmount();
            } else {
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
}
