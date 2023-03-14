package fr.cs.authentificationproject.services.implement;

import fr.cs.authentificationproject.auth.AuthenticationResponse;
import fr.cs.authentificationproject.config.JwtService;
import fr.cs.authentificationproject.dto.UserDto;
import fr.cs.authentificationproject.entities.EmailMessage;
import fr.cs.authentificationproject.entities.Role;
import fr.cs.authentificationproject.repositories.UserRepository;
import fr.cs.authentificationproject.services.EmailSendService;
import fr.cs.authentificationproject.services.RegistrationService;
import fr.cs.authentificationproject.token.ConfirmationToken;
import fr.cs.authentificationproject.token.ConfirmationTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author slimane
 * @Project
 */
@Service
@RequiredArgsConstructor
public class ResgistrationServiceilmp implements RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserServiceImp userServiceImp;
    private final EmailSendService emailSendService;

    @Override
    @Transactional
    public AuthenticationResponse registerAdmin(UserDto request) {
//        validator.validate(request);


        var user = UserDto.toEntity(request).builder().firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(new Role(1, "ADMIN")).build();
        userRepository.save(user);

        var savedUser = userRepository.save(user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("fullName", savedUser.getFirstName() + " " + savedUser.getLastName());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    @Override
    @Transactional
    public AuthenticationResponse registerUser(UserDto request) {
//        validator.validate(request);


        var user = UserDto.toEntity(request).builder().firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(new Role(2, "USER")).build();
        userRepository.save(user);

        var savedUser = userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);

        String link = "http://localhost:9090/auth/confirm?token=" + token;
        emailSendService.sendEmail(
                request.getEmail(),
                buildEmail(request.getFirstName(),
                        link));
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("fullName", savedUser.getFirstName() + " " + savedUser.getLastName());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userServiceImp.enableAppUser(confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    private String buildEmail(String name, String link) {
        return "Bonjour : " + name + "<a href=\"" + link + "\">Activate Now</a>";

    }
}
