package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.pojo.SysResult;
import com.jt.service.CartService;
import com.jt.service.OrderService;
import com.jt.util.UserThreadLocal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {
    @Reference(timeout = 3000, check = false)
    private CartService cartService;
    @Reference(timeout = 3000, check = false)
    private OrderService orderService;


    //跳转订单确定页面
    @RequestMapping("create")
    public String show(Model model){
        //根据用户信息查询购物车记录
        long userId = UserThreadLocal.get().getId();
        List<Cart> carts = cartService.findCartListByUserId(userId);
        model.addAttribute("carts", carts);
        return "order-cart";
    }

    @RequestMapping("submit")
    @ResponseBody
    public SysResult saveOrder(Order order) {
        try {
            Long userId = UserThreadLocal.get().getId();
            order.setUserId(userId);
            String orderId = orderService.saveOrder(order);
            if (!StringUtils.isEmpty(orderId)) {
                return SysResult.ok(orderId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.fail();
        }
        return SysResult.fail();
    }

    @RequestMapping("success")
    public String findOrderById(String id,Model model) {
        Order order = orderService.findOrderById(id);
        model.addAttribute("order", order);
        return "success";
    }

}
