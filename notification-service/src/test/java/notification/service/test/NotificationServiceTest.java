package notification.service.test;

import notification.dto.NotificationDTO;
import notification.model.User;
import notification.service.NotificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import notification.exception.BaseException;
import notification.exception.UserNotFoundException;
import notification.repository.INofiticationRepository;
import notification.service.IUserService;
import notification.utility.service.MobilePushService;
import notification.utility.service.SmsService;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {

	@Mock
	private IUserService userService;

	@Mock
	private INofiticationRepository notifRepo;

	@Mock
	private SmsService smsService;

	@Mock
	private MobilePushService androidService;

	@InjectMocks
	private NotificationService service;

	@Test
	public void notifyTest_Success() throws BaseException {
		NotificationDTO notification = new NotificationDTO();
		notification.setMessage("Hi test message");
		String userId = "1";
		User user = new User();
		Mockito.when(userService.getUser(userId)).thenReturn(user);
		service.notify(userId, notification);
	}

	@Test(expected = UserNotFoundException.class)
	public void notifyTest_UserNotFound() throws BaseException {
		NotificationDTO notification = new NotificationDTO();
		String userId = "1";
		service.notify(userId, notification);
	}
}
