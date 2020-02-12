package com.bookshop01.cs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop01.cart.dao.CartDAO;
import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.cs.dao.CSDAO;
import com.bookshop01.cs.vo.csVO;
import com.bookshop01.goods.vo.GoodsVO;

@Service("csService")
@Transactional(propagation=Propagation.REQUIRED)
public class CSServiceImpl  implements CSService{
	@Autowired
	CSDAO csdao;
	
	public List<csVO> CSList() throws Exception{
		List<csVO> csList = csdao.CSList();
		System.out.println(csList.size()+"dsfkjsdjlfjsdkflsjdlfjlsdfl");
		if(csList.size()==0){ //카트에 저장된 상품이없는 경우
			return null;
		}
		return csList;
	}
	
}