package com.ilibed.post;

import com.ilibed.user.SimpleUser;

public class PostDTOObject {
    private PostProjection postProjection;
    private SimpleUser simpleUser;

    public PostDTOObject(PostProjection postProjection, SimpleUser simpleUser){
        this.postProjection = postProjection;
        this.simpleUser = simpleUser;
    }

    public PostProjection getPostProjection() {
        return postProjection;
    }

    public void setPostProjection(PostProjection postProjection) {
        this.postProjection = postProjection;
    }

    public SimpleUser getSimpleUser() {
        return simpleUser;
    }

    public void setSimpleUser(SimpleUser simpleUser) {
        this.simpleUser = simpleUser;
    }
}
