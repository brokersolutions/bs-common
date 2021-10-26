package bs.common.delivery.dostavista;

import java.util.List;

import bs.common.util.MessageUtil;

/**
 * Dostavista delivery and order statuses
 * 
 * 
 * @author Maikel Guerra Ferrer
 */
public class DostavistaStatuses {
	/**
	 * Delivery statuses 
	 * 
	 * 
	 * @return List with delivery status
	 */
	protected static List<String> getDeliveryStatus() {
		return List.of("invalid", "draft", "planned", "active", "finished", "cancelled", "delayed", "failed",
				"courier_assigned", "courier_departed", "parcel_picked_up", "courier_arrived", "deleted");
	}
	
	/**
	 * Validates if the given status is in delivery's statuses list
	 * 
	 * @param status - (String) An status to validate
	 * 
	 * @return True if the given status is in delivery's statuses list
	 */
	protected static Boolean isValidDeliveryStatus(final String status) {
		return getDeliveryStatus().stream()
								  .filter(item -> item.equals(status))
								  .findAny()
								  .isPresent();
	}

	/**
	 * Delivery status internationalization
	 * 
	 * @param status - (String) current status
	 * 
	 * @return Delivery status internationalization
	 */
	protected static String getDeliveryStatus(final String status) {
		return switch (status) {
		case "invalid" -> MessageUtil.getMessage("dostavista.delivery.status.invalid");
		case "draft" -> MessageUtil.getMessage("dostavista.delivery.status.draft");
		case "planned" -> MessageUtil.getMessage("dostavista.delivery.status.planned");
		case "active" -> MessageUtil.getMessage("dostavista.delivery.status.active");
		case "finished" -> MessageUtil.getMessage("dostavista.delivery.status.finished");
		case "cancelled" -> MessageUtil.getMessage("dostavista.delivery.status.cancelled");
		case "delayed" -> MessageUtil.getMessage("dostavista.delivery.status.delayed");
		case "failed" -> MessageUtil.getMessage("dostavista.delivery.status.failed");
		case "courier_assigned" -> MessageUtil.getMessage("dostavista.delivery.status.courier_assigned");
		case "courier_departed" -> MessageUtil.getMessage("dostavista.delivery.status.courier_departed");
		case "parcel_picked_up" -> MessageUtil.getMessage("dostavista.delivery.status.parcel_picked_up");
		case "courier_arrived" -> MessageUtil.getMessage("dostavista.delivery.status.courier_arrived");
		case "deleted" -> MessageUtil.getMessage("dostavista.delivery.status.deleted");
		default -> throw new IllegalArgumentException(
				"DostavistaStatuses#getDeliveryStatus unexpected value: " + status);
		};
	}

	/**
	 * Order statuses 
	 * 
	 * 
	 * @return List with order status
	 */
	protected static List<String> getOrderStatus() {
		return List.of("new", "available", "active", "completed", "reactiveted", "draft", "delayed", "cancelled");
	}
	
	/**
	 * Validates if the given status is in order's statuses list
	 * 
	 * @param status - (String) An status to validate
	 * 
	 * @return True if the given status is in order status list
	 */
	protected static Boolean isValidOrderStatus(final String status) {
		return getOrderStatus().stream()
							   .filter(item -> item.equals(status))
							   .findAny()
							   .isPresent();
	}
	
	/**
	 * Order status internationalization
	 * 
	 * @param status - (String) current status
	 * 
	 * @return Order status internationalization
	 */
	protected static String getOrderStatus(final String status) {
		return switch (status) {
		case "new" -> MessageUtil.getMessage("dostavista.order.status.new");
		case "available" -> MessageUtil.getMessage("dostavista.order.status.available");
		case "active" -> MessageUtil.getMessage("dostavista.order.status.active");
		case "completed" -> MessageUtil.getMessage("dostavista.order.status.completed");
		case "reactiveted" -> MessageUtil.getMessage("dostavista.order.status.reactiveted");
		case "draft" -> MessageUtil.getMessage("dostavista.order.status.draft");
		case "delayed" -> MessageUtil.getMessage("dostavista.order.status.delayed");
		case "cancelled" -> MessageUtil.getMessage("dostavista.order.status.cancelled");
		default -> throw new IllegalArgumentException("DostavistaStatuses#getOrderStatus unexpected value: " + status);
		};
	}
}