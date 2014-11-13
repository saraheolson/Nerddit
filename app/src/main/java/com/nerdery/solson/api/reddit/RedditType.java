package com.nerdery.solson.api.reddit;

import com.nerdery.solson.api.reddit.model.RedditComment;
import com.nerdery.solson.api.reddit.model.RedditLink;
import com.nerdery.solson.api.reddit.model.RedditListing;
import com.nerdery.solson.api.reddit.model.RedditMore;

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
