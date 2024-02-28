package hellojpa;

import hellojpa.valueType.embedded.Address;
import hellojpa.valueType.embedded.Period;
import hellojpa.inheritance.Item;
import hellojpa.inheritance.Movie;
import hellojpa.valueType.primitive.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");  //EntityManagerFactory은 애플리케이션당 1개만 있어야함.
        EntityManager em = emf.createEntityManager();  //EntityManager 여러개 생성가능 : DatabaseConnection 받는것과 비슷함
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            /**
             *  1 : 다 연관관계
             */
//            oneToMany(em);
            /**
             * 상속관계
             */
//            inheritance(em);
            /**
             * @MappedSuperclass 사용하기
             */
            //mappedSuperClass(em);
            /**
             * 프록시 객체의 특징
             * 1. 프록시 객체는 처음 사용할 때 한번만 초기화
             * 2. 프록시 객체를 초기화할때, 프록시 객체가 실제 엔티티로 바뀌는 것이 아니다.
             * 3. 프록시 객체는 원본 엔티티를 상속 받음 (따라서 객체 비교시에는 instance of 를 사용해야함)
             * 4. 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
             * 5. 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제발생
             */
//            proxy(em, member);
            /**
             * Team3 Entity에 설정
             * 지연로딩, LAZY LOADING
             * 즉시로딩, EAGER LOADING  --> 실무에서는 지양해야함!
             */
            // lazy_eager(em,tx);
            /**
             * cascade = CascadeType.ALL -> Parent1 Entity에 설정
             * 옵션 : ALL(모두), PERSIST(영속), REMOVE(삭제)
             *
             * 고아 객체 제거 : 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
             *
             */
//            cascade_orphanRemoval(em);

            /**
             * 임베디드 타입 사용하기
             * 0. 임베디드 타입은 값타입이다
             * 1. 임베디드 타입은 엔티티의 값일 뿐이다
             * 2. 임베디드 타입을 사용해도 매핑하는 테이블은 같다.
             */
//            embedded(em);

            //  값타입공유(em);


            /**
             * value 타입의 비교는 equals를 override해서 사용해야 한다.
             */
//            값타입비교();



            /**
             * 값타입 컬렉션의 제약사항
             * 1. 값타입 엔티티는 식별자 개념이 없다-> 추적이 어렵다.
             * 2. 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된
             * 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두
             * 다시 저장한다.
             * 3. 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본
             * 키를 구성해야 함: null 입력X, 중복 저장X
             *
             * 실무에서의 대안 : 일대다 관계를 고려
             *  - 일대다 관계를 위한 엔티티 만들고, 여기에서 값 타입 사용
             */
//            값타입컬렉션제약사항(em);


//            printMember(member);
//            printMemberAndTeam(member);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            tx.rollback();
            System.out.println("흠");
        } finally {
            em.close();
        }
        emf.close(); //WAS 가 내려갈때 EntityManagerFactory 를 닫아주어야한다.
    }














   /*
    private static void cascade_orphanRemoval(EntityManager em) {

        Child1 child1 = new Child1();
        Child1 child2 = new Child1();

        Parent1 parent = new Parent1();
        parent.addChild(child1);
        parent.addChild(child2);

        em.persist(parent);
//        em.persist(child1);
//        em.persist(child2);

        em.flush();
        em.clear();

        Parent1 findParent = em.find(Parent1.class, parent.getId());
//        findParent.getChildList().remove(0); //delete 쿼리 실행
        em.remove(findParent);
    }
    */

}
