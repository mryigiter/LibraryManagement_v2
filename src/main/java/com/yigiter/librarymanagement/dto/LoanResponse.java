package com.yigiter.librarymanagement.dto;

import com.yigiter.librarymanagement.domain.Book;
import com.yigiter.librarymanagement.domain.Borrower;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {
    private boolean status;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String bookTitle;
    private String borrowerEmail;
}
