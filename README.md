# Library_Management_System
Library management system using Java, Springboot , H2database.
## There Are 3 controllers in this project
### Book Controller with the following endpoints
● GET /api/books: Retrieve a list of all books.
● GET /api/books/{id}: Retrieve details of a specific book by ID.
● POST /api/books: Add a new book to the library.
● PUT /api/books/{id}: Update an existing book's information.
● DELETE /api/books/{id}: Remove a book from the library

Note: To deal with the POST and PUT endpoints you will have to send A Book Json in the Body
example : {
  "title": "Sample Book",
  "author": "John Doe",
  "publicationYear": 2022,
  "isbn": "978-1-23456-789-0"
}

### Patron Controller with the following endpoints
● GET /api/patrons: Retrieve a list of all patrons.

● GET /api/patrons/{id}: Retrieve details of a specific patron by ID.
● POST /api/patrons: Add a new patron to the system.
● PUT /api/patrons/{id}: Update an existing patron's information.
● DELETE /api/patrons/{id}: Remove a patron from the system.

Note: To deal with the POST and PUT endpoints you will have to send A Patron Json in the Body
example : {
    "name": "John Doe",
    "contactInformation": "johndoe@example.com"
}

### Borrowing Record Controller with the following endpoints
● POST /api/borrow/{bookId}/patron/{patronId}: Allow a patron to
borrow a book.
● PUT /api/return/{bookId}/patron/{patronId}: Record the return of a borrowed book by a patron.
