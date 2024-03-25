import jakarta.persistence.*;
import javax.sql.DataSource;
import jakarta.transaction.Transactional;
import jpabook.config.AppConfig;
import jpabook.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class JpaTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    DataSource dataSource;

    @Autowired
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;

    @Autowired
    private ApplicationContext appContext;
    @Test
    public void beanTest(){
        final String[] beanDefinitionNames = appContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
    @Test
    public void test() {

        Member member = new Member();
        member.setName("신호철");
        em.persist(member);
        Member member1 = em.find(Member.class, 1L);
        System.out.println(member1.getName());





    }

}
