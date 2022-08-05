package com.tennine.app.model;

import java.util.Date;

public class ReelsModel {

    private String videoUrl;
    private String videoDescrp;
    private String videoId;
    private long videoDate;
    private Date videoTimeStamp;
    private String userId;
    private String userName;
    private String userImage;
    private long likes;
    private long shares;
    private long comments;
    private long videoPlay;
    private boolean IsPresentUserLike;
    private boolean isRating;
    private long numRating;

    public ReelsModel() {
    }


    public ReelsModel(String videoUrl, String videoDescrp, String videoId, long videoDate, Date videoTimeStamp,
                      String userId, String userName, String userImage, long likes, long shares, long comments, long videoPlay) {
        this.videoUrl = videoUrl;
        this.videoDescrp = videoDescrp;
        this.videoId = videoId;
        this.videoDate = videoDate;
        this.videoTimeStamp = videoTimeStamp;
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.likes = likes;
        this.shares = shares;
        this.comments = comments;
        this.videoPlay = videoPlay;
    }

    public long getNumRating() {
        return numRating;
    }

    public void setNumRating(long numRating) {
        this.numRating = numRating;
    }

    public boolean isRating() {
        return isRating;
    }

    public void setRating(boolean rating) {
        isRating = rating;
    }

    public long getVideoPlay() {
        return videoPlay;
    }

    public boolean isPresentUserLike() {
        return IsPresentUserLike;
    }

    public void setPresentUserLike(boolean presentUserLike) {
        IsPresentUserLike = presentUserLike;
    }

    public void setVideoPlay(long videoPlay) {
        this.videoPlay = videoPlay;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoDescrp() {
        return videoDescrp;
    }

    public void setVideoDescrp(String videoDescrp) {
        this.videoDescrp = videoDescrp;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public long getVideoDate() {
        return videoDate;
    }

    public void setVideoDate(long videoDate) {
        this.videoDate = videoDate;
    }

    public Date getVideoTimeStamp() {
        return videoTimeStamp;
    }

    public void setVideoTimeStamp(Date videoTimeStamp) {
        this.videoTimeStamp = videoTimeStamp;
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

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getShares() {
        return shares;
    }

    public void setShares(long shares) {
        this.shares = shares;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }
}
