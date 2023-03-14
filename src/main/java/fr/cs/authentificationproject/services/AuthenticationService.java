package fr.cs.authentificationproject.services;

import fr.cs.authentificationproject.auth.AuthenticationRequest;
import fr.cs.authentificationproject.auth.AuthenticationResponse;

/**
 * @author
 * @Project
 */
public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
