package com.artnft.artnft.dto;

import lombok.Data;

@Data
public class TransferRequest {
    Long amount;
    String walletId;
}