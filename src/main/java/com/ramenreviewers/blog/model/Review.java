package com.ramenreviewers.blog.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pkl.config.java.mapper.Named;

@Setter
@Getter
@NoArgsConstructor
public final class Review {

  private  Shop shop;
  private  List<Dish> dishes;
  private  List<String> reviewers;
  private int totalScore;
  private List<String> picturePaths;
  private String id;

  public Review(
      @Named("shop") Shop shop,
      @Named("dishes") List<Dish> dishes,
      @Named("reviewers") List<String> reviewers) {
    this.shop = shop;
    this.dishes = dishes;
    this.reviewers = reviewers;
  }



  @Getter
  @NoArgsConstructor
  public static final class Dish {

    private  String name;
    private  int price;
    private  int scoreBroth;
    private  int scoreNoodles;
    private  int scoreToppings;
    private  String description;

    public Dish(
        @Named("name") String name,
        @Named("price") int price,
        @Named("scoreBroth") int scoreBroth,
        @Named("scoreNoodles") int scoreNoodles,
        @Named("scoreToppings") int scoreToppings,
        @Named("description") String description) {
      this.name = name;
      this.price = price;
      this.scoreBroth = scoreBroth;
      this.scoreNoodles = scoreNoodles;
      this.scoreToppings = scoreToppings;
      this.description = description;
    }
  }

  @Getter
  public static final class Shop {

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

  @Getter
  public static final class Link {

    private final String url;
    private final String displayName;

    public Link(
        @Named("url") String url,
        @Named("displayName") String displayName) {
      this.url = url;
      this.displayName = displayName;
    }
  }

}


