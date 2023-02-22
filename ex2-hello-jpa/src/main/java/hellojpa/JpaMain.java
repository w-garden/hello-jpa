package hellojpa;

import hellojpa.ManyOne.Member01;
import hellojpa.ManyOne.Team01;
import hellojpa.cascade.Child;
import hellojpa.cascade.Parent;
import hellojpa.inheritance.Item;
import hellojpa.inheritance.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");  //EntityManagerFactory은 애플리케이션당 1개만 있어야함.
        EntityManager em = emf.createEntityManager();  //EntityManager 여러개 생성가능 : DatabaseConnection 받는것과 비슷함
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            /**
             *  연관관계가 없음
             *   팀저장
             */
//            notMapping(em);
            /**
             *   //양방향 매핑 : 연관관계의 주인에 값 설정
             *   //권장사항 : 양쪽에 값 설정을 다 하자 -> 연관관계 편의 메서드로 처리
             *   //팀저장
             */
//            mapping_relation(em);
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
             * Team Entity에 설정
             * 지연로딩, LAZY LOADING
             * 즉시로딩, EAGER LOADING  --> 실무에서는 지양해야함!
             */
            // lazy_eager(em,tx);
            /**
             * cascade = CascadeType.ALL -> Parent Entity에 설정
             * 옵션 : ALL(모두), PERSIST(영속), REMOVE(삭제)
             *
             * 고아 객체 제거 : 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
             *
             */
            cascade_orphanRemoval(em);


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
    private static void printMember(Member member) {
        System.out.println("member = "+ member.getUsername());
    }
    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username =" + username);

        Team team = member.getTeam();
        System.out.println("team = "+ team.getName());
    }
    private static void notMapping(EntityManager em) {
      /*  Team team =new Team();
        team.setName("TeamA");
        em.persist(team);
        //회원저장
        Member member = new Member();
        member.setUsername("member1");
        member.setTeamId(team.getId());
        em.persist(member);
        //조회
        Member01 findMember = em.find(Member01.class, member.getId());


        Long findTeamId = findMember.getTeamId();
        Team findTeam = em.find(Team.class, findTeamId); */
    }
    private static void mapping_relation(EntityManager em) {
        Team01 team =new Team01();
        team.setName("TeamA");
        em.persist(team);

        //회원저장
        Member01 member = new Member01();
        member.setUsername("member1");
//            member.changeTeam(team); //team 값설정  -> 연관관계 편의 메서드로 처리
        em.persist(member);
//            team.getMembers().add(member); //member 값설정 -> 연관관계 편의 메서드로 처리
        team.addMember(member);
//            em.flush();
//            em.clear();

        //조회(양방향 조회)
        Member findMember = em.find(Member.class, member.getId());

        //Member02 -> Team02
        Team findTeam = findMember.getTeam();
        System.out.println("findTeam =" + findTeam.getName());
        System.out.println("=========================");
        Team findTeam2 = em.find(Team.class, team.getId());
        List<Member> members2 = findTeam2.getMembers();
        for (Member m2 : members2) {
            System.out.println("m2 = " + m2.getUsername());
        }
        System.out.println("=========================");
        //Team02 -> Member02
        List<Member> members = findMember.getTeam().getMembers();
        for (Member m : members) {
            System.out.println("m = " + m.getUsername());
        }
        System.out.println("=========================");
    }

    private static void oneToMany(EntityManager em) {
        Member member = saveMember(em);

        Team team = new Team();
        team.setName("teamA");

        team.getMembers().add(member);

        em.persist(team);
    }
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
    private static void mappedSuperClass(EntityManager em) {
        Member member = new Member();
        member.setUsername("user1");
        member.setCreatedBy("SHIN");
        member.setCreateDate(LocalDateTime.now());


        Member member2= new Member();
        member2.setUsername("hello");

        em.persist(member);
        em.flush();
        em.clear();

//          Member findMember = em.find(Member.class, member.getId());
    }
    private static void proxy(EntityManager em, Member member) {
        Member findMember = em.getReference(Member.class, member.getId()); //DB 쿼리 실행 안함
        System.out.println("findMember.getClass() = " + findMember.getClass());  //프록시(가짜) 클래스 class hellojpa.Member$HibernateProxy$FMWUKLLF
        System.out.println("findMember.getId() = " + findMember.getId());
        System.out.println("findMember.getUsername() = " + findMember.getUsername());
        System.out.println("findMember.getUsername() = " + findMember.getUsername());
    }
    private static Member saveMember(EntityManager em) {
        Member member = new Member();
        member.setUsername("member1");
        em.persist(member);
        return member;
    }
    private static void lazy_eager(EntityManager em) {
        Team team = new Team();
        team.setName("team1");
        em.persist(team);
        Member member1 = new Member();
        member1.setUsername("member1");
        em.persist(member1);
        member1.setTeam(team);

        em.flush();
        em.clear();

        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

        Member m = em.find(Member.class, member1.getId());
        System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());
        //지연로딩 : 프록시로 조회됨. 즉시로딩 : 실제 Entity 조회

        System.out.println("============================");
        m.getTeam().getName();  //지연로딩 : 프록시 초기화, DB 쿼리 실행
        System.out.println("============================");
    }
    private static void cascade_orphanRemoval(EntityManager em) {

        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();
        parent.addChild(child1);
        parent.addChild(child2);

        em.persist(parent);
//        em.persist(child1);
//        em.persist(child2);

        em.flush();
        em.clear();

        Parent findParent = em.find(Parent.class, parent.getId());
//        findParent.getChildList().remove(0); //delete 쿼리 실행
        em.remove(findParent);
    }
}
