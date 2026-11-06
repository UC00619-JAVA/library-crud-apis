package com.example.libraryjpa.service;

import com.example.libraryjpa.dto.BookRequest;
import com.example.libraryjpa.dto.BookResponse;
import com.example.libraryjpa.exception.BookNotFoundException;
import com.example.libraryjpa.entity.Book;
import com.example.libraryjpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// The business logic lives here - not in the controller
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookResponse> findAll(String author) {
        List<Book> books;
        if (author == null) {
            books = bookRepository.findAll();
        } else {
            books = bookRepository.findByAuthor(author);
        }
        return toResponses(books);
    }

    public List<BookResponse> findAvailable() {
        return toResponses(bookRepository.findAvailableBooks());
    }

    public BookResponse findById(Long id) {
        return BookResponse.from(getBook(id));
    }

    public BookResponse create(BookRequest request) {
        Book book = new Book(request.title(), request.author(), request.publishedYear());
        return BookResponse.from(bookRepository.save(book));
    }

    public BookResponse update(Long id, BookRequest request) {
        Book book = getBook(id);
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setPublishedYear(request.publishedYear());
        return BookResponse.from(bookRepository.save(book));
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    // finds the book or throws BookNotFoundException (-> 404)
    private Book getBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return book;
    }

    private List<BookResponse> toResponses(List<Book> books) {
        List<BookResponse> result = new ArrayList<>();
        for (Book book : books) {
            result.add(BookResponse.from(book));
        }
        return result;
    }
}
