package com.howisit.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import com.howisit.entity.Cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartHistDto {
	
	public CartHistDto(Cart cart) {
		this.cartId = cart.getId();
		this.orderDate = cart.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
	
	private Long cartId;
	
	private String orderDate;
	
	private List<CartItemDto> cartItemDtoList = new ArrayList<>();
	
	public void addCartItemDto(CartItemDto cartItemDto) {
		this.cartItemDtoList.add(cartItemDto);
	}
}
