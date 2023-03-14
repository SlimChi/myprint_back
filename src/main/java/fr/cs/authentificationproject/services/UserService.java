package fr.cs.authentificationproject.services;


import fr.cs.authentificationproject.auth.AuthenticationRequest;
import fr.cs.authentificationproject.auth.AuthenticationResponse;
import fr.cs.authentificationproject.dto.UserDto;
import fr.cs.authentificationproject.entities.User;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;


public interface UserService {


    void delete(Integer id);

    @Transactional
    List<UserDto> findAll();

    UserDto findById(Integer id);

    List<User> getUsers();


    Optional<User> getUserById(Integer id);

    @Transactional
    void updateUser(Integer id, String firstName, String lastName, String email, String password);

}
