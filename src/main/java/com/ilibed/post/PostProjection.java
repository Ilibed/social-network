package com.ilibed.post;

import org.springframework.beans.factory.annotation.Value;

public interface PostProjection {
    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.text}")
    String getText();

    @Value("#{target.creationDate}")
    String getCreationDate();

    @Value("#{target.photoPath}")
    String getPhotoPath();

    @Value("#{target.ownerId}")
    Integer getOwnerId();
}
