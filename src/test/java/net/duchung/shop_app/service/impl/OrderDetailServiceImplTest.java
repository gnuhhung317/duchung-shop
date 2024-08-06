package net.duchung.shop_app.service.impl;

import net.duchung.shop_app.dto.OrderDetailDto;
import net.duchung.shop_app.service.OrderDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderDetailServiceImplTest {
    @Autowired
    private OrderDetailService orderDetailServiceImpl;
    @Test
    void getOrdersDetailByOrder() {
        System.out.println(orderDetailServiceImpl.getOrdersDetailsByOrder(1l).size());
        for (OrderDetailDto orderDetailDto : orderDetailServiceImpl.getOrdersDetailsByOrder(1l)) {
            System.out.println(orderDetailDto.getOrderId());
        }
    }

}