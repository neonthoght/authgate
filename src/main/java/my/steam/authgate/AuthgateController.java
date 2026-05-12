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

    @PostMapping(value="/signup")
    public ResponseEntity<String> signup(@RequestBody UserSMA user) {

        result = gateService.signup(user.username, user.password);
        if (result == 0) {
            response.status(200).body("Пользователь создан!");
        } else {
            response.status(200).body("Пользователь с таким именем уже существует!");
        }

        return response;
    }


}
