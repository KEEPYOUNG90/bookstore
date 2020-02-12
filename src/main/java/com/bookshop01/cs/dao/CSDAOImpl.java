package com.bookshop01.cs.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.cs.vo.csVO;
import com.bookshop01.goods.vo.GoodsVO;

@Repository("cartDAO")
public class CSDAOImpl  implements  CSDAO{
	@Autowired
	private SqlSession sqlSession;
	
	public List<csVO> CSList(csVO csVO) throws DataAccessException {
		List<csVO> csList =(List)sqlSession.selectList("mapper.cart.selectCartList",csVO);
		return csList;
	}

}