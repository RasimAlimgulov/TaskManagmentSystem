import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "controller_methods")
@RestController
@RequestMapping("/users")
public class Controller {
    @Autowired
    UserService userService;


    @SneakyThrows  ///пробрасыввает исключения дальше
    @Operation( /////
            summary = "Метод getAllUsers",
            description = "Метод использует Service, получает всех user-ов и возвращает их в виде списка"
    )
    @GetMapping("/user")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }



    @PostMapping("/user")
    public User addUser(@RequestBody User user){
        if (user.getUsername()==null || user.getEmail()==null) throw new NoInfoAboutNewUserException();
           else return userService.addUser(user);
    }
}
