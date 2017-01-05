package kr.or.dgit.bigdata.swmng.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sale {
	private int no;
	private Buyer shopName;
	private String shopName2;
	private Software title;
	private String title2;
	private int orderCount;
	private boolean payment;
	private Date date;

	
	public Sale(int no, String shopName2, String title2, int orderCount, boolean payment, Date date) {
		this.no = no;
		this.shopName2 = shopName2;
		this.title2 = title2;
		this.orderCount = orderCount;
		this.payment = payment;
		this.date = date;
	}

	public String getShopName2() {
		return shopName2;
	}

	public void setShopName2(String shopName2) {
		this.shopName2 = shopName2;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}


	

	@Override
	public String toString() {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		return String.format("Sale [no=%s, shopName=%s, title=%s, orderCount=%s, payment=%s, date=%s]", no, shopName.getShopName(),
				title.getTitle(), orderCount, payment, format.format(date));
	}

	public Sale(int no, Buyer shopName, Software title, int orderCount, boolean payment, Date date) {
		this.no = no;
		this.shopName = shopName;
		this.title = title;
		this.orderCount = orderCount;
		this.payment = payment;
		this.date = date;
	}

	public Sale() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Buyer getShopName() {
		return shopName;
	}

	public void setShopName(Buyer shopName) {
		this.shopName = shopName;
	}

	public Software getTitle() {
		return title;
	}

	public void setTitle(Software title) {
		this.title = title;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public boolean isPayment() {
		return payment;
	}

	public void setPayment(boolean payment) {
		this.payment = payment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
