package com.howisit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="cart_item_id")
@Setter
@Getter
@ToString
public class CartItem extends BaseEntity{
	@Id
	@Column(name="cart_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	private int count;
	
	public static CartItem createCartItem(Item item, int count) {
		CartItem cartItem = new CartItem();
		cartItem.setItem(item);
		cartItem.setCount(count);
		
		return cartItem;
	}
}
