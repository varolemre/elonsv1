package com.artnft.artnft.repository;

import com.artnft.artnft.entity.TransferLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferLogRepository extends JpaRepository<TransferLog, Long> {
}

