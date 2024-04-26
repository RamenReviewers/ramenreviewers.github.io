package com.ramenreviewers.blog.model;

import java.util.List;
import lombok.Getter;
import org.pkl.config.java.mapper.Named;

@Getter
public final class Shop {

  private final String name;
  private final String address;
  private final String city;
  private final String zip;
  private final List<String> tags;
  private final List<Link> links;
  private final int scoreAtmosphere;

  public Shop(
      @Named("name") String name,
      @Named("address") String address,
      @Named("city") String city,
      @Named("zip") String zip,
      @Named("tags") List<String> tags,
      @Named("links") List<Link> links,
      @Named("scoreAtmosphere") int scoreAtmosphere) {
    this.name = name;
    this.address = address;
    this.city = city;
    this.zip = zip;
    this.tags = tags;
    this.links = links;
    this.scoreAtmosphere = scoreAtmosphere;
  }

}