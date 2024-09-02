package com.example.lab12.Service;

import com.example.lab12.Api.ApiException;
import com.example.lab12.Model.Blog;
import com.example.lab12.Model.User;
import com.example.lab12.Repository.AuthRepository;
import com.example.lab12.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final AuthRepository authRepository;
    private final BlogRepository blogRepository;
    public List<Blog> getAllBlog(){
        return blogRepository.findAll();
    }
    public List<Blog> getMyBlogs(Integer user_id){
        User user=authRepository.findUserById(user_id);
        return blogRepository.findAllByUser(user);
    }
    public void addBlog(Integer user_id,Blog blog){
        User user=authRepository.findUserById(user_id);
        blog.setUser(user);
        blogRepository.save(blog);

    }
    public void updateBlog(Integer blog_id,Integer user_id,Blog blog){
        User user=authRepository.findUserById(user_id);
        Blog blog1=blogRepository.findBlogById(blog_id);
        if(user_id!=blog1.getUser().getId()){
            throw new ApiException("Sorry you dont have authority");
        }
        blog1.setUser(user);
        blog1.setTitle(blog.getTitle());
        blog1.setBody(blog.getBody());
        blogRepository.save(blog1);

    }
    public void deleteBlog(Integer blog_id,Integer user_id){
        User user=authRepository.findUserById(user_id);
        Blog blog=blogRepository.findBlogById(blog_id);
        if(user_id!=blog.getUser().getId()){
            throw new ApiException("Sorry you dont have authority");
        }
        blogRepository.delete(blog);

    }
    public Blog getBlogById(Integer blog_id,Integer user_id){
        User user=authRepository.findUserById(user_id);
        Blog blog=blogRepository.findBlogById(blog_id);
        if(user_id!=blog.getUser().getId()){
            throw new ApiException("Sorry you dont have authority");
        }
        return blog;
    }
    public Blog getBlogByTitle(String title,Integer user_id){
        User user=authRepository.findUserById(user_id);
        Blog blog=blogRepository.findBlogByTitle(title);
        if(user_id!=blog.getUser().getId()){
            throw new ApiException("Sorry you dont have authority");
        }
        return blog;
    }
}
