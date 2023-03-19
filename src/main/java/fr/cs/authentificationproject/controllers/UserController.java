package fr.cs.authentificationproject.controllers;

import fr.cs.authentificationproject.auth.AuthenticationRequest;
import fr.cs.authentificationproject.auth.AuthenticationResponse;
import fr.cs.authentificationproject.dto.AdresseDto;
import fr.cs.authentificationproject.dto.UserDto;
import fr.cs.authentificationproject.entities.Adresse;
import fr.cs.authentificationproject.services.AdresseService;
import fr.cs.authentificationproject.services.UserService;
import fr.cs.authentificationproject.token.ConfirmationTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;
    private final AdresseService adresseService;
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping("/findAll")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/FindById")
    public ResponseEntity<UserDto> findById(
            @PathVariable("user-id") Integer userId
    ) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PutMapping("/UpdateUserById")
    public ResponseEntity updateUser(@PathParam("id")Integer id,
                                     @RequestParam String firstName,
                                     @RequestParam String lastName,
                                     @RequestParam String email,
                                     @RequestParam String password){

        userService.updateUser(id,firstName,lastName,email,password);

        return ResponseEntity.ok().build();

    }


    @DeleteMapping("/{deleteById}")
    public ResponseEntity<Void> delete(
            @PathVariable("id_user") Integer userId
    ) {

        userService.delete(userId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/addAdresseToUser")
    public ResponseEntity addAdresseToUser(@RequestBody AdresseDto adresse) {

        adresseService.addAdresseToUser(adresse);
        return ResponseEntity.ok().build();

    }


}
