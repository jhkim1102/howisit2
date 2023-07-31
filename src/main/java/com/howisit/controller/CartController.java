package com.howisit.controller;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.howisit.dto.CartDto;
import com.howisit.dto.CartHistDto;
import com.howisit.service.CartService;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;	
	
	@PostMapping(value = "/cart")
	public @ResponseBody ResponseEntity cart(@RequestBody @Valid CartDto cartDto,
											BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
			for(FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage()); //에러메세지를 합친다.
			}
			
			return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
		
		String email = principal.getName();
		Long cartId;
		
		try {
			cartId = cartService.Cart(cartDto, email);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Long>(cartId, HttpStatus.OK); //성공시
	}
	
	@GetMapping(value = {"/carts", "carts/{page}"})
	public String CartHist(@PathVariable("page") Optional<Integer> page,
							Principal principal, Model model) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
		
		Page<CartHistDto> cartHistDtoList = cartService.getCartList(principal.getName(), pageable);
		
		model.addAttribute("carts", cartHistDtoList);
		model.addAttribute("maxPage", 5);
		
		return "cart/cartList";
		
	}
	
	@DeleteMapping("/cart/{cartId}/delete")
	public @ResponseBody ResponseEntity deleteCart(@PathVariable("cartId") Long cartId, 
													Principal principal) {
		
		if(!cartService.vaildateCart(cartId, principal.getName())) {
			return new ResponseEntity<String>("삭제 권한이 없습니다",HttpStatus.FORBIDDEN);
		}
		
		cartService.deleteCart(cartId);
		
		return new ResponseEntity<Long>(cartId, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}