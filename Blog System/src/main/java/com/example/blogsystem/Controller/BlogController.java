package com.example.blogsystem.Controller;

import com.example.blogsystem.Api.ApiResponse;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.MyUser;
import com.example.blogsystem.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<Blog>>> getAllBlogs() {
        return ResponseEntity.status(200).body(new ApiResponse<>(blogService.getAllBlogs()));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addBlog(@AuthenticationPrincipal MyUser user,@RequestBody @Valid Blog blog) {
        blogService.addBlog(user.getId(), blog);
        return ResponseEntity.status(201).body(new ApiResponse<>("Blog added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<String>> updateBlog(@AuthenticationPrincipal MyUser user,@PathVariable Integer id,@RequestBody @Valid Blog blog) {
        blogService.updateBlog(user.getId(), id, blog);
        return ResponseEntity.status(200).body(new ApiResponse<>("Blog updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBlog(@AuthenticationPrincipal MyUser user,@PathVariable Integer id) {
        blogService.deleteBlog(user.getId(), id);
        return ResponseEntity.status(200).body(new ApiResponse<>("Blog deleted successfully"));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ApiResponse<Blog>> getBlogById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(new ApiResponse<>(blogService.getBlogById(id)));
    }

    @GetMapping("/get-by-title/{title}")
    public ResponseEntity<ApiResponse<Blog>> getBlogByTitle(@PathVariable String title) {
        return ResponseEntity.status(200).body(new ApiResponse<>(blogService.getBlogByTitle(title)));
    }

    @GetMapping("/get-user-blogs/{id}")
    public ResponseEntity<ApiResponse<List<Blog>>> getMyBlogs(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(new ApiResponse<>(blogService.getMyBlogs(id)));
    }


}
