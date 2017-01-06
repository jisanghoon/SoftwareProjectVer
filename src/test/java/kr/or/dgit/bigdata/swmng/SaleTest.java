package kr.or.dgit.bigdata.swmng;

import java.util.GregorianCalendar;
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
	 
	  for (int i = 0; i < listmap.size(); i++) { 
		  Map<String, Object> tempMap =  listmap.get(i); 
		  System.out.println( String.format("%s -> %s",tempMap.get("shopName"), tempMap.get("totalCnt")) ); }
	  
	 }

	  
		@Test
		public void testSelectAllOrderByCategory() {
			List<Sale> list = ss.selectAllOrderByCategory();
			Assert.assertNotNull(list);
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
		
		@Test
		public void testSelectAllOrderByTitle() {
			List<Sale> list = ss.selectAllOrderByTitle();
			Assert.assertNotNull(list);
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
		
		
		@Test
		public void testSelectBetweenDates() {
			GregorianCalendar cal1 = new GregorianCalendar(2009,1,1);
			GregorianCalendar cal2 = new GregorianCalendar(2010,8,1);
			System.out.println(cal2.getTime());
			List<Sale> list = ss.selectBetweenDates(cal1.getTime(),cal2.getTime());
			Assert.assertNotNull(list);
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
	

}
