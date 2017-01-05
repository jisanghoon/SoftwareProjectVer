package kr.or.dgit.bigdata.swmng.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.mappers.BuyerMapper;
import kr.or.dgit.bigdata.swmng.mappers.CompanyMapper;
import kr.or.dgit.bigdata.swmng.util.MybatisSessionFactory;

public class BuyerService implements BuyerMapper<Buyer> {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(BuyerService.class);

	private static final BuyerService instance = new BuyerService();

	public static BuyerService getInstance() {
	
		return instance;
	}

	private BuyerService() {
	}

	

	@Override
	public void insertItem(Buyer item) {
		if (logger.isDebugEnabled()) {
			logger.debug("insertItem(Company) - start");
		}
		
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper<Buyer> buyerDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			buyerDao.insertItem(item);
			sqlSession.commit(); // mybatis는 오토커밋이 안됨 수동커밋.
		} finally {
			sqlSession.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("insertItem(Company) - end");
		}
	}

	@Override
	public void deleteItem(int idx) {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteItem(int) - start");
		}
		
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper buyerDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			buyerDao.deleteItem(idx);
			sqlSession.commit(); // mybatis는 오토커밋이 안됨 수동커밋.
		} finally {
			sqlSession.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("deleteItem(int) - end");
		}
	}

	@Override
	public void updateItem(Buyer item) {
		if (logger.isDebugEnabled()) {
			logger.debug("updateItem(Company) - start");
		}
		
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper buyerDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			buyerDao.updateItem(item);
			sqlSession.commit(); // mybatis는 오토커밋이 안됨 수동커밋.
		} finally {
			sqlSession.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("updateItem(Company) - end");
		}
	}

	@Override
	public Buyer selectByNo(int idx) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectByNo(int) - start");
		}
		
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper buyerDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			Buyer returnBuyer = (Buyer) buyerDao.selectByNo(idx);
			if (logger.isDebugEnabled()) {
				logger.debug("selectByNo(int) - end");
			}
			return returnBuyer;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Buyer> selectAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectAll() - start");
		}
	
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper buyerDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			List<Buyer> returnList = buyerDao.selectAll();
			if (logger.isDebugEnabled()) {
				logger.debug("selectAll() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public Buyer selectMaxNo() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectMaxNo() - start");
		}
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper companyDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			Buyer returnList = (Buyer) companyDao.selectMaxNo();
			if (logger.isDebugEnabled()) {
				logger.debug("selectMaxNo() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Buyer> selectShopName() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectShopName() - start");
		}
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper buyerDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			List<Buyer> returnList = buyerDao.selectShopName();
			if (logger.isDebugEnabled()) {
				logger.debug("selectShopName() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}



}