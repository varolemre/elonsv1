package com.artnft.artnft.entity;

import com.artnft.artnft.dto.Views;
import com.artnft.artnft.valitor.annotations.UniqueMail;
import com.artnft.artnft.valitor.annotations.UniqueUsername;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class User extends BaseEntity implements UserDetails  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Mail Can Not Be Empty!")
    @Size(min=4, max=50)
    @UniqueMail
    @JsonView(Views.Base.class)
    private String mail;

    @NotNull(message = "Username Can Not Be Empty!")
    @Size(min=4, max=44)
    @UniqueUsername
    @JsonView(Views.Base.class)
    private String username;

    @NotNull(message = "Display Name Can Not Be Empty!")
    @Size(min=1, max=55)
    @JsonView(Views.Base.class)
    private String displayName;

    @Size(min=1, max=55)
    private String refCode;

    @NotNull
    @Size(min = 8,max=50)
    @JsonIgnore
    private String password;

    @JsonView(Views.Base.class)
    private Long balance;

    @JsonView(Views.Base.class)
    private String image;

    private String walletId;

    @OneToMany(fetch = FetchType.EAGER,mappedBy="to")
    @JsonIgnore
    private List<Followers> followers;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy="from",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Followers> following;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
