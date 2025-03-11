package com._9._ss23.page.controller;

import OrderPageService;
import com._9._ss23.page.dto.OrderDto;
import com._9._ss23.product.vo.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orderPage")
@RequiredArgsConstructor
public class OrderPageController {

    private final OrderPageService orderPageService;

    @GetMapping()
    public String orderPage(Model model){
         model.addAttribute("products",orderPageService.getProducts());
         return "orderPage";
    }

    @GetMapping("/getOrderItem")
    public String getProducts(Model model){
        return "orderPage";
    }

    @PostMapping("/order")
    @ResponseBody
    public OrderDto order(Model model,@RequestBody List<ProductDto> productDto){
        OrderDto response = orderPageService.order(productDto);

        model.addAttribute("response", response);
        return response;
    }
}
