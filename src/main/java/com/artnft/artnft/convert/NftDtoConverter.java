package com.artnft.artnft.convert;

import com.artnft.artnft.dto.NftDto;
import com.artnft.artnft.entity.Nft;
import org.springframework.stereotype.Service;

@Service
public class NftDtoConverter {

    public NftDto convert(Nft nft, Long floorPrice) {
        NftDto nftDto = new NftDto();
        nftDto.setName(nft.getName());
        nftDto.setImage(nft.getImage());
        nftDto.setSerial(nft.getSerial());
        nftDto.setQtype(nft.getQtype());
        nftDto.setFloorPrice(floorPrice);
        return nftDto;
    }
}
