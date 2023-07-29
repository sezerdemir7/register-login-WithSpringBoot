package security.registerlogin.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import security.registerlogin.dto.UserDto;
import security.registerlogin.entity.User;
import security.registerlogin.service.UserService;

import java.util.List;


@Controller
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    @GetMapping("/index")
    public String Home(){
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user=new UserDto();
        model.addAttribute("user",user);
        return "register";
    }



    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){

        User existingUser=userService.findUserByEmail(userDto.getEmail());

        if(existingUser!=null&& existingUser.getEmail() !=null && !existingUser.getEmail().isEmpty()){

            result.rejectValue("email",null,
                    "There is already an account with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user",userDto);
            return "register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users=userService.findAllUsers();
        model.addAttribute("users",users);
        return "users";
    }


}
