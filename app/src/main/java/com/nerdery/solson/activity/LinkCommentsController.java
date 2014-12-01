package com.nerdery.solson.activity;

import com.nerdery.solson.model.RedditLink;

/**
 * Controls viewing comments for a selected RedditLink object.
 */
public interface LinkCommentsController {

    public void updateComments(RedditLink link);

}
