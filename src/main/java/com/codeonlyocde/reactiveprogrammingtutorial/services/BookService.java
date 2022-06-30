package com.codeonlyocde.reactiveprogrammingtutorial.services;

import com.codeonlyocde.reactiveprogrammingtutorial.domain.Book;
import com.codeonlyocde.reactiveprogrammingtutorial.domain.Review;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class BookService {

      private BookInfoService bookInfoService;
      private ReviewService reviewService;

      public BookService(BookInfoService bookInfoService, ReviewService reviewService) {
        this.bookInfoService = bookInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Book> getBooks(){
        var allBooks = bookInfoService.getBooks();
         return allBooks
                 .flatMap(bookInfo -> {
                     Mono<List<Review>> reviews = reviewService.getReviews(bookInfo.getBookId()).collectList();
                     return reviews.map(review -> new Book(bookInfo,review));
                 })
                 .log();
    }

    public Mono<Book> getBookById(long bookId){
          var book = bookInfoService.getBookById(bookId);
          var review = reviewService.getReviews(bookId).collectList();

        return book.zipWith(review,(b,r) -> new Book(b,r));
    }
}
