package com.example.blogsystem.Service;

import com.example.blogsystem.Api.ApiException;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.MyUser;
import com.example.blogsystem.Repository.AuthRepository;
import com.example.blogsystem.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;
    private final BlogRepository blogRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = authRepository.findMyUserByUsername(username);
        if (myUser == null) {throw new ApiException("Username or password is incorrect");}
        return myUser;
    }

    public List<MyUser> getAllUsers() {
        return authRepository.findAll();
    }

    public void updateUser(Integer id,MyUser myUser) {
        MyUser oldUser = authRepository.findMyUserById(id);
        if (oldUser == null) {throw new ApiException("User not found");}

        oldUser.setUsername(myUser.getUsername());
        oldUser.setPassword(myUser.getPassword());

        authRepository.save(oldUser);
    }

    public void deleteUser(Integer id) {
        MyUser myUser = authRepository.findMyUserById(id);
        if (myUser == null) {throw new ApiException("User not found");}
        authRepository.delete(myUser);
    }
}
