package com.artnft.artnft.repository;

import com.artnft.artnft.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection,Long> {
    Collection findByName(String name);
}
