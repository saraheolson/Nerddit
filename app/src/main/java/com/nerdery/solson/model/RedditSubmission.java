package com.nerdery.solson.model;

import com.j256.ormlite.field.DatabaseField;

import org.joda.time.DateTime;

public class RedditSubmission extends RedditObject {

    String banned_by;

    String subreddit;

    boolean saved;

    @DatabaseField(id = true)
    String id;

    int gilded;

    @DatabaseField
    String author;

    @DatabaseField
    int score;

    @DatabaseField
    String name;

    long created;

    String author_flair_text;

    @DatabaseField
    DateTime created_utc;

    int ups;

    public String getBannedBy() {
        return banned_by;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public boolean isSaved() {
        return saved;
    }

    public String getId() {
        return id;
    }

    public int getGilded() {
        return gilded;
    }

    public String getAuthor() {
        return author;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public long getCreated() {
        return created;
    }

    public String getAuthorFlairText() {
        return author_flair_text;
    }

    public DateTime getCreatedUtc() {
        return created_utc;
    }

    public int getUps() {
        return ups;
    }
}
