import jpql.Address;
import jpql.Member;
import jpql.MemberDTO;
import jpql.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.*;

import java.util.List;

import static jpql.MemberType.USER;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class JpqlTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    @BeforeEach
    public void savedMemberAndTeam() {
        tx.begin();

        Team team = new Team();
        team.setName("팀A");
        em.persist(team);

        for (int i = 1; i <= 20; i++) {
            Member member = new Member();
            member.setUsername("회원" + i);
            member.setAge((int) (Math.random() * 50) + 1);
            member.setType(USER);
            member.setTeam(team);
            em.persist(member);
        }
        em.clear();
    }

    @AfterEach
    public void commit() {
        tx.commit();
    }

    @Test
    public void basicJPQLTest() {
        tx.begin();
        System.out.println("==================================           basicJPQL 실행          ==================================");
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        assertThat(result.size(), equalTo(20));
        final Member member = result.get(0);
        assertThat(member.getUsername(), equalTo("회원1"));
    }

    @Test
    public void typeQueryTest() {

        System.out.println("==================================           typeQuery.query1          ==================================");
        TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
        List<Member> resultList = query1.getResultList();
        assertThat(resultList.size(), equalTo(20));

        System.out.println("==================================           typeQuery.query2          ==================================");
        TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
        List<String> resultList2 = query2.getResultList();

        for (String string : resultList2) {
            assertThat(string, containsString("회원"));
        }

        System.out.println("==================================           typeQuery.query3          ==================================");
        Query query3 = em.createQuery("select m.username, m.age, m.team.name from Member m");
        List resultList3 = query3.getResultList();

        for (Object o : resultList3) {
            Object[] result = (Object[]) o;
            assertThat((String) result[0], containsString("회원"));
            assertThat((int) result[1], lessThan(51));
            assertThat((String) result[2], containsString("팀A"));

        }

        System.out.println("==================================           typeQuery.query4          ==================================");
        TypedQuery<Member> query4 = em.createQuery("select m FROM Member m WHERE username = :username", Member.class);
        Member resultList4 = query4.setParameter("username", "회원1").getSingleResult();
        assertThat(resultList4.getUsername(), is("회원1"));

    }

    @Test
    public void parameterBindingTest() {
        System.out.println("==============================           parameterBinding.Named parameters          ================================");
        Member result = em.createQuery("select m from Member m where m.username= :username", Member.class)
                .setParameter("username", "회원2")
                .getSingleResult();
        assertThat(result.getUsername(), is("회원2"));

        System.out.println("==============================           parameterBinding.Positional parameters       ===============================");
        Member result1 = em.createQuery("select m from Member m where m.username= ?1", Member.class)
                .setParameter(1, "회원3")
                .getSingleResult();
        assertThat(result1.getUsername(), is("회원3"));
    }

    @Test
    public void projectionTest() {
        em.clear();
        System.out.println("============================         projectionTest       =============================");
        Query query = em.createQuery("select m.username, m.age, m.team from Member m where m.username =:username");
        List<Object[]> list = query.setParameter("username", "회원4").getResultList();
        for (Object[] objects : list) {
            assertThat(objects[0], instanceOf(String.class));
            assertThat(objects[1], instanceOf(Integer.class));
            assertThat(objects[2], instanceOf(Team.class));
        }

        System.out.println("============================         projectionTest       =============================");
        TypedQuery<MemberDTO> typedQuery = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m where m.username=:username", MemberDTO.class)
                .setParameter("username", "회원10");
        MemberDTO memberDTO = typedQuery.getSingleResult();
        assertThat(memberDTO.getUsername(), is("회원10"));

    }

    @Test
    public void pagingTest() {
        System.out.println("=================== pagingTest ===================");
        List<Member> result = em.createQuery("select m from Member m order by m.id", Member.class)
                .setFirstResult(4)
                .setMaxResults(7)
                .getResultList();
        assertThat(result.size(), is(7));
        for (Member member1 : result) {
            assertThat(member1.getUsername(), containsString("회원"));
        }
    }

    @Test
    public void joinTest() {
        System.out.println("=================   innerJoin   ================");
        String query = "select m, t from Member m inner join m.team t where t.name =: name";
        List<Object[]> innerJoin = em.createQuery(query).setParameter("name", "팀A").getResultList();
        for (Object[] row : innerJoin) {
            assertThat(row[0], instanceOf(Member.class));
            assertThat(row[1], instanceOf(Team.class));
        }


        Member member = em.find(Member.class, 1L);
        member.setTeam(null);

        System.out.println("=================   outerJoin   ================");
        long innerJoinCnt = (long) em.createQuery("SELECT count(m) from Member m JOIN m.team t").getSingleResult();
        long outerJoinCnt = (long) em.createQuery("SELECT count(m) from Member m LEFT JOIN m.team t").getSingleResult();
        assertThat(innerJoinCnt, is(19L));
        assertThat(outerJoinCnt, is(20L));

        System.out.println("=================   joinOn   ================");
        List<Object[]> joinOn
                = em.createQuery("select substring(m.username,0,2) , t.name, count(*) from Team t left join t.members m on m.username like '회원1%' group by substring(m.username,0,2) , t.name")
                .getResultList();
        for (Object[] row : joinOn) {
            assertThat(row[0], is("회원"));
            assertThat(row[1], is("팀A"));
            assertThat(row[2], is(10L));
        }

        System.out.println("================= thetaJoin ================");
        Member findMember = em.find(Member.class, 10L);
        findMember.setUsername("팀A");
        Member nonRelationJoin = em.createQuery("select m from Member m, Team t where m.username = t.name", Member.class)
                .getSingleResult();
        assertThat(nonRelationJoin.getUsername(), is("팀A"));
        assertThat(nonRelationJoin.getTeam().getName(), is("팀A"));

    }

    @Test
    public void fetchJoinTest() {
        System.out.println("================== entity fetchJoin ===================");
        Member result =
                em.createQuery("select m From Member m join fetch m.team where m.username LIKE '회원12'", Member.class)
                        .getSingleResult();
        assertThat(result.getTeam().getName(), equalTo("팀A"));

        Team newTeam = new Team();
        newTeam.setName("팀B");
        em.persist(newTeam);
        Member findMember1 = em.find(Member.class, 10L);
        findMember1.changeTeam(newTeam);
        Member findMember2 = em.find(Member.class, 11L);
        findMember2.changeTeam(newTeam);
        Member findMember3 = em.find(Member.class, 12L);
        findMember3.changeTeam(newTeam);

        System.out.println("================== collection fetchJoin ===================");
        List<Team> result2 =
                em.createQuery("select t From Team t join fetch t.members where t.name ='팀B'", Team.class)
                        .getResultList();

        for (Team team : result2) {
            System.out.println("teamName = " + team.getName() + " | team = " + team);
            for (Member member : team.getMembers()) {
                System.out.println("-> member = " + member);
            }
            System.out.println();
            assertThat(team.getMembers().size(), is(3));
        }
    }

    @Test
    @DisplayName("기본키 엔티티 파라미터와 PK 파라미터")
    public void primaryKeyValueTest() {
        Member member = em.find(Member.class, 5L);
        System.out.println("================== entity parameter  ===================");
        List resultList = em.createQuery("select m from Member m where m=:member").setParameter("member", member)
                .getResultList();
        System.out.println("================== primary key parameter  ===================");
        List resultList1 = em.createQuery("select m from Member m where m.id=:memberId").setParameter("memberId", 5L)
                .getResultList();
        assertThat(resultList, equalTo(resultList1));
    }

    @Test
    @DisplayName("외래키 엔티티 파라미터와 PK 파라미터")
    public void foreignKeyValueTest() {
        Team team = em.find(Team.class, 1L);

        List resultList = em.createQuery("select m from Member m where m.team =:team").setParameter("team", team)
                .getResultList();
        List resultList1 = em.createQuery("select m from Member m where m.team.id=:teamId").setParameter("teamId", 1L)
                .getResultList();
        assertThat(resultList, equalTo(resultList1));
    }

    @Test
    public void namedQueryTest() {
        System.out.println("==================   named Query Annotation Test  ===================");
        Member member = em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", "회원1")
                .getSingleResult();
        assertThat(member.getUsername(),is("회원1"));

        long count = em.createNamedQuery("Member.totalCount", Long.class)
                .getSingleResult();
        assertThat(count,is(20L));

        System.out.println("==================   named Query XML Test  ===================");
        List<Team> resultList = em.createNamedQuery("Team.findByTeamName", Team.class).setParameter("name", "팀1")
                .getResultList();
        for (Team team : resultList) {
            assertThat(team.getName(),is("팀1"));
        }
        Long singleResult = em.createNamedQuery("Team.totalCount", Long.class).getSingleResult();
        assertThat(singleResult,is(1L));


    }


}
