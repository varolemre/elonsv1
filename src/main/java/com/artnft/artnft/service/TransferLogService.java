package com.artnft.artnft.service;

import com.artnft.artnft.entity.TransferLog;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.TransferLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransferLogService {
    private final TransferLogRepository transferLogRepository;

    public TransferLog saveTransferLog(User user, String walletId, Long amount, String status) {
        TransferLog transferLog = new TransferLog();
        transferLog.setUser(user);
        transferLog.setStatus(status);
        transferLog.setAmount(amount);
        transferLog.setUtilDate(java.time.LocalDateTime.now());
        Instant inst = Instant.now();
        transferLog.setUtilTime(Date.from(inst));
        transferLog.setWallet(walletId);
        return transferLogRepository.save(transferLog);

    }
}
