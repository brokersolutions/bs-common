package bs.common.enums;

public enum EOrderStatus {
	CONFIRMED,
	PREPARING,
	READY,
	SHIPPED,
	DELIVERED;
	
	public static enum OrderStatusCode {
		C,
		P,
		R,
		S,
		D;
	}
}