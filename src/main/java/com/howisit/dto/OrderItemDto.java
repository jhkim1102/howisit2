package com.howisit.dto;



import com.howisit.entity.OrderItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
	
	//엔티티 -> DTO로 바꿔준다
	public OrderItemDto(OrderItem orderItem,String imgUrl) {
		this.itemNm = orderItem.getItem().getItemNm();
		this.count = orderItem.getCount();
		this.orderPrice = orderItem.getOrderPrice();
		this.imgUrl = imgUrl;
		this.address = orderItem.getItem().getAddress();
	}
	
	private String address; //주소
	
	private String itemNm; //상품명
	
	private int count; //주문수량
	
	private int orderPrice; //주문 금액
	
	private String imgUrl; //상품 이미지 경로
}
