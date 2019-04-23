package com.jt.service;

import com.jt.pojo.Order;

public interface OrderService {
    String saveOrder(Order order);
    Order findOrderById(String id);
}
