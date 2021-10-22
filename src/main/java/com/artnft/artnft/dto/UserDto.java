package com.artnft.artnft.dto;

import com.artnft.artnft.entity.User;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String mail;
    private String image;
    private String wallet;
    private String displayName;
    private Long balance;


    public UserDto(User user) {
        this.setUsername(user.getUsername());
        this.setMail(user.getMail());
        this.setImage(user.getImage());
        this.setWallet(user.getWalletId());
        this.setDisplayName(user.getDisplayName());
        this.setBalance(user.getBalance());
    }

}
