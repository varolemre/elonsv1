package com.artnft.artnft.service;

import com.artnft.artnft.dto.TransferRequest;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final UserService userService;
    private final TransferLogService transferLogService;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> sendNft(TransferRequest transferRequest, User user) {
        System.out.println("user amk " + user);
        try {
            User currentUser = userService.getUserById(user.getId());
            if (Objects.equals(currentUser.getId(), user.getId())) {
                Long currentBalance = currentUser.getBalance();
                if (transferRequest.getAmount() > currentBalance) {
                    transferLogService.saveTransferLog(currentUser, transferRequest.getWalletId(), transferRequest.getAmount(), "Bakiye Yetersiz");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't have enough gem!");

                }
                User sendingUser = userService.getUserByWalletId(transferRequest.getWalletId());
                if (sendingUser != null) {
                    currentBalance = currentBalance - transferRequest.getAmount();
                    currentUser.setBalance(currentBalance);
                    Long sendingUserBalance = sendingUser.getBalance();
                    sendingUserBalance = sendingUserBalance + transferRequest.getAmount();
                    sendingUser.setBalance(sendingUserBalance);
                    userRepository.save(currentUser);
                    userRepository.save(sendingUser);
                    transferLogService.saveTransferLog(currentUser, transferRequest.getWalletId(), transferRequest.getAmount(), "Başarılı");
                    return ResponseEntity.ok("OK");
                } else
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error! Bad Credentials...");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error! Bad Credentials...");
        }
    }
}
