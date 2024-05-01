package com.bookwise.bookwise.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
public  class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Column(name = "author", length = 50, nullable = false)
    private String author;

    @Column(name = "publication_year", nullable = false)
    @Digits(integer = 4, fraction = 0)
    private Short publication_year;

    @Column(name = "isbn", length = 50, unique = true, nullable = false)
    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$")
    private String isbn;

    @OneToMany(mappedBy = "book")
    Set<BorrowingRecord> borrowingRecords;

    public Book(Long id, String title, String author, Short publication_year, String isbn) {
        this.title = title;
        this.author = author;
        this.publication_year = publication_year;
        this.isbn = isbn;
    }

    public Book() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Short getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(Short publication_year) {
        this.publication_year = publication_year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Set<BorrowingRecord> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(Set<BorrowingRecord> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }

    public void setEqualsTo(Book book) {
        this.setTitle(book.getTitle());
        this.setAuthor(book.getAuthor());
        this.setPublication_year(book.getPublication_year());
        this.setIsbn(book.getIsbn());
    }
}