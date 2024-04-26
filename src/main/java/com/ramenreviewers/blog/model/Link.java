package com.ramenreviewers.blog.model;

import lombok.Getter;
import org.pkl.config.java.mapper.Named;

@Getter
public final class Link {

  private final String url;
  private final String displayName;

  public Link(
      @Named("url") String url,
      @Named("displayName") String displayName) {
    this.url = url;
    this.displayName = displayName;
  }
}