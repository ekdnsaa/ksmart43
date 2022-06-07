package ksmart.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.dto.Member;

@Mapper
public interface GoodsMapper {
	
	//상품검색
	public List<Goods> getSearchGoodsList(String searchKey, String searchValue);
	
	
	public int addGoods(Member member);
	
	public int getModifyGoods(Member member);
	
	public List<Goods> getGoodsList();

}
