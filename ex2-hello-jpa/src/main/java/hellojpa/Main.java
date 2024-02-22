package hellojpa;

import hellojpa.embedded.Address;
import hellojpa.embedded.Period;
import hellojpa.inheritance.Item;
import hellojpa.inheritance.Movie;

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
            //proxy(em, member);
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
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close(); //WAS 가 내려갈때 EntityManagerFactory 를 닫아주어야한다.
    }

    private static void 값타입컬렉션제약사항(EntityManager em) {
        hellojpa.값타입컬렉션.Member member = new hellojpa.값타입컬렉션.Member();
        member.setUsername("member1");
        member.setHomeAddress(new Address("homeCity", "street", "123456"));

        member.getFavoriteFoods().add("치킨");
        member.getFavoriteFoods().add("족발");
        member.getFavoriteFoods().add("피자");

        member.getAddressHistory().add(new Address("old1", "street", "123456"));
        member.getAddressHistory().add(new Address("old2", "street", "123456"));

        em.persist(member);

        em.flush();
        em.clear();
        System.out.println("====================START================");
        hellojpa.값타입컬렉션.Member findMember = em.find(hellojpa.값타입컬렉션.Member.class, member.getId());

        List<Address> addressHistory = findMember.getAddressHistory();
        for (Address address : addressHistory) {
            System.out.println("address.getCity() = " + address.getCity());
        }
        Set<String> favoriteFoods = findMember.getFavoriteFoods();
        for (String favoriteFood : favoriteFoods) {
            System.out.println("favoriteFood = " + favoriteFood);
        }
        Address a = findMember.getHomeAddress();
        findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));


        //치킨 -> 한식
        findMember.getFavoriteFoods().remove("치킨");
        findMember.getFavoriteFoods().add("한식");

        //주소 바꾸기
        findMember.getAddressHistory().remove(new Address("old1", "street", "123456"));
        findMember.getAddressHistory().add(new Address("newCity1", "street", "123456"));
    }

    private static void 값타입비교() {
        int a =10;
        int b =10;
        System.out.println("a == b :"+(a==b));

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");
        System.out.println("address1 == address2 : " + (address1 == address2));
        System.out.println("address1 equals address2 :" + (address1.equals(address2)));
    }

    private static void 값타입공유(EntityManager em) {
        Address address = new Address("city","street","10000");
        hellojpa.값타입.Member member1 = new hellojpa.값타입.Member();
        member1.setUsername("member1");
        member1.setHomeAddress(address);
        em.persist(member1);

        Address newAddress = new Address("서울", address.getStreet(), address.getZipcode());  // 해결책 : 새롭게 객체를 만들어야함


        hellojpa.값타입.Member member2 = new hellojpa.값타입.Member();
        member2.setUsername("member2");
        member2.setHomeAddress(newAddress);
        em.persist(member2);

//      member1.getHomeAddress().setCity("newCity"); //member1,member2의 값 모두 변경된다--> Entity에서 Setter를 제거함
        em.persist(member1);
    }

    private static void embedded(EntityManager em) {
        hellojpa.embedded.Member member = new hellojpa.embedded.Member();
        member.setUsername("hello");
        member.setHomeAddress(new Address("city", "street", "zipcode"));
        member.setPeriod(new Period());
        em.persist(member);
    }
//
//    private static void printMember(hellojpa.Member3 member) {
//        System.out.println("member = "+ member.getUsername());
//    }
//    private static void printMemberAndTeam(hellojpa.Member3 member) {
//        String username = member.getUsername();
//        System.out.println("username =" + username);
//
//        hellojpa.Team3 team = member.getTeam();
//        System.out.println("team = "+ team.getName());
//    }


    /*

    private static void oneToMany(EntityManager em) {
        hellojpa.Member3 member = saveMember(em);

        hellojpa.Team3 team = new hellojpa.Team3();
        team.setName("teamA");

        team.getMembers().add(member);

        em.persist(team);
    }
    */

    private static void inheritance(EntityManager em) {
        Movie movie = new Movie();
        movie.setDirector("감독1");
        movie.setActor("배우1");
        movie.setName("바람과 함께 사라지다");
        movie.setPrice(10000);
        em.persist(movie);

        em.flush();
        em.clear();

//      Movie findMovie = em.find(Movie.class, movie.getId());
        Item item = em.find(Item.class, movie.getId());
        System.out.println("item = "+ item);
    }
    /*
    private static void mappedSuperClass(EntityManager em) {
        hellojpa.Member3 member = new hellojpa.Member3();
        member.setUsername("user1");
        member.setCreatedBy("SHIN");
        member.setCreateDate(LocalDateTime.now());


        hellojpa.Member3 member2= new hellojpa.Member3();
        member2.setUsername("hello");

        em.persist(member);
        em.flush();
        em.clear();

//          Member3 findMember = em.find(Member3.class, member.getId());
    }
    private static void proxy(EntityManager em, hellojpa.Member3 member) {
        hellojpa.Member3 findMember = em.getReference(hellojpa.Member3.class, member.getId()); //DB 쿼리 실행 안함
        System.out.println("findMember.getClass() = " + findMember.getClass());  //프록시(가짜) 클래스 class hellojpa.Member3$HibernateProxy$FMWUKLLF
        System.out.println("findMember.getId() = " + findMember.getId());
        System.out.println("findMember.getUsername() = " + findMember.getUsername());
        System.out.println("findMember.getUsername() = " + findMember.getUsername());
    }
    private static hellojpa.Member3 saveMember(EntityManager em) {
        hellojpa.Member3 member = new hellojpa.Member3();
        member.setUsername("member1");
        em.persist(member);
        return member;
    }
    private static void lazy_eager(EntityManager em) {
        hellojpa.Team3 team = new hellojpa.Team3();
        team.setName("team1");
        em.persist(team);
        hellojpa.Member3 member1 = new hellojpa.Member3();
        member1.setUsername("member1");
        em.persist(member1);
        member1.setTeam(team);

        em.flush();
        em.clear();

        List<hellojpa.Member3> members = em.createQuery("select m from Member3 m", hellojpa.Member3.class).getResultList();

        hellojpa.Member3 m = em.find(hellojpa.Member3.class, member1.getId());
        System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());
        //지연로딩 : 프록시로 조회됨. 즉시로딩 : 실제 Entity 조회

        System.out.println("============================");
        m.getTeam().getName();  //지연로딩 : 프록시 초기화, DB 쿼리 실행
        System.out.println("============================");
    }
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
