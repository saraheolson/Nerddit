package com.nerdery.solson.api.reddit;

import com.nerdery.solson.model.RedditComment;
import com.nerdery.solson.model.RedditLink;
import com.nerdery.solson.model.RedditListing;
import com.nerdery.solson.model.RedditMore;

/**
 * To save time implenting the Reddit API (which has TERRIBLE documentation), I took this code from:
 * https://github.com/jacobtabak/droidcon/
 */
public enum RedditType {
    t1(RedditComment.class),
    t3(RedditLink.class),
    Listing(RedditListing.class),
    more(RedditMore.class);

    private final Class mCls;

    RedditType(Class cls) {
        mCls = cls;
    }

    public Class getDerivedClass() {
        return mCls;
    }
}
