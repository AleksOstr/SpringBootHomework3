package ru.geekbrains.springboothomework3.security;

import jakarta.annotation.PostConstruct;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public LibraryUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(username));
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), roles);
    }

    @PostConstruct
    private void posConstruct() {
        Role userRole = new Role("ROLE_USER");
        Role readerRole = new Role("ROLE_READER");
        Role adminRole = new Role("ROLE_ADMIN");

        roleRepository.save(userRole);
        roleRepository.save(readerRole);
        roleRepository.save(adminRole);

        User user = new User("user", "user", List.of(userRole));
        User reader = new User("reader", "reader", List.of(readerRole));
        User admin = new User("admin", "admin", List.of(adminRole, readerRole, userRole));

        userRepository.save(admin);
        userRepository.save(reader);
        userRepository.save(user);
    }
}
