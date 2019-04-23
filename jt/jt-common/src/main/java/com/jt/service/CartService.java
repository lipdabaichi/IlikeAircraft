package com.jt.service;

import com.jt.pojo.Cart;
import com.jt.pojo.Order;

import java.util.List;

public interface CartService {
     List<Cart> findCartListByUserId(Long userId) ;
     void updateCartNum(Cart cart);
     void saveCart(Cart cart);

}
