package notification.repository;

import java.util.List;

import notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INofiticationRepository extends JpaRepository<Notification, Short> {

	List<Notification> findAllByUserId(Short userId);

}