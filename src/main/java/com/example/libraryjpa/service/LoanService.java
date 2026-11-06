package com.example.libraryjpa.service;

import com.example.libraryjpa.entity.Book;
import com.example.libraryjpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanService {

    private final BookRepository bookRepository;

    @Transactional
    public void borrow(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new IllegalArgumentException("Book not found: " + id);
        }
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book already borrowed: " + book.getTitle());
        }
        book.setAvailable(false);
        bookRepository.save(book);
        log.info("Borrowed: {}", book.getTitle());
    }

    @Transactional
    public void borrowAll(List<Long> ids) {
        for (Long id : ids) {
            borrow(id);
        }
    }

    @Transactional
    public void returnAll() {
        bookRepository.markAllAvailable();
    }
}
