package com.tennine.app.model;

public class FollowingFollowersModel {

    private String userId;
    private String username;
    private String image;
    private String nickname;
    public Boolean isConnected;

    public FollowingFollowersModel(String userId, String username, String image, String nickname,Boolean isConnected) {
        this.userId = userId;
        this.username = username;
        this.image = image;
        this.nickname = nickname;
        this.isConnected = isConnected;
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
