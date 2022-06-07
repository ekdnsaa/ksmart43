package ksmart.mybatis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.mapper.GoodsMapper;

@Service
@Transactional
public class GoodsService {
	
	private final GoodsMapper goodsMapper;
	
	public GoodsService(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

	
	public List<Goods> getGoodsList(){
		List<Goods> goodsList = goodsMapper.getGoodsList();
		return goodsList;
	}


	
	  public List<Goods> getSearchGoodsList(String searchKey, String searchValue) {
		  List<Goods> searchGoodsList = goodsMapper.getSearchGoodsList(searchKey, searchValue); 
		  return searchGoodsList; 
	  }
	

	
}
