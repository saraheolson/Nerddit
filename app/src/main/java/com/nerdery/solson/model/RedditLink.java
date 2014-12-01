package com.nerdery.solson.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * RedditLink object holds information about a single link from the Hot topic list.
 *
 * To save time implenting the Reddit API (which has TERRIBLE documentation), I took this code from:
 * https://github.com/jacobtabak/droidcon/
 */
@DatabaseTable(tableName = "RedditLink")
public class RedditLink extends RedditSubmission implements Serializable {

    @DatabaseField
    private DateTime downloaded;

    private String domain;

    private String selftext_html;

    private String selftext;

    private String link_flair_text;

    private boolean clicked;

    private boolean hidden;

    @DatabaseField
    private String thumbnail;

    private boolean is_self;

    @DatabaseField
    private String permalink;

    private boolean stickied;

    @DatabaseField
    private String url;

    @DatabaseField
    private String title;

    @DatabaseField
    private int num_comments;

    private boolean visited;

    public String getDomain() {
        return domain;
    }

    public String getSelftextHtml() {
        return selftext_html;
    }

    public String getSelftext() {
        return selftext;
    }

    public String getLinkFlairText() {
        return link_flair_text;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isHidden() {
        return hidden;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public boolean isSelf() {
        return is_self;
    }

    public String getPermalink() {
        return permalink;
    }

    public boolean isStickied() {
        return stickied;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public int getNumComments() {
        return num_comments;
    }

    public boolean isVisited() {
        return visited;
    }

    public DateTime getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(DateTime downloaded) {
        this.downloaded = downloaded;
    }
}
