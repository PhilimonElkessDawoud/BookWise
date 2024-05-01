package com.bookwise.bookwise.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "name", length = 200, nullable = false)
    @NotBlank(message = "Please provide a username")
    private String name;

    @Column(name = "phone", length = 20, unique = true, nullable = false)
    @Size(min = 10, message = "Phone Number must be 10 digits.")
    private String phone;

    @Email(message = "Please provide a valid email address")
    @Column(name = "email", length = 50, unique = true, nullable = false)
    private String email;

    @Column(name = "address", length = 500, nullable = false)
    private String address;

    @OneToMany(mappedBy = "patron")
    Set<BorrowingRecord> borrowingRecords;

    public Patron(Long id, String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Patron() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<BorrowingRecord> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(Set<BorrowingRecord> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }

    public void setEqualsTo(Patron patron) {
        this.setName(patron.getName());
        this.setPhone(patron.getPhone());
        this.setEmail(patron.getEmail());
        this.setAddress(patron.getAddress());
    }
}