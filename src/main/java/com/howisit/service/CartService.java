package com.howisit.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.howisit.dto.CartDto;
import com.howisit.dto.CartHistDto;
import com.howisit.dto.CartItemDto;
import com.howisit.entity.CartItem;
import com.howisit.entity.Item;
import com.howisit.entity.ItemImg;
import com.howisit.entity.Cart;
import com.howisit.entity.Member;
import com.howisit.respository.CartRepository;
import com.howisit.respository.ItemImgRepository;
import com.howisit.respository.ItemRepository;
import com.howisit.respository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final CartRepository cartRepository;
	private final ItemImgRepository itemImgRepository;
	

public Long Cart(CartDto cartDto, String email) {
	//1.주문할 상품을 조회
	Item item = itemRepository.findById(cartDto.getItemId())
												.orElseThrow(EntityNotFoundException::new);
	//2.현재 로그인한 회원의 이메일을 이용해 조회
	Member member = memberRepository.findByEmail(email);	
	
	List<CartItem> cartItemList = new ArrayList<>();
	CartItem cartItem = CartItem.createCartItem(item, cartDto.getCount());
	cartItemList.add(cartItem);
	
	Cart cart = com.howisit.entity.Cart.createCart(member, cartItemList);
	cartRepository.save(cart);
	
	return cart.getId();
}


@Transactional(readOnly = true)
public Page<CartHistDto> getCartList(String email, Pageable pageable){
	List<Cart> carts = cartRepository.findcarts(email, pageable);
	
	Long totalCount = cartRepository.countcart(email);
	
	
	List<CartHistDto> cartHistDtos = new ArrayList<>();
	for(Cart cart : carts) {
		CartHistDto cartHistDto = new CartHistDto(cart);
		List<CartItem> cartItems = cart.getCartItems();
	
	
	for(CartItem cartItem : cartItems) {
		ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(cartItem.getItem().getId(),"Y");
		CartItemDto cartItemDto = new CartItemDto(cartItem, itemImg.getImgUrl());
		cartHistDto.addCartItemDto(cartItemDto);
	}
	cartHistDtos.add(cartHistDto);
	}
	
	return new PageImpl<>(cartHistDtos, pageable, totalCount);
	
}

@Transactional(readOnly = true)
public boolean vaildateCart(Long cartId, String email) {
	Member curMember = memberRepository.findByEmail(email);
	Cart cart = cartRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);
	
	Member savedMember = cart.getMember();
	
	//로그인한 사용자의 이메일과 주문한 사용자의 이메일이 같은 지 작동비교
	if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
		//같이 않으면
		return false;
	}

	return true;
}

public void deleteCart(Long cartId) {
	Cart cart = cartRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);
	
	cartRepository.delete(cart);
}






}







