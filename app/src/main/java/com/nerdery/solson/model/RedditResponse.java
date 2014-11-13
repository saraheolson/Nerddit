package com.nerdery.solson.model;

public class RedditResponse<T> {
  RedditResponse(T data) {
    this.data = data;
  }

  T data;

  public T getData() {
    return data;
  }
}
