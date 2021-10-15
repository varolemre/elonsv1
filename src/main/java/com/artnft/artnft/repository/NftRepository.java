package com.artnft.artnft.repository;

import com.artnft.artnft.dto.NftProjection;
import com.artnft.artnft.entity.Nft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NftRepository extends JpaRepository<Nft, Long> {

    @Query(value="Select n from Nft n")
    Page<NftProjection> getAllNftsProjection(Pageable page);
}
