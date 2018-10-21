package cn.zyblogs;

import cn.zyblogs.entity.Order;
import cn.zyblogs.producter.OrderSender;
import cn.zyblogs.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerRabbitApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private OrderSender orderSender;
    @Autowired
    private OrderService orderService;

    @Autowired
    private Sid sid;

    @Test
    public void testCreateOrder() throws Exception {
        Order order = new Order();
        order.setId(sid.nextShort());
        order.setName("测试创建订单");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        orderService.create(order);
    }

    @Test
    public void testSend1() throws Exception {
        Order order = new Order();
        order.setId("20180818000000001");
        order.setName("测试订单1");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());

        orderSender.send(order);
    }

}
