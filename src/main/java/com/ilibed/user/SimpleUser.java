package com.ilibed.user;

import org.springframework.beans.factory.annotation.Value;

public interface SimpleUser {
    @Value("#{target.firstName} #{target.lastName}")
    String getFullName();

    @Value("#{target.path}")
    String getPhotoPath();
}
