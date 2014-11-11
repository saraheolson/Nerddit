package com.nerdery.solson.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Holds the data for a Reddit hot topic.
 * @author solson
 */
public class Topic implements Serializable {

    @DatabaseField(id = true)
    private long id;

    @DatabaseField
    private String title;

//    @DatabaseField
//    private int score;
//
//    @DatabaseField
//    private Date postedOn;
//
//    @DatabaseField
//    private String author;
//
//    @DatabaseField
//    private String imageUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public int getScore() {
//        return score;
//    }
//
//    public void setScore(int score) {
//        this.score = score;
//    }
//
//    public Date getPostedOn() {
//        return postedOn;
//    }
//
//    public void setPostedOn(Date postedOn) {
//        this.postedOn = postedOn;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
}
