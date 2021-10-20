package com.artnft.artnft.service;

import com.artnft.artnft.CurrentUser;
import com.artnft.artnft.dto.FollowerDto;
import com.artnft.artnft.dto.GenericResponse;
import com.artnft.artnft.dto.UserDto;
import com.artnft.artnft.dto.UserUptadeDto;
import com.artnft.artnft.entity.Followers;
import com.artnft.artnft.entity.Market;
import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.error.NotFoundException;
import com.artnft.artnft.repository.FollowersRepository;
import com.artnft.artnft.repository.MarketRepository;
import com.artnft.artnft.repository.NftRepository;
import com.artnft.artnft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowersRepository followersRepository;
    private final NftRepository nftRepository;
    private final MarketRepository marketRepository;

    public GenericResponse saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setWalletId(UUID.randomUUID().toString());
        userRepository.save(user);
        return new GenericResponse("User Created");
    }

    public User getUsers(Long id) {
       return userRepository.findById(id).orElseThrow();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User getUserByWalletId(String walletId){
        return userRepository.findBywalletId(walletId);
    }



    public User getByUsername(String username) {
        User inDb = userRepository.findByUsername(username);
        if(inDb == null){
            throw new NotFoundException();
        }return  inDb;
    }

    public User uptadeUser(String username, UserUptadeDto userUptadeDto) {
        User inDb = getByUsername(username);
        inDb.setDisplayName(userUptadeDto.getDisplayName());
        return userRepository.save(inDb);
    }

    public ResponseEntity<?> followUser(String username, User user) {
        User byUsername = getByUsername(username);
        List<Followers> followers1 = byUsername.getFollowers();
        List<Followers> collect = followers1.stream().filter(p -> p.getFrom().getId().equals(user.getId())).collect(Collectors.toList());
        System.out.println("COLLECT: "+collect);
        System.out.println("FOLLOWERS"+followers1);
        if(collect.size()>0){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");
        }
        Followers followers = new Followers();
        followers.setFrom(user);
        followers.setTo(byUsername);
        followersRepository.save(followers);
        return ResponseEntity.ok(new GenericResponse("Followed"));

    }

    public List<Followers> getUserFollowers(String username) {
        User byUsername = getByUsername(username);
        List<Followers> followers = byUsername.getFollowers();
        return followers;
     }

     public Long getFollowerNumber(String username){
         User byUsername = getByUsername(username);
         long size = byUsername.getFollowers().size();
         return size;
     }

    public boolean checkFollow(String username, User user) {
        User byUsername = getByUsername(username);
        List<Followers> followers1 = byUsername.getFollowers();
        List<Followers> collect = followers1.stream().filter(p -> p.getFrom().getId().equals(user.getId())).collect(Collectors.toList());
        if(collect.size()>0){
            return false;
        }else
            return true;
    }

    public Long getFollowingNumber(User user){
        List<Followers> following = user.getFollowing();
        long size = following.size();
        return size;
    }

    public List<Nft> getUserNfts(User user) {
       return nftRepository.findByUserId(user.getId());
    }

    public List<Market> getUserNftOnSale(User user) {
      return   marketRepository.findByUserId(user.getId());
    }
}
