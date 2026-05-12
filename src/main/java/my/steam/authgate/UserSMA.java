package my.steam.authgate;
import java.util.Collection;

//import org.jspecify.annotations.Nullable;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
@Table(name="users", schema = "auth")
public class UserSMA{
    @Id
    String id; 

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password; 

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
}
