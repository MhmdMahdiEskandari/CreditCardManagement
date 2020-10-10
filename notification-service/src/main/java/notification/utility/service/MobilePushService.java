package notification.utility.service;

import notification.exception.PushException;
import notification.model.User;
import notification.service.IPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("mobileService")
public class MobilePushService implements IPushService {

	private static final Logger LOG = LoggerFactory.getLogger(MobilePushService.class);
	
	private static final String FIREBASE_SERVER_KEY = "Your Server Key here!";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

	@Override
	@Async
	public void pushNotification(User user, String message) throws PushException {

	}
}
