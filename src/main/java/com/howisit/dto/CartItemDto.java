package com.howisit.dto;

import com.howisit.entity.CartItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
	public CartItemDto(CartItem cartItem, String imgUrl) {
		this.id = cartItem.getItem().getId();
		this.itemNm = cartItem.getItem().getItemNm();
		this.imgUrl = imgUrl;		
		this.address = cartItem.getItem().getAddress();
	}
	
	private Long id;
	
	private String itemNm;
	
	private String imgUrl;
	
	private String address;
}
