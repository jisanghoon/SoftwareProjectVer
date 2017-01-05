package kr.or.dgit.bigdata.swmng.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.mappers.CompanyMapper;
import kr.or.dgit.bigdata.swmng.mappers.SaleMapper;
import kr.or.dgit.bigdata.swmng.util.MybatisSessionFactory;

public class SaleService implements SaleMapper<Sale> {
	private static final Logger logger = Logger.getLogger(CompanyService.class);

	private static final SaleService instance = new SaleService();

	public static SaleService getInstance() {
		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - end");
		}
		return instance;
	}

	private SaleService() {
	}

	
	@Override
	public List<Sale> selectAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectAll() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectAll();
			if (logger.isDebugEnabled()) {
				logger.debug("selectAll() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Sale> selectAllSortDate() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectAllSortDate() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectAllSortDate();
			if (logger.isDebugEnabled()) {
				logger.debug("selectAllSortDate() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Sale> selectAllSortSupplier() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectAllSortSupplier() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectAllSortSupplier();
			if (logger.isDebugEnabled()) {
				logger.debug("selectAllSortDate() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Map<String, Object>> selectAllGroupByConame() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectAllGroupByConame() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Map<String, Object>> returnList = saleDao.selectAllGroupByConame();
			if (logger.isDebugEnabled()) {
				logger.debug("selectAllGroupByConame() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public Sale selectMaxNo() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectMaxNo() - start");
		}
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			Sale returnList = (Sale) saleDao.selectMaxNo();
			if (logger.isDebugEnabled()) {
				logger.debug("selectMaxNo() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void insertItem(Sale item) {
		if (logger.isDebugEnabled()) {
			logger.debug("insertItem(Sale) - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper<Sale> saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			saleDao.insertItem(item);
			sqlSession.commit(); // mybatis는 오토커밋이 안됨 수동커밋.
		} finally {
			sqlSession.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("insertItem(Sale) - end");
		}
	}


}
