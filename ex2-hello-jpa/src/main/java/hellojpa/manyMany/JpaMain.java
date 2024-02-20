package hellojpa.manyMany;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            save(em);
            find(em);
            findInverse(em);

            save2(em);
            find2(em);

            save3(em);
            find3(em);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void find3(EntityManager em) {
        Long orderId = 2L;
        Order order = em.find(Order.class, orderId);
        System.out.println();
        Member9 member = order.getMember();
        Product9 product = order.getProduct();
        System.out.println("=============== find3 ===================");
        System.out.println("member = " + member.getUsername());
        System.out.println("product = " + product.getName());
        System.out.println("orderAmount() = " + order.getOrderAmount());
        System.out.println("orderDate() = " + order.getOrderDate());
        System.out.println("=============== find3 ===================");
    }

    private static void save3(EntityManager em) {
        Member9 member = new Member9("member1", "회원1");
        em.persist(member);

        Product9 product = new Product9("productA", "상품1");
        em.persist(product);

        Order order = new Order();
        order.setMember(member);
        order.setProduct(product);
        order.setOrderAmount(4);
        order.setOrderDate(new Date());
        em.persist(order);
    }

    private static void save2(EntityManager em) {
        Member8 member8 = new Member8("memberA", "회원1");
        em.persist(member8);

        Product8 product8 = new Product8("productA", "상품1");
        em.persist(product8);

        MemberProduct memberProduct = new MemberProduct();
        memberProduct.setMember(member8); //주문 회원 - 연관관계 설정
        memberProduct.setProduct(product8); //주문 상품 - 연관관계 설정
        memberProduct.setOrderAmount(2); //주문 수량

        em.persist(memberProduct);
    }

    private static void find2(EntityManager em) {
        MemberProductId memberProductId = new MemberProductId();
        memberProductId.setMember("memberA");
        memberProductId.setProduct("productA");

        MemberProduct memberProduct = em.find(MemberProduct.class, memberProductId);
        Member8 member8 = memberProduct.getMember();
        Product8 product8 = memberProduct.getProduct();
        System.out.println("================= find2 ===================");
        System.out.println("member8 = " + member8.getUsername());
        System.out.println("product8 = " + product8.getName());
        System.out.println("orderAmount = " + memberProduct.getOrderAmount());
        System.out.println("================= find2 ===================");
    }

    private static void findInverse(EntityManager em) {
        Product7 product = em.find(Product7.class, "productA");
        final List<Member7> members = product.getMembers();
        System.out.println("=============== findInverse() ================");
        for (Member7 member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
        }
        System.out.println("=============== findInverse() ================");
    }

    private static void find(EntityManager em) {
        Member7 member = em.find(Member7.class, 1L);
        List<Product7> products = member.getProducts();
        System.out.println("================= find ==================");
        for (Product7 product : products) {
            System.out.println("product.name = " + product.getName());
        }
        System.out.println("================= find ==================");
    }

    private static void save(EntityManager em) {
        Product7 productA = new Product7();
        productA.setId("productA");
        productA.setName("상품A");
        em.persist(productA);

        Member7 member1 = new Member7();
        member1.setUsername("회원1");
        member1.addProduct(productA);
        em.persist(member1);
    }


}
