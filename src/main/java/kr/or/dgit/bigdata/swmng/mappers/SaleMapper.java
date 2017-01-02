package kr.or.dgit.bigdata.swmng.mappers;

import java.util.List;
import java.util.Map;

public interface SaleMapper<T> {
	List<T> selectAll();
	List<T> selectAllSortDate();
	List<T> selectAllSortSupplier();
	List<Map<String,Object>> selectAllGroupByConame();
}
