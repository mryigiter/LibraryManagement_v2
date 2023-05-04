package com.yigiter.librarymanagement.service;


import com.yigiter.librarymanagement.domain.Book;
import com.yigiter.librarymanagement.dto.BookDTO;
import com.yigiter.librarymanagement.exception.BadRequestException;
import com.yigiter.librarymanagement.exception.ErrorMessage;
import com.yigiter.librarymanagement.exception.ResourceNotFoundException;
import com.yigiter.librarymanagement.exception.ResponseMessage;
import com.yigiter.librarymanagement.repository.BookRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final LoanService loanService;
    private final BookRepository bookRepository;
    public BookService(@Lazy LoanService loanService, BookRepository bookRepository) {
        this.loanService = loanService;
        this.bookRepository = bookRepository;
    }
    public BookDTO createBook(BookDTO bookDTO) {
       boolean title= bookRepository.existsByTitle(bookDTO.getTitle());
       boolean isbn =bookRepository.existsByIsbn(bookDTO.getIsbn());
        if (title || isbn) {
            throw new ResourceNotFoundException(String.format(ErrorMessage.THE_BOOK_ALREADY_CREATED_MESSAGE));
        }
        Book book = bookDTOToBook(bookDTO);
        bookRepository.save(book);
        return bookDTO;
    }
    public BookDTO getBook(String title) {
        Book book= callBook(title);
         BookDTO bookDTO =bookToBookDTO(book);
         return bookDTO;
    }
    public String removeBook(String title) {
        Book book= callBook(title);
        boolean loanStatus= loanService.getLoanByBook(title);
            if (!loanStatus){
               throw new BadRequestException(String.format(ErrorMessage.THE_BOOK_LOAN_STATUS_MESSAGE));
            }
        bookRepository.delete(book);
        return ResponseMessage.BOOK_DELETED_MESSAGE;


    }
    public List<BookDTO> getAllBooks() {
        List<Book> bookList =bookRepository.findAll();
        List<BookDTO> bookDTOList = new ArrayList<BookDTO>();
        for (Book book :bookList ) {
            bookDTOList.add(bookToBookDTO(book));
        }
        return bookDTOList;
    }

    //Assist Methods
    public Book callBook( String title){
        Book book= bookRepository.findByTitle(title).orElseThrow(
                ()->new ResourceNotFoundException(String.format(ErrorMessage.NOT_AVAILABLE_BOOK,title))
        );
        return book;
    }

    //convert methods
    private Book bookDTOToBook(BookDTO bookDTO){
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setAvailableCopies(bookDTO.getAvailableCopies());
        return book;
    }
    private  BookDTO bookToBookDTO(Book book){
            BookDTO bookDTO =new BookDTO();
            bookDTO.setTitle(book.getTitle());
            bookDTO.setAuthor(book.getAuthor());
            bookDTO.setIsbn(book.getIsbn());
            bookDTO.setAvailableCopies(book.getAvailableCopies());
            return bookDTO;
    }


}









