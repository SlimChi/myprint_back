package fr.cs.authentificationproject.controllers;


import fr.cs.authentificationproject.auth.AuthenticationRequest;
import fr.cs.authentificationproject.auth.AuthenticationResponse;
import fr.cs.authentificationproject.dto.UserDto;
import fr.cs.authentificationproject.services.implement.ResgistrationServiceilmp;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Admin")
public class RegistrationController {

    private final ResgistrationServiceilmp userService;


    @PostMapping("register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody UserDto user) {

        return ResponseEntity.ok(userService.registerAdmin(user));

    }

    @PostMapping("register/user")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserDto user) {

        return ResponseEntity.ok(userService.registerUser(user));

    }

    @PostMapping("/Sign-in")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest user) {

        return ResponseEntity.ok(userService.authenticate(user));
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return userService.confirmToken(token);
    }

}
