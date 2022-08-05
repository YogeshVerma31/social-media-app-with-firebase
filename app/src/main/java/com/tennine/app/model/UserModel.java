package com.tennine.app.model;

public class UserModel {

    public String email,username,
            fullname,
            nickname,
            phoneNumber,
            location,
            bio,
            image,
            Id,
            lastname,token;

    public Boolean isConnected;

    public long no_of_posts,no_of_followers,no_of_following;

    public UserModel(){

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

    public UserModel(String Id,String email, String username, String fullname, String nickname, String phoneNumber, String location, String bio, String image, String lastname,
                     long no_of_posts, long no_of_followers, long no_of_following, Boolean isConnected) {
        this.Id = Id;
        this.email = email;
        this.username = username;
        this.fullname = fullname;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.bio = bio;
        this.image = image;
        this.lastname = lastname;
        this.no_of_posts = no_of_posts;
        this.no_of_followers = no_of_followers;
        this.no_of_following = no_of_following;
        this.isConnected = isConnected;
    }

    public Boolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(Boolean isConnected) {
        this.isConnected = isConnected;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public long getNo_of_posts() {
        return no_of_posts;
    }

    public void setNo_of_posts(long no_of_posts) {
        this.no_of_posts = no_of_posts;
    }

    public long getNo_of_followers() {
        return no_of_followers;
    }

    public void setNo_of_followers(long no_of_followers) {
        this.no_of_followers = no_of_followers;
    }

    public long getNo_of_following() {
        return no_of_following;
    }

    public void setNo_of_following(long no_of_following) {
        this.no_of_following = no_of_following;
    }
}
