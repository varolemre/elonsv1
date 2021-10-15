package com.artnft.artnft.dto;

import lombok.Data;

@Data
public class TransferRequest {
    Long userId;
    Long amount;
    String walletId;

}