package vhgomes.com.remakemechanic.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import vhgomes.com.remakemechanic.dtos.*;
import vhgomes.com.remakemechanic.services.UserService;


import java.util.stream.Stream;

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

    @GetMapping("/all-users-by-role/{roleId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Stream<UserResponseDTO>> getAllUserByRole(@PathVariable Long roleId) {
        return userService.getAllUsersByRole(roleId);
    }

    @GetMapping("/all-vehicles-by-user")
    public ResponseEntity<?> getAllVehicleRegistredBy(JwtAuthenticationToken jwtAuthenticationToken) {
        return userService.getAllVehicleRegistredBy(jwtAuthenticationToken);
    }
}
