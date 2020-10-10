package notification.service;

import java.util.List;

import notification.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserService {

	User getUser(String userId);

	List<User> getUsers();

}
