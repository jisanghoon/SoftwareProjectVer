package kr.or.dgit.bigdata.swmng.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sale {
	private int no;
	private String shopName;
	private String title;
	private int orderCount;
	private boolean payment;
	private Date date;
	private int supPrice;
	private int sellPrice;
	private String coName;
	private String category;
	


	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return String.format("Sale [no=%s, shopName=%s, title=%s, orderCount=%s, payment=%s, date=%s]", no, shopName,
				title, orderCount, payment, format.format(date));
	}

	public Sale(int no, String shopName, String title, int orderCount, boolean payment, Date date, int supPrice,
			int sellPrice, String coName) {
		this.no = no;
		this.shopName = shopName;
		this.title = title;
		this.orderCount = orderCount;
		this.payment = payment;
		this.date = date;
		this.supPrice = supPrice;
		this.sellPrice = sellPrice;
		this.coName = coName;
	}

	public Sale() {
		super();
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
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

	public int getSupPrice() {
		return supPrice;
	}

	public void setSupPrice(int supPrice) {
		this.supPrice = supPrice;
	}

	public int getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}



}
