package com.tennine.app.model;

import java.util.Date;

public class CommentsModel {
    private String commentsId;
    private String userId;
    private String userName;
    private String userImage;
    private String textComments;
    private long commentsDate;
    public Date commnetsDate;

    public CommentsModel(String commentsId, String userId, String userName, String userImage, String textComments,
                         long commentsDate) {
        this.commentsId = commentsId;
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.textComments = textComments;
        this.commentsDate = commentsDate;
    }



    public CommentsModel(){

    }

    public String getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(String commentsId) {
        this.commentsId = commentsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getTextComments() {
        return textComments;
    }

    public void setTextComments(String textComments) {
        this.textComments = textComments;
    }

    public long getCommentsDate() {
        return commentsDate;
    }

    public void setCommentsDate(long commentsDate) {
        this.commentsDate = commentsDate;
    }
}
