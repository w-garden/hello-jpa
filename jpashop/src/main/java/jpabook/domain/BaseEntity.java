package jpabook.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
}
