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
import vhgomes.com.remakemechanic.dtos.ClientsResponseDTO;
import vhgomes.com.remakemechanic.dtos.CreateUserDTO;
import vhgomes.com.remakemechanic.dtos.LoginUserDTO;
import vhgomes.com.remakemechanic.dtos.LoginUserResponseDTO;
import vhgomes.com.remakemechanic.models.Role;
import vhgomes.com.remakemechanic.models.User;
import vhgomes.com.remakemechanic.repositories.RoleRepository;
import vhgomes.com.remakemechanic.repositories.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.projection.EntityProjection.ProjectionType.DTO;

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

    public ResponseEntity<?> getAllClients() {
        var basicRole = roleRepository.findByName(Role.Values.CLIENT.name());
        var users = userRepository.findUsersByRoleEquals(Set.of(basicRole)).stream().map(
                user -> new ClientsResponseDTO(
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
