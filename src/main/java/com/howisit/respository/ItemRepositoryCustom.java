package com.howisit.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.howisit.dto.ItemSearchDto;
import com.howisit.dto.MainItemDto;
import com.howisit.entity.Item;


public interface ItemRepositoryCustom {
	Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
	
	Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
