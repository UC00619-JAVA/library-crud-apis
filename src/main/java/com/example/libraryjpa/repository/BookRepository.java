package com.example.libraryjpa.repository;

import com.example.libraryjpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // derived queries (session 11, exercise 3)
    List<Book> findByAuthor(String author);

    List<Book> findByPublishedYearGreaterThan(int year);

    List<Book> findByTitleContainingIgnoreCase(String text);

    // @Query (session 11, exercise 4)
    @Query("SELECT b FROM Book b WHERE b.available = true ORDER BY b.title")
    List<Book> findAvailableBooks();

    @Query(value = "SELECT COUNT(*) FROM books WHERE published_year < :year", nativeQuery = true)
    long countPublishedBefore(@Param("year") int year);

    @Modifying
    @Query("UPDATE Book b SET b.available = true")
    int markAllAvailable();
}
