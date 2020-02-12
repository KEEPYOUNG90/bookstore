package com.bookshop01.cs.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.cs.vo.csVO;
import com.bookshop01.goods.vo.GoodsVO;

public interface CSDAO {
	public List<csVO> CSList(csVO csVO) throws DataAccessException;

}