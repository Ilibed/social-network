package com.ilibed.post;

import com.ilibed.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @RequestMapping(value = "api/post/save/file")
    public ResponseEntity<PostDTOObject> savePost(@RequestParam(value = "file", required = false) MultipartFile file,
                                                   @RequestParam("text") String text,
                                                   @RequestParam("creationDate") String creationDate,
                                                  @RequestParam("userId") Integer userId){
        PostDTOObject postDTOObject = null;

        try {
            postDTOObject = postService.createPost(file, text, creationDate, userId);

            return new ResponseEntity<>(postDTOObject, HttpStatus.OK);
        }
        catch (ServiceException e){
            //logging
            System.out.print(e.getMessage());
        }

        return new ResponseEntity<>(postDTOObject, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "api/get/posts")
    public ResponseEntity<List<PostDTOObject>> getAllPosts(@RequestParam("userId") Integer userId){
        List<PostDTOObject> postProjections = postService.findAllPostsForUser(userId);

        return new ResponseEntity<>(postProjections, HttpStatus.OK);
    }

    @RequestMapping(value = "api/delete/post/{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable("id") Integer id){
        postService.deletePost(id);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
