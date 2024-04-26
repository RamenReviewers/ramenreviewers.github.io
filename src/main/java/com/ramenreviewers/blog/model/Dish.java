package com.ramenreviewers.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pkl.config.java.mapper.Named;

@Getter
@NoArgsConstructor
public final class Dish {

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
