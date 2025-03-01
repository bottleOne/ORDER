package com._9._ss23;

import com._9._ss23.order.service.OrderService;
import com._9._ss23.order.service.ProductOrderServiceImpl;
import com._9._ss23.product.domain.Product;
import com._9._ss23.product.dto.ProductDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		 SpringApplication.run(Application.class, args);
	}

}
