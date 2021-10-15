package com.artnft.artnft.controller;

import com.artnft.artnft.CurrentUser;
import com.artnft.artnft.dto.TransferRequest;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public String transfer(@RequestBody TransferRequest transferRequest , @CurrentUser User user){
        return transferService.sendNft(transferRequest,user);
    }
}
