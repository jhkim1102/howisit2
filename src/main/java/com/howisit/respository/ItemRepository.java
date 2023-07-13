package com.howisit.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.howisit.constant.ItemSellStatus;
import com.howisit.dto.ItemRankDto;
import com.howisit.entity.Item;



public interface ItemRepository extends JpaRepository<Item, Long>,
ItemRepositoryCustom{
	//select * from item where item_nm = ?
	List<Item> findByItemNm(String itemNm);
	
	//select * from item where item_nm = ? and item_sell_status = ?
	List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus itemSellStatus);
	
	// JPQL(non native 쿼리) -> 컬럼명, 테이블명은 entity 클래스 기준으로 작성한다
	@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
	List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

	// JPQL(native 쿼리) -> h2 데이터베이스를 기준으로한 쿼리문 작성
	@Query(value = "select*from item where item_detail like %:itemDetail% order by price desc", nativeQuery = true)
	List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
	
	@Query(value = "select data.num num, item.item_id id, item.item_nm itemNm, item.price price, item_img.img_url imgUrl, item_img.repimg_yn repimgYn \r\n"
			+ "            from item \r\n"
			+ "			inner join item_img on (item.item_id = item_img.item_id)\r\n"
			+ "			inner join (select @ROWNUM\\:=@ROWNUM+1 num, groupdata.* from\r\n"
			+ "			            (select order_item.item_id, count(*) cnt\r\n"
			+ "			              from order_item\r\n"
			+ "			              inner join orders on (order_item.order_id = orders.order_id)\r\n"
			+ "			              where orders.order_status = 'ORDER'\r\n"
			+ "			             group by order_item.item_id order by cnt desc) groupdata,\r\n"
			+ "                          (SELECT @ROWNUM\\:=0) R \r\n"
			+ "                          limit 6) data\r\n"
			+ "			on (item.item_id = data.item_id)\r\n"
			+ "			where item_img.repimg_yn = 'Y'\r\n"
			+ "			order by num", nativeQuery = true)
	List<ItemRankDto> getItemRankList();

}
