package com.ramenreviewers.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pkl.config.java.mapper.Named;

@Getter
@NoArgsConstructor
public final class Dish {

  private  String name;
  private  float price;
  private  float scoreBroth;
  private  float scoreNoodles;
  private  float scoreToppings;
  private  String description;

  public Dish(
      @Named("name") String name,
      @Named("price") float price,
      @Named("scoreBroth") float scoreBroth,
      @Named("scoreNoodles") float scoreNoodles,
      @Named("scoreToppings") float scoreToppings,
      @Named("description") String description) {
    this.name = name;
    this.price = price;
    this.scoreBroth = scoreBroth;
    this.scoreNoodles = scoreNoodles;
    this.scoreToppings = scoreToppings;
    this.description = description;
  }
}
