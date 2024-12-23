package com.example.blogsystem.Service;

import com.example.blogsystem.Api.ApiException;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.MyUser;
import com.example.blogsystem.Repository.AuthRepository;
import com.example.blogsystem.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;

    public List<Blog> getAllBlogs(){
        return blogRepository.findAll();
    }

    public void addBlog(Integer id, Blog blog){
        MyUser myUser=authRepository.findMyUserById(id);
        if (myUser == null) {throw new ApiException("user not found");}

        blog.setMyUser(myUser);

        blogRepository.save(blog);
    }

    public void updateBlog(Integer id,Integer blogId,Blog blog){
        MyUser myUser=authRepository.findMyUserById(id);
        if (myUser == null) {throw new ApiException("user not found");}
        Blog oldBlog=blogRepository.findBlogById(blogId);
        if (oldBlog == null) {throw new ApiException("blog not found");}
        if (!myUser.getBlogs().contains(oldBlog)) {
            throw new ApiException("unauthorized");
        }
        oldBlog.setTitle(blog.getTitle());
        oldBlog.setBody(blog.getBody());
        blogRepository.save(oldBlog);
    }

    public void deleteBlog(Integer id,Integer blogId){
        MyUser myUser=authRepository.findMyUserById(id);
        if (myUser == null) {throw new ApiException("user not found");}
        Blog blog=blogRepository.findBlogById(blogId);
        if (!myUser.getBlogs().contains(blog)) {
            throw new ApiException("unauthorized");
        }
        blogRepository.delete(blog);
    }

    public Blog getBlogById(Integer blogId){
        Blog blog=blogRepository.findBlogById(blogId);
        if (blog == null) {throw new ApiException("blog not found");}

        return blog;
    }

    public Blog getBlogByTitle(String title){
        Blog blog=blogRepository.findBlogByTitle(title);
        if (blog == null) {throw new ApiException("blog not found");}

        return blog;
    }

    public List<Blog> getMyBlogs(Integer id){
        MyUser myUser=authRepository.findMyUserById(id);
        if (myUser == null) {throw new ApiException("User not found");}

        return blogRepository.findBlogsByMyUser(myUser);
    }
}
