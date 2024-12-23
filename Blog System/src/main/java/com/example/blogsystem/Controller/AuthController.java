package com.example.blogsystem.Controller;

import com.example.blogsystem.Api.ApiResponse;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.MyUser;
import com.example.blogsystem.Service.AuthService;
import com.example.blogsystem.Service.MyUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MyUserDetailsService myUserDetailsService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody @Valid MyUser user) {
        authService.register(user);
        return ResponseEntity.status(201).body(new ApiResponse<>("User registered successfully"));
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<ApiResponse<List<MyUser>>> getAllUsers(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(new ApiResponse<>(myUserDetailsService.getAllUsers()));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<String>> updateUser(@AuthenticationPrincipal MyUser user,@RequestBody @Valid MyUser updatedUser) {
        myUserDetailsService.updateUser(user.getId(),updatedUser);
        return ResponseEntity.status(200).body(new ApiResponse<>("User updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteUser(@AuthenticationPrincipal MyUser user) {
        myUserDetailsService.deleteUser(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse<>("User deleted successfully"));
    }


}
