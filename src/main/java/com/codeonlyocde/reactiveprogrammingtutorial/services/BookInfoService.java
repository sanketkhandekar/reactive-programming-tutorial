package com.codeonlyocde.reactiveprogrammingtutorial.services;

import com.codeonlyocde.reactiveprogrammingtutorial.domain.BookInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoExtensionsKt;

import java.util.List;

public class BookInfoService {
    public Flux<BookInfo> getBooks(){
        var books = List.of(
                new BookInfo(1,"Book One","Author One","121231"),
                new BookInfo(2,"Book Two","Author Two","423423"),
                new BookInfo(3,"Book Three","Author Three ","2345233")
        );
        return Flux.fromIterable(books);
    }
    public Mono<BookInfo> getBookById(long bookId){
        var book = new  BookInfo(bookId,"Book one","Author one","121321");
        return  Mono.just(book);
    }
}
