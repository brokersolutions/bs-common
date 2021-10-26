package bs.common.delivery.enviaya;

import java.util.List;

import org.springframework.stereotype.Service;

import bs.common.delivery.enviaya.EnviayaCatalogue.CatalogueType;
import bs.common.delivery.enviaya.EnviayaPickup.EnviayaPickupResponse;
import bs.common.delivery.enviaya.EnviayaShipment.EnviayaShipmentBooking;
import bs.common.delivery.enviaya.EnviayaShipment.EnviayaShipmentData;
import bs.common.delivery.enviaya.EnviayaTracking.EnviayaTrackingResponse;

/**
 * Service that manages the EnviaYa delivery operations
 * 
 * @author Maikel Guerra Ferrer
 */
@Service
public class EnviayaDeliveryService extends EnviayaDelivery {

	@Override
	public void enviayaConfig(final String apiKey) {
		super.enviayaConfig(apiKey);
	}

	@Override
	public List<EnviayaRate> rates(final EnviayaRating enviayaRating) {
		return super.rates(enviayaRating);
	}

	@Override
	public EnviayaShipment shipmentBooking(final EnviayaShipmentBooking shipmentBooking) {
		return super.shipmentBooking(shipmentBooking);
	}

	@Override
	public EnviayaShipment shipmentLookUp(final EnviayaShipmentData data) {
		return super.shipmentLookUp(data);
	}

	@Override
	public EnviayaShipment shipmentCancellation(final EnviayaShipmentData data) {
		return super.shipmentCancellation(data);
	}

	@Override
	public EnviayaTrackingResponse shipmentTracking(final EnviayaTracking tracking) {
		return super.shipmentTracking(tracking);
	}
	
	@Override
	public EnviayaPickupResponse pickupsBooking(EnviayaPickup enviayaPickup) {
		return super.pickupsBooking(enviayaPickup);
	}

	@Override
	public EnviayaPickupResponse pickupsLookUp(EnviayaPickup enviayaPickup) {
		return super.pickupsLookUp(enviayaPickup);
	}

	@Override
	public EnviayaCatalogue getCatalogue(final CatalogueType type) {
		return super.getCatalogue(type);
	}

	@Override
	public void showLogs() {
		super.showLogs();
	}

}
