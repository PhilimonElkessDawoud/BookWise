# BookWise

BookWise is a comprehensive library management system designed to facilitate the management of book inventories, patron interactions, and borrowing records. It provides a robust set of RESTful APIs to perform various operations related to books and patrons.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **JDK 22**: Download and install from [Oracle JDK](https://www.oracle.com/eg/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/install/).
- **IntelliJ IDEA Community Edition 2024**: Download from [JetBrains](https://www.jetbrains.com/idea/download/).
- **MySQL Server and Workbench**: Download from [MySQL Downloads](https://www.mysql.com/downloads/).
- **Postman**: Download from [Postman](https://www.postman.com/downloads/).

## Dependencies

This project was initialized using [Spring Initializr](https://start.spring.io/). The following dependencies are included:

- **Spring Web**: For building web applications.
- **Spring Data JPA**: For database integration using Java Persistence API.
- **MySQL Driver**: To connect to MySQL databases.
- **Spring Boot DevTools**: For development-time features like automatic restarts.
- **Spring Cache Abstraction**: For caching support.
- **Validation**: For validating bean properties.

## IMPORTANT NOTE
Don't forget to change your "username", "password" and "schema name" concerning the configuration variables of the database in the file "BookWise/src/main/resources
/application.properties". 

## APIs

BookWise implements RESTful endpoints to handle the following operations:

### Book Management Endpoints

- `GET /api/books`: Retrieve a list of all books.
- `GET /api/books/{id}`: Retrieve details of a specific book by ID.
- `POST /api/books`: Add a new book to the library.
- `PUT /api/books/{id}`: Update an existing book's information.
- `DELETE /api/books/{id}`: Remove a book from the library.

### Patron Management Endpoints

- `GET /api/patrons`: Retrieve a list of all patrons.
- `GET /api/patrons/{id}`: Retrieve details of a specific patron by ID.
- `POST /api/patrons`: Add a new patron to the system.
- `PUT /api/patrons/{id}`: Update an existing patron's information.
- `DELETE /api/patrons/{id}`: Remove a patron from the system.

### Borrowing Endpoints

- `POST /api/borrow/{bookId}/patron/{patronId}`: Allow a patron to borrow a book.
- `PUT /api/return/{bookId}/patron/{patronId}`: Record the return of a borrowed book by a patron.

## Features

- **Validation**: Ensures that all user input is valid.
- **Error Handling**: Provides meaningful error messages to the client.
- **Transaction Management**: Ensures data integrity and consistency.
- **Testing**: Includes unit and integration tests to ensure functionality.
- **Bonus: Caching**: to improve performance.
