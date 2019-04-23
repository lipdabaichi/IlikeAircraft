package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.SysResult;
import com.jt.pojo.User;
import com.jt.service.CartService;
import com.jt.util.UserThreadLocal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("cart")
public class CartController {

    @Reference(timeout = 3000,check = false)
    private CartService cartService;
    //实现购物车列表数据展现
    @RequestMapping("show")
    public String show(Model model){
//        Long userId = 7L;   //暂时写死
        Long userId = UserThreadLocal.get().getId();
        System.out.println(">>>>>show>>>>>>>"+userId);
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        model.addAttribute("cartList", cartList);
        return "cart";
    }

    // http://www.jt.com/service/cart/update/num/562379/9
    //如果使用restful风格有多个参数,                !!!!!!!!!!!6666
    // 并且和pojo属性一致,则可以使用pojo接收
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public SysResult updateNum(Cart cart, HttpServletRequest request) {
        try { Long userId = UserThreadLocal.get().getId();
//            Long userId=  7L ;

            System.out.println(">>>>>updateNum>>>>>>>"+userId);
            cart.setUserId(userId);
            cartService.updateCartNum(cart);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.fail();
        }
    }

    //新增购物车
    @RequestMapping("/add/{itemId}")
    public String saveCart(Cart cart) {
//        Long userId = 7L;
        Long userId = UserThreadLocal.get().getId();
        System.out.println(">>>>>saveCart>>>>>>>"+userId);   //没问题
        cart.setUserId(userId);
        cartService.saveCart(cart);
        return "redirect:/cart/show.html";
        //重定向:跳转到购物车页面
    }



}
