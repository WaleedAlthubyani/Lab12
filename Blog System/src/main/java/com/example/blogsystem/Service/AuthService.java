package com.example.blogsystem.Service;

import com.example.blogsystem.Model.MyUser;
import com.example.blogsystem.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public void register(MyUser myUser) {

        myUser.setRole("USER");
        String hash = new BCryptPasswordEncoder().encode(myUser.getPassword());
        myUser.setPassword(hash);

        authRepository.save(myUser);
    }
}
