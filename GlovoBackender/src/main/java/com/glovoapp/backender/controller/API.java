package com.glovoapp.backender.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.glovoapp.backender.config.OrderProperties;
import com.glovoapp.backender.dto.Order;
import com.glovoapp.backender.model.OrderRepository;
import com.glovoapp.backender.shared.OrderVM;

@RestController("/orders")
@ComponentScan("com.glovoapp.backender")
@EnableAutoConfiguration
class API {
	private final String welcomeMessage;
	@Autowired
	private final OrderRepository orderRepository;
	
	

	private static final Logger  logger = LoggerFactory.getLogger(API.class);

	@Autowired
	API(@Value("${backender.welcome_message: welcome to Glovo backender}") String welcomeMessage,
			OrderRepository orderRepository) {
		this.welcomeMessage = welcomeMessage;
		this.orderRepository = orderRepository;
	}

	@GetMapping("/")
	@ResponseBody
	public String root() {
		return welcomeMessage;
	}

	@GetMapping("/orders")
	@ResponseBody
	public List<OrderVM> orders() {
		return orderRepository.findAll().stream().map(order -> new OrderVM(order.getId(), order.getDescription()))
				.collect(Collectors.toList());
	}

	@GetMapping("/courierOrders")
	@ResponseBody
	public List<OrderVM> courierOrders(@RequestParam("courierId")  String courierId) {
			
		return orderRepository.getOrdersForCourier(courierId).
    		   stream().map(order -> new OrderVM(order.getId(), order.getDescription())).collect(Collectors.toList());
		
	}

	//other version with the full order details for helping in testing
	// for testing purposes only and will be removed if needed while deploying
	
	@GetMapping("/courierOrders_withDetails")
	@ResponseBody
	public List<Order> courierOrders_withDetails(@RequestParam("courierId")  String courierId) {
		return new ArrayList<Order>(orderRepository.getOrdersForCourier(courierId));

	}

	public static void main(String[] args) {
		SpringApplication.run(API.class, args);
	}
	
}
