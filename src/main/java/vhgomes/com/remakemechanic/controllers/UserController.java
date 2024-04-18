package vhgomes.com.remakemechanic.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vhgomes.com.remakemechanic.dtos.CreateUserDTO;
import vhgomes.com.remakemechanic.dtos.LoginUserDTO;
import vhgomes.com.remakemechanic.dtos.LoginUserResponseDTO;
import vhgomes.com.remakemechanic.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody CreateUserDTO createUserDTO) {
        return userService.register(createUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> login(@RequestBody LoginUserDTO userLoginDTO) {
        return userService.login(userLoginDTO);
    }

    @GetMapping()
    public ResponseEntity<?> getAllClients() {
        return userService.getAllClients();
    }
}
