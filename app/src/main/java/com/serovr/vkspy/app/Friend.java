package com.serovr.vkspy.app;

public class Friend {
    private String firstName;
    private String lastName;
    private String imageUrl;
    private String bigImageUrl;
    private int id;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public int getId() {
        return id;
    }

    Friend(int id, String firstName, String lastName, String imageUrl){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }

}
