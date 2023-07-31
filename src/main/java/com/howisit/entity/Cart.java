package com.howisit.entity;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart")
@Setter
@Getter
@ToString
public class Cart {
	@Id
	@Column(name="cart_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDateTime orderDate; 
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	//order에서도 orderItem을 참조할 수 있도록 양방향 관계를 만든다.
		//다만 orderItem은 자식 테이블이 되므로 List로 만든다.
		@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, 
				orphanRemoval = true, fetch = FetchType.LAZY) //연관관계의 주인 설정(외래키 지정)
		private List<CartItem> cartItems = new ArrayList<>();
		
	public void addCartItem(CartItem cartItem) {
		this.cartItems.add(cartItem);
		cartItem.setCart(this);
	}
	
	public static Cart createCart(Member member, List<CartItem> cartItemList) {
		Cart cart = new Cart();
		cart.setMember(member);
		
		for(CartItem cartItem : cartItemList) {
			cart.addCartItem(cartItem);
		}
		
		cart.setOrderDate(LocalDateTime.now());
		
		return cart;
	}
	
	
	
}
