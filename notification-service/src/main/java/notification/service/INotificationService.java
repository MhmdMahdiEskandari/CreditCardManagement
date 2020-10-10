package notification.service;

import java.util.List;

import notification.dto.NotificationDTO;
import notification.exception.BaseException;
import notification.model.Notification;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationService {

	public void notify(String userId, NotificationDTO notification) throws BaseException;

	public List<Notification> getNotifications(String userId) throws BaseException;
}