package kr.or.dgit.bigdata.swmng.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.mappers.CompanyMapper;
import kr.or.dgit.bigdata.swmng.mappers.SoftwareMapper;
import kr.or.dgit.bigdata.swmng.util.MybatisSessionFactory;

public class SoftwareService implements SoftwareMapper<Software> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SoftwareService.class);

	private static final SoftwareService instance = new SoftwareService();

	public static SoftwareService getInstance() {
		return instance;
	}

	private SoftwareService() {
	}

	@Override
	public void insertItem(Software item) {
		if (logger.isDebugEnabled()) {
			logger.debug("insertItem(Company) - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper<Software> softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			softwareDao.insertItem(item);
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
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			softwareDao.deleteItem(idx);
			sqlSession.commit(); // mybatis는 오토커밋이 안됨 수동커밋.
		} finally {
			sqlSession.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("deleteItem(int) - end");
		}
	}

	@Override
	public void updateItem(Software item) {
		if (logger.isDebugEnabled()) {
			logger.debug("updateItem(Company) - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			softwareDao.updateItem(item);
			sqlSession.commit(); // mybatis는 오토커밋이 안됨 수동커밋.
		} finally {
			sqlSession.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("updateItem(Company) - end");
		}
	}

	@Override
	public Software selectByNo(int idx) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectByNo(int) - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			Software returnCompany = (Software) softwareDao.selectByNo(idx);
			if (logger.isDebugEnabled()) {
				logger.debug("selectByNo(int) - end");
			}
			return returnCompany;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Software> selectAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectAll() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			List<Software> returnList = softwareDao.selectAll();
			if (logger.isDebugEnabled()) {
				logger.debug("selectAll() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public Software selectMaxNo() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectMaxNo() - start");
		}
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			Software returnList = (Software) softwareDao.selectMaxNo();
			if (logger.isDebugEnabled()) {
				logger.debug("selectMaxNo() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}



	@Override
	public List<Software> selectCategory() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectCategory() - start");
		}
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			List<Software> returnList = softwareDao.selectCategory();
			if (logger.isDebugEnabled()) {
				logger.debug("selectCategory() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

}