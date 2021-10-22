package com.artnft.artnft.repository;

import com.artnft.artnft.entity.Changed;
import com.artnft.artnft.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChangedRepository extends JpaRepository<Changed,Long> {


    Changed findByNftNameAndVariant(String NftName, String Variant);
}
