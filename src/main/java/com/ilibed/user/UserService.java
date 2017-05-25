package com.ilibed.user;

import com.ilibed.exception.ServiceException;
import com.ilibed.photo.Photo;
import com.ilibed.photo.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserFriendRepository userFriendRepository;
    private final PhotoService photoService;

    @Autowired
    public UserService(UserRepository userRepository, UserFriendRepository userFriendRepository,
                       PhotoService photoService) {
        this.userRepository = userRepository;
        this.userFriendRepository = userFriendRepository;
        this.photoService = photoService;
    }

    public User getUserByEmail(String email) {
        if(email == null){
            throw new NullPointerException("UserService, getUserByEmail : email parameter is null");
        }

        return userRepository.findByEmail(email);
    }

    public User getUserById(Integer id) {
        if(id == null){
            throw new NullPointerException("UserService, getUserById : id parameter is null");
        }

        return userRepository.findOne(id);
    }

    public boolean isEmailExists(String email){
        return email != null && userRepository.existsByEmail(email);
    }

    public void createUser(User user){
        if(user == null){
            throw new NullPointerException("UserService, createUser : user parameter is null");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setBanned(false);
        user.setMainPhotoId(1);
        user.setRoleId(2);
        userRepository.save(user);
    }

    public User updateUser(MultipartFile file, String name, String surname, String country, String city) throws ServiceException{
        Integer ownerId = getAuthId();
        if (ownerId == null){
            return null;
        }

        Photo photo;
        if (file == null){
            photo = photoService.findById(1);
        }
        else {
            photo = photoService.savePhoto(photoService.createPhoto(file, ownerId));
        }

        userRepository.update(name, surname, country, city, photo.getId(), ownerId);

        return userRepository.findOne(ownerId);
    }

    public SimpleUser addToFriend(Integer userId){
        Integer ownerId = getAuthId();
        if (ownerId == null){
            return null;
        }

        UserFriend userFriend = new UserFriend();
        userFriend.setFriendId(userId);
        userFriend.setUserId(ownerId);
        userFriendRepository.save(userFriend);
        userFriend = new UserFriend();
        userFriend.setUserId(userId);
        userFriend.setFriendId(ownerId);
        userFriendRepository.save(userFriend);

        return userRepository.findSimpleById(userId);
    }

    public List<SimpleUser> getUserFriends(){
        Integer ownerId = getAuthId();
        if (ownerId == null){
            return null;
        }

        return userRepository.findUserFriends(ownerId);
    }

    public List<SimpleUser> getUsers(){
        Integer ownerId = getAuthId();
        if (ownerId == null){
            return null;
        }

        List<Integer> ids = userFriendRepository.findFriends(ownerId);
        ids.add(ownerId);

        return userRepository.findUsersWithoutOwner(ids);
    }

    public void removeFriend(Integer userId){
        Integer ownerId = getAuthId();
        if (ownerId != null){
            UserFriend userFriend = userFriendRepository.findByUserIdAndFriendId(ownerId, userId);
            userFriendRepository.delete(userFriend.getId());
            userFriend = userFriendRepository.findByUserIdAndFriendId(userId, ownerId);
            userFriendRepository.delete(userFriend.getId());
        }
    }

    public User getAuthUser(){
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !(authentication.getPrincipal().getClass() == String.class)){
            String email = ((UserDetails) authentication.getPrincipal()).getUsername();
            user = getUserByEmail(email);
        }

        return user;
    }

    public Integer getAuthId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()){
            String email = ((UserDetails) authentication.getPrincipal()).getUsername();
            return getIdByEmail(email);
        }

        return null;
    }

    public void setAuthUser(User user){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Integer getIdByEmail(String email){
        if(email == null){
            throw new NullPointerException("UserService, getIdByEmail : email parameter is null");
        }

        return userRepository.findIdByEmail(email);
    }

    public SimpleUser getSimpleUserInfo(Integer id){
        if(id == null){
            throw new NullPointerException("UserService, getSimpleUserInfo : id parameter is null");
        }

        return userRepository.findSimpleById(id);
    }

    public PresentationUser getPresentationUser(Integer id){
        return userRepository.findPresentationUser(id);
    }
}
