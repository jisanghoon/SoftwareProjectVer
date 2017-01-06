package kr.or.dgit.bigdata.swmng.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.mappers.CompanyMapper;
import kr.or.dgit.bigdata.swmng.util.MybatisSessionFactory;

public class CompanyService implements CompanyMapper<Company> {

	private static final Logger logger = Logger.getLogger(CompanyService.class);

	private static final CompanyService instance = new CompanyService();

	public static CompanyService getInstance() {

		return instance;
	}

	private CompanyService() {
	}

	@Override
	public void insertItem(Company item) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		CompanyMapper<Company> companyDao = sqlSession.getMapper(CompanyMapper.class);
		try {
			companyDao.insertItem(item);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public void deleteItem(int idx) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		CompanyMapper companyDao = sqlSession.getMapper(CompanyMapper.class);
		try {
			companyDao.deleteItem(idx);
			sqlSession.commit(); // mybatis는 오토커밋이 안됨 수동커밋.
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public void updateItem(Company item) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		CompanyMapper companyDao = sqlSession.getMapper(CompanyMapper.class);
		try {
			companyDao.updateItem(item);
			sqlSession.commit(); // mybatis는 오토커밋이 안됨 수동커밋.
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public Company selectByNo(int idx) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		CompanyMapper companyDao = sqlSession.getMapper(CompanyMapper.class);
		try {
			Company returnCompany = (Company) companyDao.selectByNo(idx);
			return returnCompany;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Company> selectAll() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		CompanyMapper companyDao = sqlSession.getMapper(CompanyMapper.class);
		try {
			List<Company> returnList = companyDao.selectAll();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public Company selectMaxNo() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		CompanyMapper companyDao = sqlSession.getMapper(CompanyMapper.class);
		try {
			Company returnList = (Company) companyDao.selectMaxNo();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Company> selectCoName() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		CompanyMapper companyDao = sqlSession.getMapper(CompanyMapper.class);
		try {
			List<Company> returnList = companyDao.selectCoName();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

}