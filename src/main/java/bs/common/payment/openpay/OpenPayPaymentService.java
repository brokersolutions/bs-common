package bs.common.payment.openpay;

import org.springframework.stereotype.Service;

import bs.common.payment.openpay.OpenPayCard.OpenPayCardResponse;
import bs.common.payment.openpay.OpenPayCard.OpenPayCardSecure;
import bs.common.payment.openpay.OpenPayCharge.OpenPayChargeResponse;
import bs.common.payment.openpay.OpenPayCustomer.OpenPayCustomerResponse;

/**
 * Service that manages the OpenPay's operations
 *
 * 
 * @see OpenPayPayment
 * @author Maikel Guerra Ferrer
 */
@Service
public class OpenPayPaymentService extends OpenPayPayment {

	@Override
	public void openPayConfig(String url, String apiKey, String merchantId) {
		super.openPayConfig(url, apiKey, merchantId);
	}

	@Override
	public OpenPayCustomerResponse createCustomer(OpenPayCustomer bean) throws Exception {
		return super.createCustomer(bean);
	}

	@Override
	public OpenPayCustomerResponse updateCustomer(OpenPayCustomer bean) throws Exception {
		return super.updateCustomer(bean);
	}

	@Override
	public Boolean deleteCustomer(OpenPayCustomer bean) throws Exception {
		return super.deleteCustomer(bean);
	}

	@Override
	public OpenPayCardResponse createCard(OpenPayCard bean) throws Exception {
		return super.createCard(bean);
	}

	@Override
	public OpenPayCardResponse createCard(OpenPayCardSecure bean) throws Exception {
		return super.createCard(bean);
	}

	@Override
	public Boolean deleteCard(OpenPayCard bean) throws Exception {
		return super.deleteCard(bean);
	}

	@Override
	public OpenPayChargeResponse charge(OpenPayCharge bean) throws Exception {
		return super.charge(bean);
	}
	
	@Override
	public void showLogs() {
		super.showLogs();
	}

}
