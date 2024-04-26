package com.ramenreviewers.blog.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pkl.config.java.mapper.Named;

@Setter
@Getter
@NoArgsConstructor
public class Review {

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
}


