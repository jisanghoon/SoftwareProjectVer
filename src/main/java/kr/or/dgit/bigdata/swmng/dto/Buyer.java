package kr.or.dgit.bigdata.swmng.dto;

public class Buyer {
	private int no;
	private String shopName;
	private String address;
	private String tel;
	private byte[] picPath;

	public byte[] getPicPath() {
		return picPath;
	}

	public void setPicPath(byte[] picPath) {
		this.picPath = picPath;
	}

	@Override
	public String toString() {
		return String.format("Buyer [no=%s, shopName=%s, address=%s, tel=%s]", no, shopName, address, tel);
	}

	public Buyer(int no, String shopName, String address, String tel) {
		this.no = no;
		this.shopName = shopName;
		this.address = address;
		this.tel = tel;
	}

	public Buyer(int no, String shopName, String address, String tel, byte[] picPath) {
		this.no = no;
		this.shopName = shopName;
		this.address = address;
		this.tel = tel;
		this.picPath = picPath;
	}

	public Buyer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
