package com.artnft.artnft.service;

import com.artnft.artnft.entity.Trade;
import com.artnft.artnft.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;

    public Trade addTrade(Long userId, Long price, String nftName) {
        Trade trade = new Trade();
        trade.setNftName(nftName);
        trade.setPrice(price);
        trade.setUserId(userId);
        return tradeRepository.save(trade);
    }

    public List<Trade> findTradeByNftName(String nftName) {
        List<Trade> collect = tradeRepository.findByNftName(nftName);
        collect.subList(Math.max(collect.size() - 5, 0), collect.size());
        return collect;
    }

}
