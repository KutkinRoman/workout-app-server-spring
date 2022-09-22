package workout.server.security.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import workout.server.security.constans.RoleName;
import workout.server.security.entity.inter.AppUser;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class AppUserImpl implements AppUser, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created;

    @ManyToMany
    @JoinTable
    private Collection<UserRole> roles;

    transient Collection<? extends GrantedAuthority> authorities;

    public AppUserImpl () {
    }

    public AppUserImpl (Long id, String username, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        if (authorities == null) {
            this.authorities = this.roles
                    .stream ()
                    .map (AppUserImpl::mapRoleToAuthority)
                    .collect (Collectors.toList ());
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    @Override
    public boolean isEnabled () {
        return true;
    }

    @Override
    public boolean containsRole (RoleName roleName) {
        if (authorities != null && !authorities.isEmpty ()) {
            return authorities
                    .stream ()
                    .map (Objects::toString)
                    .collect (Collectors.toList ())
                    .contains (roleName.toString ());
        }
        if (roles != null && !roles.isEmpty ()) {
            return roles
                    .stream ()
                    .map (UserRole::getName)
                    .collect (Collectors.toList ())
                    .contains (roleName);
        }
        return false;
    }

    public static SimpleGrantedAuthority mapRoleToAuthority (UserRole role) {
        return new SimpleGrantedAuthority (role.getName ().toString ());
    }

}
