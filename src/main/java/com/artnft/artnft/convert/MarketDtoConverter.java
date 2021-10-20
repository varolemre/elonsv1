package com.artnft.artnft.convert;

import com.artnft.artnft.dto.MarketDTO;
import com.artnft.artnft.entity.Market;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MarketDtoConverter {

    public MarketDTO convert(Market market,Long totalNft,Long floorPrice){
        MarketDTO marketDTO = new MarketDTO();
        marketDTO.setMarketId(market.getId());
        marketDTO.setUserId(market.getUser().getId());
        marketDTO.setNftName(market.getNft().getName());
        marketDTO.setNftId(market.getNft().getId());
        marketDTO.setAmount(market.getAmount());
        marketDTO.setImage(market.getNft().getImage());
        marketDTO.setQtype(market.getNft().getQtype());
        marketDTO.setCreatedDate(market.getCreatedDate());
        marketDTO.setSerial(market.getSerial());
        marketDTO.setTotalNftSale(totalNft);
        marketDTO.setTotalEdition(market.getNft().getTotalEdition());
        marketDTO.setFloorPrice(floorPrice);
        return marketDTO;
    }
}

