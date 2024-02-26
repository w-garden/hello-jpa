package jpabook.model.domain;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {
    private Date createDate;
    private Date lastModified;
}
