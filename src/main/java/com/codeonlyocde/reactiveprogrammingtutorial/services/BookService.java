package com.codeonlyocde.reactiveprogrammingtutorial.services;

import com.codeonlyocde.reactiveprogrammingtutorial.domain.Book;
import com.codeonlyocde.reactiveprogrammingtutorial.domain.Review;
import com.codeonlyocde.reactiveprogrammingtutorial.exception.BookExcepion;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
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
                 .onErrorMap(throwable -> {
                     log.error("throwable "+throwable);
                     return new BookExcepion("Exception occured while reading books");
                 })
                 .log();
    }

    public Flux<Book> getBooksRetry(){
        var allBooks = bookInfoService.getBooks();
        return allBooks
                .flatMap(bookInfo -> {
                    Mono<List<Review>> reviews = reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews.map(review -> new Book(bookInfo,review));
                })
                .onErrorMap(throwable -> {
                    log.error("throwable "+throwable);
                    return new BookExcepion("Exception occured while reading books");
                })
                .retry(3)
                .log();
    }

    public Flux<Book> getBooksRetryWhen(){

       // var retrySpec = getRetryExhaustedThrow();
        var allBooks = bookInfoService.getBooks();
        return allBooks
                .flatMap(bookInfo -> {
                    Mono<List<Review>> reviews = reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews.map(review -> new Book(bookInfo,review));
                })
                .onErrorMap(throwable -> {
                    log.error("throwable "+throwable);
                    return new BookExcepion("Exception occured while reading books");
                })
                .retryWhen(getRetryExhaustedThrow())
                .log();
    }

    private RetryBackoffSpec getRetryExhaustedThrow() {
        return Retry.backoff
                        (3, Duration.ofMillis(1000))
                .filter(throwable -> throwable instanceof BookExcepion)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                        Exceptions.propagate(retrySignal.failure()));
    }

    public Mono<Book> getBookById(long bookId){
          var book = bookInfoService.getBookById(bookId);
          var review = reviewService.getReviews(bookId).collectList();

        return book.zipWith(review,(b,r) -> new Book(b,r));
    }
}
