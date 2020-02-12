package com.bookshop01.cs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.cs.vo.csVO;
import com.bookshop01.goods.vo.GoodsVO;

public interface CSService {	
	public List<csVO> CSList(csVO csVO) throws Exception;

}