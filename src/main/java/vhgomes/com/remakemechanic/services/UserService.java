package vhgomes.com.remakemechanic.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vhgomes.com.remakemechanic.dtos.*;
import vhgomes.com.remakemechanic.models.Role;
import vhgomes.com.remakemechanic.models.User;
import vhgomes.com.remakemechanic.repositories.RoleRepository;
import vhgomes.com.remakemechanic.repositories.UserRepository;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtEncoder jwtEncoder;

    public UserService(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtEncoder jwtEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public ResponseEntity<Void> register(CreateUserDTO createUserDTO) {
        var basicRole = roleRepository.findByName(Role.Values.CLIENT.name());
        var userExists = userRepository.findByUsername(createUserDTO.username());
        var cpfUniqueUser = userRepository.findByCpf(createUserDTO.cpf());

        if (userExists.isPresent() || cpfUniqueUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var createdUser = new User();
        createdUser.setName(createUserDTO.name());
        createdUser.setUsername(createUserDTO.username());
        createdUser.setPassword(bCryptPasswordEncoder.encode(createUserDTO.password()));
        createdUser.setTelephone(createUserDTO.telephone());
        createdUser.setCpf(createUserDTO.cpf());
        createdUser.setRole(Set.of(basicRole));

        userRepository.save(createdUser);

        return ResponseEntity.ok().build();

    }

    public ResponseEntity<LoginUserResponseDTO> login(LoginUserDTO userLoginDTO) {
        var user = userRepository.findByUsername(userLoginDTO.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(userLoginDTO, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("user or password is invalid");
        }

        var now = Instant.now();
        var expiresIn = 3600L;
        var scopes = user.get().getRole().stream().map(Role::getName).collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("backend")
                .subject(user.get().getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginUserResponseDTO(jwtValue, expiresIn));
    }

    public ResponseEntity<Stream<UserResponseDTO>> getAllUsersByRole(Long roleId) {
        var role = roleRepository.findById(roleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var users = userRepository.findUsersByRoleEquals(Set.of(role)).stream().map(
                user -> new UserResponseDTO(
                        user.getUsername(),
                        user.getName(),
                        user.getTelephone(),
                        user.getCpf(),
                        user.getUserId()
                )
        );

        return ResponseEntity.ok(users);
    }
}
