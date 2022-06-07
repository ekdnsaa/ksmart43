package ksmart.mybatis.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ksmart.mybatis.dto.Goods;

import ksmart.mybatis.service.GoodsService;


@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	private static final Logger log = LoggerFactory.getLogger(GoodsController.class);

	
	private final GoodsService goodsService;
	
	public GoodsController(GoodsService goodsService) {
		this.goodsService = goodsService;
	}
	

	
	

	
	@PostMapping("/goodsList") 
 		public String getSearchGoodsList(@RequestParam(name = "searchKey") String searchKey
 										,@RequestParam(name = "searchValue", required = false)String searchValue
 										,Model model) { 
			log.info("searchKey : {}", searchKey);
			log.info("searchValue : {}", searchValue); 
			if("goodsName".equals(searchKey)) {
				searchKey = "g.g_name"; 
	  		}else if("goodsPrice".equals(searchKey)) {
	  			searchKey = "g.g_price";
	  		}else if("goodsSellerId".equals(searchKey)) {
	  			searchKey = "g.g_seller_id";
	  		}
	  List<Goods> searchGoodsList = goodsService.getSearchGoodsList(searchKey, searchValue);
	  
	  if(searchGoodsList != null) model.addAttribute("goodsList", searchGoodsList);
	  
	  return "goods/goodsList";
	 }
	  

	
	
	
	
	
	
	@GetMapping("/goodsList")
	public String getGoodsList(Model model) {
		List<Goods> goodsList = goodsService.getGoodsList();
		log.info("상품 전체 목록:{}", goodsList);
		model.addAttribute("goodsList", goodsList);
		return "goods/goodsList";
	}
}