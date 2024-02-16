package hellojpa.manyMany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 다대다 추천하지 않음
 * RDBMS는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없음
 *
 * 연결 테이블을 추가해서(연결 테이블을 Entity 로 승격시키고)
 * 일대다, 다대일 관계로 풀어내야함
 *
 */
//@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name ="USERNAME")
    private String username;

    @ManyToMany
    @JoinTable(name="MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();
}
