package com.culturer.guishi.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/25 0025.
 */

public class OrderDataBean {
	
	/**
	 * datas : [{"OrderType":"is_pay","OrderData":[{"Id":40,"UserId":3,"Tel":"15623166629","Address":"毛坦港湾","Receiver":"宋志文","Amount":0,"PayType":"cash","IsDiscounts":false,"DiscountMsg":"无折扣","Msg":"加辣","Item":"订单","IsPay":false,"Appoint":false,"AppointTime":1775138426,"IsSend":false,"IsSend1":false,"SenderId":0,"SendType":0,"IsReceive":false,"ReceiveTiem":1775138426,"IsCancel":false,"CancelMsg":"","AddTime":1535078043},{"Id":39,"UserId":3,"Tel":"15623166629","Address":"毛坦港湾","Receiver":"宋志文","Amount":0,"PayType":"cash","IsDiscounts":false,"DiscountMsg":"无折扣","Msg":"加辣","Item":"订单","IsPay":false,"Appoint":false,"AppointTime":1647343488,"IsSend":false,"IsSend1":false,"SenderId":0,"SendType":0,"IsReceive":false,"ReceiveTiem":1647343488,"IsCancel":false,"CancelMsg":"","AddTime":1534950251}],"Count":2},{"OrderType":"is_send","OrderData":[{"Id":40,"UserId":3,"Tel":"15623166629","Address":"毛坦港湾","Receiver":"宋志文","Amount":0,"PayType":"cash","IsDiscounts":false,"DiscountMsg":"无折扣","Msg":"加辣","Item":"订单","IsPay":false,"Appoint":false,"AppointTime":1775138426,"IsSend":false,"IsSend1":false,"SenderId":0,"SendType":0,"IsReceive":false,"ReceiveTiem":1775138426,"IsCancel":false,"CancelMsg":"","AddTime":1535078043},{"Id":39,"UserId":3,"Tel":"15623166629","Address":"毛坦港湾","Receiver":"宋志文","Amount":0,"PayType":"cash","IsDiscounts":false,"DiscountMsg":"无折扣","Msg":"加辣","Item":"订单","IsPay":false,"Appoint":false,"AppointTime":1647343488,"IsSend":false,"IsSend1":false,"SenderId":0,"SendType":0,"IsReceive":false,"ReceiveTiem":1647343488,"IsCancel":false,"CancelMsg":"","AddTime":1534950251}],"Count":2},{"OrderType":"is_receive","OrderData":[{"Id":40,"UserId":3,"Tel":"15623166629","Address":"毛坦港湾","Receiver":"宋志文","Amount":0,"PayType":"cash","IsDiscounts":false,"DiscountMsg":"无折扣","Msg":"加辣","Item":"订单","IsPay":false,"Appoint":false,"AppointTime":1775138426,"IsSend":false,"IsSend1":false,"SenderId":0,"SendType":0,"IsReceive":false,"ReceiveTiem":1775138426,"IsCancel":false,"CancelMsg":"","AddTime":1535078043},{"Id":39,"UserId":3,"Tel":"15623166629","Address":"毛坦港湾","Receiver":"宋志文","Amount":0,"PayType":"cash","IsDiscounts":false,"DiscountMsg":"无折扣","Msg":"加辣","Item":"订单","IsPay":false,"Appoint":false,"AppointTime":1647343488,"IsSend":false,"IsSend1":false,"SenderId":0,"SendType":0,"IsReceive":false,"ReceiveTiem":1647343488,"IsCancel":false,"CancelMsg":"","AddTime":1534950251}],"Count":2}]
	 * status : 200
	 * time : 2018-08-25 10:51:41
	 */
	
	private int status;
	private String time;
	private List<DatasBean> datas;
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public List<DatasBean> getDatas() {
		return datas;
	}
	
	public void setDatas(List<DatasBean> datas) {
		this.datas = datas;
	}
	
	public static class DatasBean {
		/**
		 * OrderType : is_pay
		 * OrderData : [{"Id":40,"UserId":3,"Tel":"15623166629","Address":"毛坦港湾","Receiver":"宋志文","Amount":0,"PayType":"cash","IsDiscounts":false,"DiscountMsg":"无折扣","Msg":"加辣","Item":"订单","IsPay":false,"Appoint":false,"AppointTime":1775138426,"IsSend":false,"IsSend1":false,"SenderId":0,"SendType":0,"IsReceive":false,"ReceiveTiem":1775138426,"IsCancel":false,"CancelMsg":"","AddTime":1535078043},{"Id":39,"UserId":3,"Tel":"15623166629","Address":"毛坦港湾","Receiver":"宋志文","Amount":0,"PayType":"cash","IsDiscounts":false,"DiscountMsg":"无折扣","Msg":"加辣","Item":"订单","IsPay":false,"Appoint":false,"AppointTime":1647343488,"IsSend":false,"IsSend1":false,"SenderId":0,"SendType":0,"IsReceive":false,"ReceiveTiem":1647343488,"IsCancel":false,"CancelMsg":"","AddTime":1534950251}]
		 * Count : 2
		 */
		
		private String OrderType;
		private int Count;
		private List<OrderBean> OrderData;
		
		public String getOrderType() {
			return OrderType;
		}
		
		public void setOrderType(String OrderType) {
			this.OrderType = OrderType;
		}
		
		public int getCount() {
			return Count;
		}
		
		public void setCount(int Count) {
			this.Count = Count;
		}
		
		public List<OrderBean> getOrderData() {
			return OrderData;
		}
		
		public void setOrderData(List<OrderBean> OrderData) {
			this.OrderData = OrderData;
		}

	}
}
