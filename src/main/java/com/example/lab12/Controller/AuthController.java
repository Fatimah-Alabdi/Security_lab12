package com.example.lab12.Controller;

import com.example.lab12.Model.User;
import com.example.lab12.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/get")
    public ResponseEntity getAllUser(){
        return ResponseEntity.status(200).body(authService.findAll());
    }
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody User user) {
        authService.register(user);
        return ResponseEntity.status(200).body("user registered successfully");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id,@Valid @RequestBody User user) {
        authService.update(id,user);
        return ResponseEntity.status(200).body("user updated successfully");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        authService.delete(id);
        return ResponseEntity.status(200).body("user deleted successfully");
    }
}
