package jpabook.domain;

import jakarta.persistence.*;

@Entity
public class Movie extends Item {
    private String director;
    private String actor;
}
