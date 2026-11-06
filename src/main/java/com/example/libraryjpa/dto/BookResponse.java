package com.example.libraryjpa.dto;

import com.example.libraryjpa.entity.Book;

public record BookResponse(Long id, String title, String author, int publishedYear, boolean available) {

    // mapping: entity -> DTO
    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublishedYear(),
                book.isAvailable());
    }
}
