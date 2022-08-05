package com.tennine.app.model;

public class PostModel {

    private String postId;
    private String postDescription;
    private String postedBy;
    private String postImage;
    private long postDate;
    private Long postLikes;
    private Long postComments;
    private String location;
    private String userName;
    private String imageUrl;
    private String userId;
    private Boolean isLikeOrNot;
    private Boolean isPresendUserRate;
    private long numRating;
    private String userImage;

    public PostModel(String postId, String postDescription, String postedBy, String postImage,
                     long postDate, Long postLikes, Long postComments, String location,String userName,String imageUrl,String userId) {
        this.postId = postId;
        this.postDescription = postDescription;
        this.postedBy = postedBy;
        this.postImage = postImage;
        this.postDate = postDate;
        this.postLikes = postLikes;
        this.postComments = postComments;
        this.location = location;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }


    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public PostModel(){

    }

    public Boolean getPresendUserRate() {
        return isPresendUserRate;
    }

    public void setPresendUserRate(Boolean presendUserRate) {
        isPresendUserRate = presendUserRate;
    }

    public long getNumRating() {
        return numRating;
    }

    public void setNumRating(long numRating) {
        this.numRating = numRating;
    }

    public Boolean getLikeOrNot() {
        return isLikeOrNot;
    }

    public void setLikeOrNot(Boolean likeOrNot) {
        isLikeOrNot = likeOrNot;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public long getPostDate() {
        return postDate;
    }

    public void setPostDate(long postDate) {
        this.postDate = postDate;
    }

    public Long getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(Long postLikes) {
        this.postLikes = postLikes;
    }

    public Long getPostComments() {
        return postComments;
    }

    public void setPostComments(Long postComments) {
        this.postComments = postComments;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
