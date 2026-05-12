package my.steam.authgate;
import my.steam.authgate.AuthgateService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import my.steam.authgate.UserSMA;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;


@RestController
//@Controller
@RequestMapping(value="/auth")
public class AuthgateController {

    AuthgateService gateService;
    int result = 0;
    ResponseEntity<String> response;
    UserSMA user;

    public AuthgateController(AuthgateService gateService) {this.gateService = gateService; }

    @PostMapping(value="/signup")
    public ResponseEntity<String> signup(@RequestBody UserSMA user) {
        System.out.println(user.username + user.password);
        result = gateService.signup(user.username, user.password);
        if (result == 0) {
            response = ResponseEntity.status(200).body("user created!");
        } else {
            response = ResponseEntity.status(200).body("user already exists!");
            System.out.println(response);
        }

        return response;
    }


}
