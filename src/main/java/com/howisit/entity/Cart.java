package com.howisit.entity;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart")
@Setter
@Getter
@ToString
public class Cart extends BaseEntity{
	
	@Id
	@Column(name="cart_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member member;
	
	private int count;	
	
	@OneToMany(mappedBy = "cart")
	private List<CartItem> cartItems = new ArrayList<>();
	
	public static Cart createCart(Member member) {
		Cart cart = new Cart();
		cart.setCount(0);
		cart.setMember(member);
		return cart;
	}
	
	
}
