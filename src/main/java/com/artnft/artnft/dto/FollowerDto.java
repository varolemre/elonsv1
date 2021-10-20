package com.artnft.artnft.dto;

import lombok.Data;

@Data
public class FollowerDto {

    private UserDto to;

    private UserDto from;
}
