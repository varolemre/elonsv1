package com.artnft.artnft.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NftSaveDto {
    @NotNull(message = "Nft Name Can Not Be Empty!")
    private String name;
    @NotNull(message = "Nft Type Not Be Empty!")
    private String qtype;
    @NotNull(message = "Nft Serial Can Not Be Empty!")
    private int serial;
    private String dropDate;
    private String image;
    private Long collection;
    private String licence;
    private Long totalEdition;
    private boolean sellStatus;
}
