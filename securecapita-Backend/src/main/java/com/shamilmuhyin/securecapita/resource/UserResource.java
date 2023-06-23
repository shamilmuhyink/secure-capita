package com.shamilmuhyin.securecapita.resource;

import com.shamilmuhyin.securecapita.domain.HttpResponse;
import com.shamilmuhyin.securecapita.domain.User;
import com.shamilmuhyin.securecapita.dto.UserDTO;
import com.shamilmuhyin.securecapita.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping(path="/user")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping(path="/register")
    public ResponseEntity<HttpResponse> saveuser(@RequestBody @Valid User user){
        UserDTO userDTO = userService.createUser(user);
        return ResponseEntity.created(getUri()).body(
                HttpResponse.builder()
                        .message("user created.")
                        .data(Map.of("user", userDTO))
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .timeStamp(LocalDateTime.now().toString())
                        .build()
        );
    }

    private URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/<userId>").toUriString());
    }
}
