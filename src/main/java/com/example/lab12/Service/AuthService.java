package com.example.lab12.Service;

import com.example.lab12.Api.ApiException;
import com.example.lab12.Model.User;
import com.example.lab12.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public List<User> findAll() {
        return authRepository.findAll();
    }
    public void register(User user) {
        user.setRole("USER");
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);
    }
    public void update(Integer id,User user) {
        User findUser = authRepository.findUserById(id);
        if(findUser == null){
            throw new ApiException("user not found");
        }
        findUser.setPassword(user.getPassword());
        findUser.setUsername(user.getUsername());
        authRepository.save(findUser);

    }
    public void delete(Integer id) {
        User findUser = authRepository.findUserById(id);
        if(findUser == null){
            throw new ApiException("user not found");
        }
        authRepository.delete(findUser);
    }
}
