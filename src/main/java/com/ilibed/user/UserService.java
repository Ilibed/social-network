package com.ilibed.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(Integer id) {
        return userRepository.findOne(id);
    }

    public boolean isEmailExists(String email){
        return userRepository.existsByEmail(email);
    }

    public void createUser(User user){
        user.setBanned(false);
        user.setMainPhotoId(1);
        user.setRoleId(2);
        userRepository.save(user);
    }

    public User getAuthUser(){
        User user = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()){
            String email = ((UserDetails) authentication.getPrincipal()).getUsername();
            user = getUserByEmail(email);
        }

        return user;
    }

    public void setAuthUser(User user){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Integer getIdByEmail(String email){
        return userRepository.findIdByEmail(email);
    }

    public SimpleUser getSimpleUserInfo(Integer id){
        return userRepository.findSimpleById(id);
    }
}
