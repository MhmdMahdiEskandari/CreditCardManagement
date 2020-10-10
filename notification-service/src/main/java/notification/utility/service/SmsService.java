package notification.utility.service;

import notification.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

	private static final Logger LOG = LoggerFactory.getLogger(SmsService.class);

	private static final String SENDER_NUMBER = "+989197407606";

	@Async
	public void sendSMS(User user, String message) {
		Message msg;
		try {
			PhoneNumber toNumber = new com.twilio.type.PhoneNumber(user.getPhoneNumber());
			PhoneNumber fromNumber = new com.twilio.type.PhoneNumber(SENDER_NUMBER);
			msg = Message.creator(toNumber, fromNumber, message).create();
			LOG.info("Message sent with sid: {}", msg.getSid());
		} catch (Exception ex) {
			LOG.error("SMS sending failed. Error={}", ex.getMessage());
		}
	}
}
