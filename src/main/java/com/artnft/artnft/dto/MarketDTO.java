package com.artnft.artnft.dto;

import com.artnft.artnft.entity.Market;
import lombok.Data;

import java.util.Date;

@Data
public class MarketDTO {

    private Long marketId;
    private Long amount;
    private Long nftId;
    private String nftName;
    private int serial;
    private Long userId;
    private String image;
    private String qtype;
    private Date createdDate;
    private Long totalNftSale;
    private Long totalRarity;
    private Long floorPrice;
    private String dropDate;
    private String licence;
    private String listPrice;
    private String owner;

    public MarketDTO(Market market){
        this.setMarketId(market.getId());
        this.setAmount(market.getAmount());
        this.setNftId(market.getNft().getId());
        this.setUserId(market.getUser().getId());
        this.setNftName(market.getNft().getName());
        this.setSerial(market.getNft().getSerial());
        this.setImage(market.getNft().getImage());
        this.setQtype(market.getNft().getQtype());
        this.setCreatedDate(market.getCreatedDate());
        this.setTotalRarity(market.getNft().getTotalRarity());
        this.setDropDate(market.getNft().getDropDate());
        this.setLicence(market.getNft().getLicence());
        this.setListPrice(market.getNft().getListPrice());
        this.setOwner(market.getNft().getUser().getUsername());
    }


    public MarketDTO(Market market,Long totalNftSale,Long floorPrice){
        this.setMarketId(market.getId());
        this.setAmount(market.getAmount());
        this.setNftId(market.getNft().getId());
        this.setUserId(market.getUser().getId());
        this.setNftName(market.getNft().getName());
        this.setSerial(market.getNft().getSerial());
        this.setImage(market.getNft().getImage());
        this.setQtype(market.getNft().getQtype());
        this.setCreatedDate(market.getCreatedDate());
        this.setTotalRarity(market.getNft().getTotalRarity());
        this.setTotalNftSale(totalNftSale);
        this.setFloorPrice(floorPrice);
        this.setDropDate(market.getNft().getDropDate());
        this.setLicence(market.getNft().getLicence());
        this.setListPrice(market.getNft().getListPrice());
        this.setOwner(market.getNft().getUser().getUsername());
    }

    public  MarketDTO(){

    }
}
