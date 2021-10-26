package com.artnft.artnft.dto;

import com.artnft.artnft.entity.Nft;
import lombok.Data;

@Data
public class NftDto {
    private String name;
    private String qtype;
    private int serial;
    private String image;
    private Long floorPrice;


    public NftDto(Nft nft){
        this.setName(nft.getName());
        this.setQtype(nft.getQtype());
        this.setSerial(nft.getSerial());
        this.setImage(nft.getImage());
    }

    public NftDto(Nft nft, Long floorPrice){
        this.setName(nft.getName());
        this.setQtype(nft.getQtype());
        this.setSerial(nft.getSerial());
        this.setImage(nft.getImage());
        this.setFloorPrice(floorPrice);
    }

    public NftDto(){

    }

}
