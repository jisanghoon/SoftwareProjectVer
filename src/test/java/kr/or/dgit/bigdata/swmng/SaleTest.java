package kr.or.dgit.bigdata.swmng;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.service.SaleService;

public class SaleTest {
	private static SaleService ss;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ss = SaleService.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ss = null;
	}

	@Test
	public void testSelectAll() {
		List<Sale> list = ss.selectAll();
		Assert.assertNotNull(list);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	@Test
	public void testSelectAllSortDate() {
		List<Sale> list = ss.selectAllSortDate();
		Assert.assertNotNull(list);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	
	  @Test public void testSelectAllGroupByConame() { 
		  List<Map<String, Object>> listmap = ss.selectAllGroupByConame();
		  	Assert.assertNotNull(listmap);
	 
	  for (int i = 0; i < listmap.size(); i++) { Map<String, Object> tempMap =
	  listmap.get(i); System.out.println( String.format("%s -> %s",
	  tempMap.get("shopName"), tempMap.get("totalCnt")) ); }
	  
	 }
	 

}
