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

  public static final int MAX_SCORE = 5;
  public static final int MAX_SCORE_ATMOSPHERE = 3;

  private  Shop shop;
  private  Dish dish;
  private  List<String> reviewers;
  private int totalScore;
  private List<String> picturePaths;
  private String id;

  public Review(
      @Named("shop") Shop shop,
      @Named("dish") Dish dish,
      @Named("reviewers") List<String> reviewers) {
    this.shop = shop;
    this.dish = dish;
    this.reviewers = reviewers;
  }
}


