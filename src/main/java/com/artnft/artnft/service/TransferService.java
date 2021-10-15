package com.artnft.artnft.service;

import com.artnft.artnft.dto.TransferRequest;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final UserService userService;
    private final NftService nftService;
    private final TransferLogService transferLogService;
    private final UserRepository userRepository;

    public String sendNft(TransferRequest transferRequest, User user){
        User currentUser = userService.getUserById(transferRequest.getUserId());
        if(currentUser.getId() == user.getId()){
            Long currentBalance = currentUser.getBalance();
            if(transferRequest.getAmount()>currentBalance){
                transferLogService.saveTransferLog(currentUser,transferRequest.getWalletId(),transferRequest.getAmount(),"Bakiye Yetersiz");
                return "Hata E686";
            }
            currentBalance= currentBalance-transferRequest.getAmount();
            currentUser.setBalance(currentBalance);
            User sendingUser = userService.getUserByWalletId(transferRequest.getWalletId());
            Long sendingUserBalance = sendingUser.getBalance();
            sendingUserBalance=sendingUserBalance+transferRequest.getAmount();
            sendingUser.setBalance(sendingUserBalance);
            userRepository.save(currentUser);
            userRepository.save(sendingUser);
            transferLogService.saveTransferLog(currentUser,transferRequest.getWalletId(),transferRequest.getAmount(),"Başarılı");
            return "Başarılı";
        }
        return "Hata E401";
    }
}
