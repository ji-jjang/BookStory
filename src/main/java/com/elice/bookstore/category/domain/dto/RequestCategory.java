package com.elice.bookstore.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RequestCategory {

  private Long id;

  private String name;

  private Integer level;

}
