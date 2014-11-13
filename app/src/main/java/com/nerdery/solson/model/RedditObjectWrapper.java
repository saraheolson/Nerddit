package com.nerdery.solson.model;

import com.google.gson.JsonElement;

import com.nerdery.solson.api.reddit.RedditType;

public class RedditObjectWrapper {
  RedditType kind;
  JsonElement data;

  public RedditType getKind() {
    return kind;
  }

  public JsonElement getData() {
    return data;
  }
}
