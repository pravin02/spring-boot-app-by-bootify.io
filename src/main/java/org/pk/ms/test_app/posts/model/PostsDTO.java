package org.pk.ms.test_app.posts.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class PostsDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String post;

    private Long userPosts;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(final String post) {
        this.post = post;
    }

    public Long getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(final Long userPosts) {
        this.userPosts = userPosts;
    }

}
