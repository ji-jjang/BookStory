package com.elice.bookstore.book.domain.dto;


import com.elice.bookstore.book.domain.Book;
import lombok.Getter;

/**
 * BookResponse.
 */
@Getter
public class BookResponse {

  private final String itemName;
  private final Integer price;
  private final String author;
  private final String publisher;
  private final String description;

  public BookResponse(Book book) {
    this.itemName = book.getItemName();
    this.price = book.getPrice();
    this.author = book.getAuthor();
    this.publisher = book.getPublisher();
    this.description = book.getDescription();
  }

}
