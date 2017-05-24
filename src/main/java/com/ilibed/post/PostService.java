package com.ilibed.post;

import com.ilibed.exception.ServiceException;
import com.ilibed.photo.Photo;
import com.ilibed.photo.PhotoService;
import com.ilibed.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserPostRepository userPostRepository;
    private PhotoService photoService;
    private UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserPostRepository userPostRepository,
                       PhotoService photoService, UserService userService){
        this.postRepository = postRepository;
        this.userPostRepository = userPostRepository;
        this.photoService = photoService;
        this.userService = userService;
    }

    public PostDTOObject createPost(MultipartFile file, String text, String creationDate, Integer userId) throws ServiceException{
        Integer ownerId = userService.getAuthId();
        if (ownerId == null){
            return null;
        }

        Photo photo;
        if (file == null){
            photo = photoService.findById(2);
        }
        else {
            photo = savePhoto(photoService.createPhoto(file, ownerId));
        }

        Post post = savePost(createPost(text, creationDate, photo.getId()));
        UserPost userPost = saveUserPost(createUserPost(userId, ownerId, post.getId()));
        PostProjection postProjection = postRepository.findPostProjectionById(userPost.getId());

        return new PostDTOObject(postProjection, userService.getSimpleUserInfo(postProjection.getOwnerId()));
    }

    public Post savePost(Post post){
        if(post == null){
            throw new NullPointerException("PostService, savePost : post parameter is null");
        }
        return postRepository.save(post);
    }

    public UserPost saveUserPost(UserPost userPost){
        if(userPost == null){
            throw new NullPointerException("PostService, saveUserPost : userPost parameter is null");
        }
        return userPostRepository.save(userPost);
    }

    public List<PostDTOObject> findAllPostsForUser(Integer id){
        List<PostProjection> postProjections = postRepository.findAllPostsForUser(id);
        List<PostDTOObject> postDTOObjects = new ArrayList<>();

        for (PostProjection postProjection : postProjections) {
            postDTOObjects.add(new PostDTOObject(postProjection, userService.getSimpleUserInfo(postProjection.getOwnerId())));
        }

        return postDTOObjects;
    }

    public void deletePost(Integer userPostId){
        if(userPostId == null){
            throw new NullPointerException("PostService, deletePost : userPostId parameter is null");
        }
        Integer postId = userPostRepository.findOne(userPostId).getPostId();
        userPostRepository.delete(userPostId);
        postRepository.delete(postId);
    }

    private Photo savePhoto(Photo photo){
        return photoService.savePhoto(photo);
    }

    private Post createPost(String text, String creationDate, Integer photoId){
        Post post = new Post();
        post.setCreationDate(creationDate);
        post.setText(text);
        post.setPhotoId(photoId);

        return post;
    }

    private UserPost createUserPost(Integer userId, Integer ownerId, Integer postId){
        UserPost userPost = new UserPost();
        userPost.setPostId(postId);
        userPost.setUserId(userId);
        userPost.setOwnerId(ownerId);

        return userPost;
    }
}
