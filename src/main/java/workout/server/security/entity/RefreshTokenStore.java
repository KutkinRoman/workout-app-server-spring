package workout.server.security.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class RefreshTokenStore {

    @Id
    private String username;

    private String tokenId;

}
