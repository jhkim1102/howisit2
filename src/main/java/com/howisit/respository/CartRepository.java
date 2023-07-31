package com.howisit.respository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.howisit.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Long>{
	@Query("select o from Cart o where o.member.email = :email order by o.orderDate desc")
	List<Cart> findcarts(@Param("email")String email, Pageable pageable);
	
	@Query("select count(o) from Cart o where o.member.email = :email")
	Long countcart(@Param("email")String email);
}
