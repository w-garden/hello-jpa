package jpabook.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;


//@Entity
@Getter
@Setter
public class Book extends Item {
    private String author;
    private String isbn;
}
