package my.steam.authgate;
import java.util.Collection;

//import org.jspecify.annotations.Nullable;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.util.UUID;


@Entity
@Table(name="users", schema = "auth")
public class UserSMA{ // user of steam market analisys system 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id; 

    
    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password; 

    @Column(name = "email")
    String email; 

    @Column(name = "is_active")
    boolean isActive;

    //используется для подтверждения email при регистрации (uuid). Испоьзовать для разных целей связанных с токеном
    @Column(name = "token")
    UUID token;

    //default constructor. Не удалять!
    UserSMA() {}

    public UserSMA(String username, String password, String email, UUID token) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
    }

    String getId() {
        return this.id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getUsername() {
        return this.username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getPassword() {
        return this.password;
    }

    void setPassword( String password) {
        this.password = password;
    }

    String getEmail() {
        return this.email;
    }

    void setEmail(String email) {
        this.email = email;
    }

        UUID getToken() {
        return this.token;
    }

    void setToken(UUID token) {
        this.token = token;
    }

    boolean getIsActive() {
        return this.isActive;
    }

    void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
