package jpa.jpagift.service;

//import jpabook.jpagift.domain.item.Card;
import jpa.jpagift.repository.OrderRepository;
        import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    
    /*@Test
    public void 상품주문() throws Exception {
        //Given
        Member member = new Member();
        member.setId(l);
        member.setName("회원1");
        member.setEmail("1@");
        em.persist(member);

        Member member2 = new Member();
        Long l2 = new Long(5);
        member.setId(l2);
        member.setName("회원2");
        member.setEmail("2@");
        em.persist(member2);

        Item item = createCard("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 2;
        //When
        Long id = orderService.order(member.getEmail(), item.getName(), orderCount, member2.getEmail());
        //Then
        Order getOrder = orderRepository.findOne(id);
        assertEquals("상품 주문시 상태는 ORDER",OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.",1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * 2, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",8, item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //Given
        Member member = createMember("1");
        Member member2 = createMember("2");
        Item item = createCard("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 11; //재고보다 많은 수량
        //When
        orderService.order(member.getEmail(), item.getName(), orderCount, member2.getEmail());
        //Then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() {
        //Given
        Member member = createMember("1");
        Member member2 = createMember("2");
        Item item = createCard("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 2;

        Long orderId = orderService.order(member.getEmail(), item.getName(), orderCount, member2.getEmail());
        //When
        orderService.cancelOrder(orderId);
        //Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCEL 이다.",OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());
    }
    
    private Member createMember(String email) {
        Member member = new Member();
        member.setName("회원"+email);
        member.setEmail(email);
        member.setAddress(new Address("서울 ~", "건물 5층", "123-123"));
        em.persist(member);
        return member;
    }
    
    private Card createCard(String name, int price, int stockQuantity) {
        Card card = new Card();
        card.setName(name);
        card.setStockQuantity(stockQuantity);
        card.setPrice(price);

        em.persist(card);
        return card;
    }*/
}