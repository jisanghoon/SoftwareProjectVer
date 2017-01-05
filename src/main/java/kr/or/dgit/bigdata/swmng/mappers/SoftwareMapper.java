package kr.or.dgit.bigdata.swmng.mappers;

import java.util.List;

public interface SoftwareMapper<T> {
	void insertItem(T item);

	void deleteItem(int idx);

	void updateItem(T item);

	T selectByNo(int idx);

	List<T> selectAll();

	T selectMaxNo();

	List<T> selectCategory();
	
	List<T> selectTitle();
}
