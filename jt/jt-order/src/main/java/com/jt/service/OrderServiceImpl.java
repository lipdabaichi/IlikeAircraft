package com.jt.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;

	/**
	 * 分析:提交一次订单,入库三张表
	 * @param order
	 * @return
	 */
	@Override
	@Transactional   //切记增加事务
	public String saveOrder(Order order) {
		Date date = new Date();
		//1.准备orderId
		String orderId = "" + order.getUserId() + System.currentTimeMillis();
		order.setOrderId(orderId)
				.setStatus(1)
				.setCreated(date)
				.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单入库成功!!!");

		//2.入库订单物流信息
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping
				.setOrderId(orderId)
				.setCreated(date)
				.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流入库成功!!!!");

		//3.入库订单商品
		List<OrderItem> items = order.getOrderItems();
		for (OrderItem orderItem : items) {
			orderItem.setOrderId(orderId)
					.setCreated(date)
					.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("恭喜你订单入库成功!!!!!");
		return orderId;
	}

	@Override
	public Order findOrderById(String id) {
		Order order = orderMapper.selectById(id);
		OrderShipping orderShipping = orderShippingMapper.selectById(id);
		QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("order_id",id);
		List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
		order.setOrderShipping(orderShipping)
				.setOrderItems(orderItems);
		return order;
	}
}
