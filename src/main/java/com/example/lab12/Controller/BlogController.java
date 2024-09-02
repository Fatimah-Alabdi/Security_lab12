package com.example.lab12.Controller;

import com.example.lab12.Api.ApiResponse;
import com.example.lab12.Model.Blog;
import com.example.lab12.Model.User;
import com.example.lab12.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/get-all")
    public ResponseEntity getAllBlog() {
        return ResponseEntity.status(200).body(blogService.getAllBlog());
    }

    @GetMapping("/get-my")
    public ResponseEntity getMyBlog(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(blogService.getMyBlogs(user.getId()));
    }
    @PostMapping("/add")
    public ResponseEntity addBlog(@AuthenticationPrincipal User user,@RequestBody @Valid Blog blog) {
        blogService.addBlog(user.getId(), blog);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully added blog"+user.getUsername()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateblog(@AuthenticationPrincipal User user, @PathVariable Integer blog_id, @RequestBody @Valid Blog blog) {
        blogService.updateBlog(blog_id, user.getId(), blog);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated blog" + user.getUsername()));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteblog(@AuthenticationPrincipal User user, @PathVariable Integer blog_id) {
        blogService.deleteBlog(blog_id, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Successfully deleted blog" + user.getUsername()));
    }
    @GetMapping("/get-blog-id/{id}")
   public ResponseEntity getBlogById(@AuthenticationPrincipal User user, @PathVariable Integer blog_id) {
        return ResponseEntity.status(200).body(blogService.getBlogById(blog_id, user.getId()));
   }
   @GetMapping("/get-blog-title/{title}")
   public ResponseEntity getBlogByTitle(@AuthenticationPrincipal User user, @PathVariable String title) {
        return ResponseEntity.status(200).body(blogService.getBlogByTitle(title, user.getId()));
   }
}
