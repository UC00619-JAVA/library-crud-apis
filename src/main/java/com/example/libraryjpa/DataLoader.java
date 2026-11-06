package com.example.libraryjpa;

import com.example.libraryjpa.entity.Book;
import com.example.libraryjpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Only inserts the 4 books the first time.
@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(String... args) {
        if (bookRepository.count() == 0) {
            bookRepository.save(new Book("Clean Code", "Robert C. Martin", 2008));
            bookRepository.save(new Book("The Clean Coder", "Robert C. Martin", 2011));
            bookRepository.save(new Book("Effective Java", "Joshua Bloch", 2018));
            bookRepository.save(new Book("Head First Java", "Kathy Sierra", 2005));
        }
        log.info("Library API ready with {} books: http://localhost:8080/api/books", bookRepository.count());
    }
}
