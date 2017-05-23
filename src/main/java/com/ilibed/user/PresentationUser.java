package com.ilibed.user;

import org.springframework.beans.factory.annotation.Value;

public interface PresentationUser {
    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.firstName} #{target.lastName}")
    String getFullName();

    @Value("#{target.path}")
    String getPhotoPath();

    @Value("#{target.city}, #{target.country}")
    String getFullAddress();

    @Value("#{target.email}")
    String getEmail();
}
