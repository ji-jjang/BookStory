package com.elice.bookstore.book.domain.dto;

import com.elice.bookstore.book.domain.Book;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

/**
 * ResponseBook.
 */
@Getter
public class ResponseQuestion {

  private final String itemName;
  private final Integer price;
  private final String author;
  private final String isbn;
  private final String publisher;
  private final String description;
  private final Integer stock;
  private final String createdAt;

  public ResponseQuestion(Book book) {
    this.itemName = book.getItemName();
    this.price = book.getPrice();
    this.author = book.getAuthor();
    this.isbn = book.getIsbn();
    this.publisher = book.getPublisher();
    this.description = book.getDescription();
    this.stock = book.getStock();
    this.createdAt = book.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }
}
