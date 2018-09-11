package com.culturer.guishi.bean;


public class UserBean {
	
	/**
	 * Id : 4
	 * Tel : 15623166629
	 * Pwd :
	 * Payee : 宋志文
	 * Address : 毛坦港湾
	 * Amount : 18588263531
	 * AmountType : 支付宝
	 * IsCustomer : true
	 * IsSeller : true
	 * IsDiliver : false
	 * IsManager : false
	 * Vid :
	 * IsLock : false
	 * AddTime : 1532616533
	 */
	
	private int Id;
	private String Tel;
	private String Pwd;
	private String Payee;
	private String Address;
	private String Amount;
	private String AmountType;
	private boolean IsCustomer;
	private boolean IsSeller;
	private boolean IsDiliver;
	private boolean IsManager;
	private String Vid;
	private boolean IsLock;
	private int AddTime;
	
	public int getId() {
		return Id;
	}
	
	public void setId(int Id) {
		this.Id = Id;
	}
	
	public String getTel() {
		return Tel;
	}
	
	public void setTel(String Tel) {
		this.Tel = Tel;
	}
	
	public String getPwd() {
		return Pwd;
	}
	
	public void setPwd(String Pwd) {
		this.Pwd = Pwd;
	}
	
	public String getPayee() {
		return Payee;
	}
	
	public void setPayee(String Payee) {
		this.Payee = Payee;
	}
	
	public String getAddress() {
		return Address;
	}
	
	public void setAddress(String Address) {
		this.Address = Address;
	}
	
	public String getAmount() {
		return Amount;
	}
	
	public void setAmount(String Amount) {
		this.Amount = Amount;
	}
	
	public String getAmountType() {
		return AmountType;
	}
	
	public void setAmountType(String AmountType) {
		this.AmountType = AmountType;
	}
	
	public boolean isIsCustomer() {
		return IsCustomer;
	}
	
	public void setIsCustomer(boolean IsCustomer) {
		this.IsCustomer = IsCustomer;
	}
	
	public boolean isIsSeller() {
		return IsSeller;
	}
	
	public void setIsSeller(boolean IsSeller) {
		this.IsSeller = IsSeller;
	}
	
	public boolean isIsDiliver() {
		return IsDiliver;
	}
	
	public void setIsDiliver(boolean IsDiliver) {
		this.IsDiliver = IsDiliver;
	}
	
	public boolean isIsManager() {
		return IsManager;
	}
	
	public void setIsManager(boolean IsManager) {
		this.IsManager = IsManager;
	}
	
	public String getVid() {
		return Vid;
	}
	
	public void setVid(String Vid) {
		this.Vid = Vid;
	}
	
	public boolean isIsLock() {
		return IsLock;
	}
	
	public void setIsLock(boolean IsLock) {
		this.IsLock = IsLock;
	}
	
	public int getAddTime() {
		return AddTime;
	}
	
	public void setAddTime(int AddTime) {
		this.AddTime = AddTime;
	}
}
