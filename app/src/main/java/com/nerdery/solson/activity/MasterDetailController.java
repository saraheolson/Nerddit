package com.nerdery.solson.activity;

import com.nerdery.solson.api.reddit.model.RedditLink;

/**
 * @author solson
 */
public interface MasterDetailController {

    public void updateDetails(RedditLink link);

    public void setActionBarTitle(String title);
}
