package com.artnft.artnft.repository;

import com.artnft.artnft.dto.NftProjection;
import com.artnft.artnft.entity.Market;
import com.artnft.artnft.entity.Nft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    Page<Market> findAllByNftName(String NftName ,Pageable pageable);

    List<Market> findByUserId(Long id);

    List<Market> findByNftName(String title);

    List<Market> findByNftNameAndNftQtype(String NftName, String NftQtype);

}
