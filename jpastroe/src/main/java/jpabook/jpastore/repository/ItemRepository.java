package jpabook.jpastore.repository;

import jpabook.jpastore.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null)
            em.persist(item);
        else
            em.merge(item); //merge 란 ? 준영속 상태의 Entity 를 영속상태의 Entity 로 넘김
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
