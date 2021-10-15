package com.artnft.artnft.dto;

import com.artnft.artnft.entity.Market;
import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class MarketDTO {

    private Long amount;

    private Long nftId;

    private String nftName;

    private int serial;

    private Long userId;

    private String image;

    private String qtype;

    private Date createdDate;


    public MarketDTO(Market market){
        this.setAmount(market.getAmount());
        this.setNftId(market.getNft().getId());
        this.setUserId(market.getUser().getId());
        this.setNftName(market.getNft().getName());
        this.setSerial(market.getNft().getSerial());
        this.setImage(market.getNft().getImage());
        this.setQtype(market.getNft().getQtype());
        this.setCreatedDate(market.getCreatedDate());
    }
}
