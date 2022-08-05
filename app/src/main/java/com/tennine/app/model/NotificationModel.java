package com.tennine.app.model;

public class NotificationModel {

    private String notificationBy;
    private String type;
    private String postId;
    private String postedBy;
    private Boolean checkOpen;
    private long notificationAt;
    private String userImage;
    private String userName;
    private String description;
    private String postImage;
    private String postType;

    public NotificationModel(){

    }

    public NotificationModel(String notificationBy, String type, String postId, String postedBy, Boolean checkOpen, long notificationAt, String userImage, String userName, String description, String postImage) {
        this.notificationBy = notificationBy;
        this.type = type;
        this.postId = postId;
        this.postedBy = postedBy;
        this.checkOpen = checkOpen;
        this.notificationAt = notificationAt;
        this.userImage = userImage;
        this.userName = userName;
        this.description = description;
        this.postImage = postImage;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getNotificationBy() {
        return notificationBy;
    }

    public void setNotificationBy(String notificationBy) {
        this.notificationBy = notificationBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public Boolean getCheckOpen() {
        return checkOpen;
    }

    public void setCheckOpen(Boolean checkOpen) {
        this.checkOpen = checkOpen;
    }

    public long getNotificationAt() {
        return notificationAt;
    }

    public void setNotificationAt(long notificationAt) {
        this.notificationAt = notificationAt;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
