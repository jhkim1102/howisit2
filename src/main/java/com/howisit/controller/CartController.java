package com.howisit.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.howisit.respository.MemberRepository;
import com.howisit.service.ItemService;

@Controller
public class CartController {
	
	 
	@GetMapping(value = "/cart")
	public String cartList() {
		return "cart/cartList";
	}
	
	
		
		
	
}

