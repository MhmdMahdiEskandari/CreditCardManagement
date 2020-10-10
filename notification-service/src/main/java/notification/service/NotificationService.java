package notification.service;

import java.util.List;

import notification.model.Notification;
import notification.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import notification.dto.NotificationDTO;
import notification.exception.BaseException;
import notification.exception.NotificationException;
import notification.exception.PushException;
import notification.exception.UserNotFoundException;
import notification.repository.INofiticationRepository;
import notification.utility.service.SmsService;

@Service
public class NotificationService implements INotificationService {

	private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private INofiticationRepository notifRepo;

	@Autowired
	private SmsService smsService;

	@Autowired
	@Qualifier("mobileService")
	private IPushService mobileService;

	@Override
	public void notify(String userId, NotificationDTO notification) throws BaseException {
		User user = userService.getUser(userId);
		if (user != null) {
			createNotification(notification, user);
			sendNotification(user, notification.getMessage());
		} else {
			LOG.error("User not found in database");
			throw new UserNotFoundException("User not found in database");
		}
	}

	private void createNotification(NotificationDTO notification, User user) throws BaseException {
		try {
			Notification notif = new Notification();
			notif.setMessage(notification.getMessage());
			notif.setUser(user);
			notifRepo.save(notif);
		} catch (Exception ex) {
			throw new NotificationException(ex.getMessage());
		}
	}

	private void sendNotification(User user, String message)
			throws BaseException {
		sendPush(user, message);
	}

	private void sendPush(User user, String message) throws PushException {
		mobileService.pushNotification(user, message);

	}

	@Override
	public List<Notification> getNotifications(String userId) throws NotificationException {
		try {
			return notifRepo.findAllByUserId(Short.valueOf(userId));
		} catch (NumberFormatException ex) {
			throw new NotificationException(ex.getMessage());
		}
	}
}
