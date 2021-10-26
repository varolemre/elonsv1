package com.artnft.artnft.repository;

import com.artnft.artnft.entity.Changed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangedRepository extends JpaRepository<Changed, Long> {
    Changed findByNftNameAndVariant(String name, String variant);
}
