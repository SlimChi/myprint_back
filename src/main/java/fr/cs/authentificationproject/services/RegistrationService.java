package fr.cs.authentificationproject.services;

import fr.cs.authentificationproject.auth.AuthenticationResponse;
import fr.cs.authentificationproject.dto.UserDto;
import jakarta.transaction.Transactional;

/**
 * @author slimane
 * @Project
 */
public interface RegistrationService {
    @Transactional
    AuthenticationResponse registerAdmin(UserDto request);

    @Transactional
    AuthenticationResponse registerUser(UserDto request);
}
