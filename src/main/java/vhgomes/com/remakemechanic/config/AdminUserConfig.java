package vhgomes.com.remakemechanic.config;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vhgomes.com.remakemechanic.models.Role;
import vhgomes.com.remakemechanic.models.User;
import vhgomes.com.remakemechanic.repositories.RoleRepository;
import vhgomes.com.remakemechanic.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());
        var userAdmin = userRepository.findByUsername("admin");
        var roleClient = roleRepository.findByName(Role.Values.CLIENT.name());

        userAdmin.ifPresentOrElse(
                (user) -> System.out.println("Admin já existe"),
                () -> {
                    var user = new User();
                    user.setUsername("admin");
                    user.setPassword(bCryptPasswordEncoder.encode("123456"));
                    user.setRole(Set.of(roleAdmin));
                    user.setName("Victor Hugo Cláudio Gomes");
                    user.setCpf("001555555555");
                    user.setTelephone("63999992222");
                    userRepository.save(user);
                }
        );

        List<User> users = new ArrayList<>();
        users.add(createUser("user1", "password1", "User One", "00111111111", "1111111111", Set.of(roleClient)));
        users.add(createUser("user2", "password2", "User Two", "00222222222", "2222222222", Set.of(roleClient)));
        users.add(createUser("user3", "password3", "User Three", "00333333333", "3333333333", Set.of(roleClient)));

        userRepository.saveAll(users);
        System.out.println("Usuários criados");
    }

    private User createUser(String username, String password, String name, String cpf, String telephone, Set<Role> roles) {
        var user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole(roles);
        user.setName(name);
        user.setCpf(cpf);
        user.setTelephone(telephone);
        return user;
    }
}