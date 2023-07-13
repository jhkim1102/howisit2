package com.howisit.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.howisit.entity.Member;



public interface MemberRepository extends JpaRepository<Member, Long>{
	//select * from member where email = ?
	Member findByEmail(String email);
}
