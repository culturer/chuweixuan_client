package com.culturer.guishi.bean;


public class OrderBean implements Cloneable {
	
	/**
	 * Id : 40
	 * UserId : 3
	 * Tel : 15623166629
	 * Address : 毛坦港湾
	 * Receiver : 宋志文
	 * Amount : 0
	 * PayType : cash
	 * IsDiscounts : false
	 * DiscountMsg : 无折扣
	 * Msg : 加辣
	 * Item : 订单
	 * IsPay : false
	 * Appoint : false
	 * AppointTime : 1775138426
	 * IsSend : false
	 * IsSend1 : false
	 * SenderId : 0
	 * SendType : 0
	 * IsReceive : false
	 * ReceiveTiem : 1775138426
	 * IsCancel : false
	 * CancelMsg :
	 * AddTime : 1535078043
	 */
	
	private long Id;
	private long UserId;
	private String Tel;
	private String Address;
	private String Receiver;
	private float Amount;
	private String PayType;
	private boolean IsDiscounts;
	private String DiscountMsg;
	private String Msg;
	private String Item;
	private boolean IsPay;
	private boolean Appoint;
	private long AppointTime;
	private boolean IsSend;
	private boolean IsSend1;
	private int SenderId;
	private int SendType;
	private boolean IsReceive;
	private long ReceiveTiem;
	private boolean IsCancel;
	private String CancelMsg;
	private long AddTime;
	
	public long getId() {
		return Id;
	}
	
	public void setId(long id) {
		this.Id = id;
	}
	
	public long getUserId() {
		return UserId;
	}
	
	public void setUserId(int userId) {
		this.UserId = userId;
	}
	
	public String getTel() {
		return Tel;
	}
	
	public void setTel(String tel) {
		this.Tel = tel;
	}
	
	public String getAddress() {
		return Address;
	}
	
	public void setAddress(String address) {
		this.Address = address;
	}
	
	public String getReceiver() {
		return Receiver;
	}
	
	public void setReceiver(String receiver) {
		this.Receiver = receiver;
	}
	
	public float getAmount() {
		return Amount;
	}
	
	public void setAmount(float amount) {
		this.Amount = amount;
	}
	
	public String getPayType() {
		return PayType;
	}
	
	public void setPayType(String payType) {
		this.PayType = payType;
	}
	
	public boolean isDiscount() {
		return IsDiscounts;
	}
	
	public void setDiscount(boolean discount) {
		IsDiscounts = discount;
	}
	
	public String getDiscountMsg() {
		return DiscountMsg;
	}
	
	public void setDiscountMsg(String discountMsg) {
		this.DiscountMsg = discountMsg;
	}
	
	public String getMsg() {
		return Msg;
	}
	
	public void setMsg(String msg) {
		this.Msg = msg;
	}
	
	public String getItem() {
		return Item;
	}
	
	public void setItem(String item) {
		this.Item = item;
	}
	
	public boolean isPay() {
		return IsPay;
	}
	
	public void setPay(boolean pay) {
		IsPay = pay;
	}
	
	public boolean isAppoint() {
		return Appoint;
	}
	
	public void setAppoint(boolean appoint) {
		this.Appoint = appoint;
	}
	
	public long getAppointTime() {
		return AppointTime;
	}
	
	public void setAppointTime(long appointTime) {
		this.AppointTime = appointTime;
	}
	
	public boolean isSend() {
		return IsSend;
	}
	
	public void setSend(boolean send) {
		IsSend = send;
	}
	
	public boolean isSend1() {
		return IsSend1;
	}
	
	public void setSend1(boolean send1) {
		IsSend1 = send1;
	}
	
	public int getSenderId() {
		return SenderId;
	}
	
	public void setSenderId(int senderId) {
		this.SenderId = senderId;
	}
	
	public int getSendType() {
		return SendType;
	}
	
	public void setSendType(int sendType) {
		this.SendType = sendType;
	}
	
	public boolean isReceive() {
		return IsReceive;
	}
	
	public void setReceive(boolean receive) {
		IsReceive = receive;
	}
	
	public long getReceiveTime() {
		return ReceiveTiem;
	}
	
	public void setReceiveTime(long receiveTime) {
		this.ReceiveTiem = receiveTime;
	}
	
	public boolean isCancel() {
		return IsCancel;
	}
	
	public void setCancel(boolean cancel) {
		IsCancel = cancel;
	}
	
	public String getCancelMsg() {
		return CancelMsg;
	}
	
	public void setCancelMsg(String cancelMsg) {
		this.CancelMsg = cancelMsg;
	}
	
	public long getAddTime() {
		return AddTime;
	}
	
	public void setAddTime(long addTime) {
		this.AddTime = addTime;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
