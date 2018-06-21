package com.example.quang.library.model;

import java.io.Serializable;

public class ItemBook implements Serializable{
    private String title;
    private String author;
    private String desc;
    private String smallImage;
    private String largeImage;
    private String selfLink;

    public ItemBook(String title, String author, String desc, String smallImage, String largeImage, String selfLink) {
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.smallImage = smallImage;
        this.largeImage = largeImage;
        this.selfLink = selfLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }
}
