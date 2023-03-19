package fr.cs.authentificationproject.services.implement;

import fr.cs.authentificationproject.auth.AuthenticationRequest;
import fr.cs.authentificationproject.auth.AuthenticationResponse;
import fr.cs.authentificationproject.config.JwtService;
import fr.cs.authentificationproject.dto.UserDto;
import fr.cs.authentificationproject.entities.Adresse;
import fr.cs.authentificationproject.entities.Role;
import fr.cs.authentificationproject.entities.User;
import fr.cs.authentificationproject.repositories.AdresseRepository;
import fr.cs.authentificationproject.repositories.UserRepository;
import fr.cs.authentificationproject.services.UserService;
import fr.cs.authentificationproject.token.ConfirmationToken;
import fr.cs.authentificationproject.token.ConfirmationTokenService;
import fr.cs.authentificationproject.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author slimane
 * @Project authentification
 */

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user was found with the provided ID : " + id));
    }


    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Integer id){

        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void updateUser(Integer id, String firstName,String lastName,String email,String password){

        User updateUser = userRepository.findById(id).orElseThrow();

        updateUser.setId(id);
        updateUser.setEmail(email);
        updateUser.setFirstName(firstName);
        updateUser.setLastName(lastName);
        updateUser.setPassword(passwordEncoder.encode(password));

    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }
}
