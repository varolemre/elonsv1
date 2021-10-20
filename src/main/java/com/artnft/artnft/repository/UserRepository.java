package com.artnft.artnft.repository;

import com.artnft.artnft.dto.NftProjection;
import com.artnft.artnft.dto.UserProjection;
import com.artnft.artnft.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
    Optional<User> findByMail(String mail);

    @Query(value="Select u from User u")
    Page<UserProjection> getAllUserProjection(Pageable page);

    User findBywalletId(String walletId);
}
