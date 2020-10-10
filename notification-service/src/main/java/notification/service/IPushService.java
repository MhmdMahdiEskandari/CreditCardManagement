package notification.service;

import notification.exception.PushException;
import notification.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IPushService {
	public void pushNotification(User user, String message) throws PushException;
}