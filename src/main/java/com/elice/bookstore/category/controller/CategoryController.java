package com.elice.bookstore.category.controller;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.service.BookService;
import com.elice.bookstore.category.domain.BookCategory;
import com.elice.bookstore.category.domain.Category;
import com.elice.bookstore.category.domain.dto.RequestBookCategory;
import com.elice.bookstore.category.domain.dto.RequestBookList;
import com.elice.bookstore.category.domain.dto.RequestCategory;
import com.elice.bookstore.category.domain.dto.ResponseBookCategory;
import com.elice.bookstore.category.domain.dto.ResponseBookCategoryList;
import com.elice.bookstore.category.service.BookCategoryService;
import com.elice.bookstore.category.service.CategoryService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Builder
@RequestMapping("/api")
public class CategoryController {

  @Autowired
  private final CategoryService categoryService;

  @Autowired
  private final BookCategoryService bookCategoryService;
  @Autowired
  private final BookService bookService;


  /**
   * 요청하는 레벨의 카테고리 가져오기
   **/
  @GetMapping("/v1/bookCategory")
  public List<RequestCategory> categories() {
    List<Category> findCategoriesByLevel = categoryService.findByLevelAll(1);

      return findCategoriesByLevel.stream()
          .map(m -> new RequestCategory(m.getId(), m.getName(), 1))
          .toList();
  }
  /**
   * 요청하는 레벨의 카테고리 가져오기
   **/
  @GetMapping("/v1/bookCategory/lowRank/{id}")
  public List<RequestCategory> categories(@PathVariable Long id) {

    List<Category> findLowRankCategories = categoryService.bringLowRankCategoryALl(categoryService.read(id).get());

    return findLowRankCategories.stream()
        .map(m -> new RequestCategory(m.getId(), m.getName(),m.getLevel()))
        .toList();
  }

  /**
   * 책에 해당하는 카테고리 가져오기
   **/
  @GetMapping("/v1/bookCategory/{id}")
  public List<RequestCategory> requestBookCategory(@PathVariable Long id) {
    Book book = bookService.findById(id);
    List<BookCategory> categoryList = book.getCategoryList();
    return categoryList.stream()
        .map(m -> new RequestCategory(m.getCategory().getId(), m.getCategory().getName(), m.getCategory().getLevel()))
        .toList();
  }

  /**
   * 카테고리에 해당하는 책 가져오기
   **/
  @GetMapping("/v1/bookCategory/bring/{id}")
  public List<RequestBookList> requestBookFromCategory(@PathVariable Long id) {
    Category category = categoryService.read(id).get();
    List<String> categoryAll = categoryService.bringHighRankCategoryAll(category);
    List<BookCategory> bookList = category.getBooks();
    return bookList.stream()
        .map(m -> new RequestBookList(m.getBook().getId(), m.getBook().getItemName(), m.getBook().getPrice(),
            m.getBook().getAuthor(), m.getBook().getDescription(), m.getBook().getPublisher(), categoryAll, m.getBook().getIsbn()))
        .toList();
  }

  /**
   * 책에서 특정 카테고리 추가하기
   **/
  @PostMapping("/v1/bookCategory/add")
  public ResponseBookCategory addCategory(@RequestBody ResponseBookCategoryList responseBookCategoryList) {
    BookCategory bookCategory1 = new BookCategory();
    BookCategory bookCategory2 = new BookCategory();
    BookCategory bookCategory3 = new BookCategory();
    bookCategory1.setCategory(categoryService.read(responseBookCategoryList.getCategoryLevel1()).get());
    bookCategory2.setCategory(categoryService.read(responseBookCategoryList.getCategoryLevel2()).get());
    bookCategory3.setCategory(categoryService.read(responseBookCategoryList.getCategoryLevel3()).get());
    bookCategory1.setBook(bookService.findById(responseBookCategoryList.getBookId()));
    bookCategory2.setBook(bookService.findById(responseBookCategoryList.getBookId()));
    bookCategory3.setBook(bookService.findById(responseBookCategoryList.getBookId()));
    BookCategory bookCategory01 = bookCategoryService.create(bookCategory1);
    BookCategory bookCategory02 = bookCategoryService.create(bookCategory2);
    BookCategory bookCategory03 = bookCategoryService.create(bookCategory3);

    ResponseBookCategory responseBookCategory = new ResponseBookCategory(
        responseBookCategoryList.getBookId(), responseBookCategoryList.getCategoryLevel1(),
        responseBookCategoryList.getCategoryLevel2(), responseBookCategoryList.getCategoryLevel3());

    return  responseBookCategory;
  }


  /**
   * 책에서 특정 카테고리 추가하기
   **/
  @PutMapping("/v1/bookCategory/update")
  public void updateCategory(@RequestBody RequestBookCategory requestBookCategory) {
    BookCategory bookCategory = new BookCategory();
    bookCategory.setCategory(requestBookCategory.getCategory());
    bookCategory.setBook(requestBookCategory.getBook());
    bookCategoryService.create(bookCategory);
  }


  /**
   * 책에서 특정 카테고리 지우기
   **/
  @DeleteMapping("/v1/bookCategory/delete")
  public void deleteCategory(@RequestBody RequestBookCategory requestBookCategory) {

    BookCategory bookCategory = bookCategoryService.findByBookAndCategory(
        requestBookCategory.getBook()
            .getId(), requestBookCategory.getCategory().getId()).get();
    bookCategoryService.delete(bookCategory);
  }
}
