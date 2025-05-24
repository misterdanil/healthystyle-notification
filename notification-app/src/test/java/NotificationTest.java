import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.healthystyle.notification.Notification;
import org.healthystyle.notification.Option;
import org.healthystyle.notification.app.Main;
import org.healthystyle.notification.repository.NotificationRepository;
import org.healthystyle.notification.repository.OptionRepository;
import org.healthystyle.notification.service.NotificationService;
import org.healthystyle.notification.service.dto.NotificationSaveRequest;
import org.healthystyle.notification.service.dto.OptionSaveRequest;
import org.healthystyle.notification.service.error.IdentifierExistException;
import org.healthystyle.notification.service.error.user.UserNotFoundException;
import org.healthystyle.notification.status.Status;
import org.healthystyle.notification.status.Type;
import org.healthystyle.util.error.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes = Main.class)
public class NotificationTest {
	@Autowired
	private NotificationService notificationService;
	@MockitoBean
	private NotificationRepository notificationRepository;

	@MockitoBean
	private OptionRepository optionRepository;

	private Status status;

	private Notification notification;
	private NotificationSaveRequest notificationSaveRequest;

	private Option option;
	private OptionSaveRequest optionSaveRequest;

	@BeforeEach
	public void setup() {
		status = new Status(Type.UNWATCHED);

		option = new Option("Перейти к сообщение", "https://google.com", notification);
		optionSaveRequest = new OptionSaveRequest();
		optionSaveRequest.setTitle(option.getTitle());
		optionSaveRequest.setLink(option.getLink());

		notification = new Notification("Вам поступило сообщение", "message", 1L, 2L, status, "message 44");
		notification.addOption(option);

		notificationSaveRequest = new NotificationSaveRequest();
		notificationSaveRequest.setTitle(notification.getTitle());
		notificationSaveRequest.setIdentifier("message 44");
		notificationSaveRequest.setFromUserId(notification.getFromUserId());
		notificationSaveRequest.setToUserId(notification.getToUserId());
		notificationSaveRequest.setType(notification.getType());
		notificationSaveRequest.setOptions(List.of(optionSaveRequest));
	}

	@Test
	public void createNotificationTest() throws ValidationException, UserNotFoundException, IdentifierExistException {
		when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
		when(optionRepository.save(any(Option.class))).thenReturn(option);
		when(notificationRepository.existsByIdentifier(any(String.class))).thenReturn(false);

		notificationService.save(notificationSaveRequest);
		verify(notificationRepository, times(1)).save(any(Notification.class));
	}

	@Test
	public void createNotificationIdentifierExistTest()
			throws ValidationException, UserNotFoundException, IdentifierExistException {
		when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
		when(optionRepository.save(any(Option.class))).thenReturn(option);
		when(notificationRepository.existsByIdentifier(any(String.class))).thenReturn(true);

		assertThrows(IdentifierExistException.class, () -> {
			notificationService.save(notificationSaveRequest);
		});
		verify(notificationRepository, times(0)).save(any(Notification.class));
	}

	@Test
	public void createNotificationToUserIsNotDefinedTest()
			throws ValidationException, UserNotFoundException, IdentifierExistException {
		notificationSaveRequest.setToUserId(null);

		when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
		when(optionRepository.save(any(Option.class))).thenReturn(option);
		when(notificationRepository.existsByIdentifier(any(String.class))).thenReturn(false);

		assertThrows(ValidationException.class, () -> {
			notificationService.save(notificationSaveRequest);
		});
		verify(notificationRepository, times(0)).save(any(Notification.class));
	}

	@Test
	public void createNotificationOptionsDefinedTest()
			throws ValidationException, UserNotFoundException, IdentifierExistException {
		notificationSaveRequest.getOptions().clear();

		when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
		when(optionRepository.save(any(Option.class))).thenReturn(option);
		when(notificationRepository.existsByIdentifier(any(String.class))).thenReturn(false);

		notificationService.save(notificationSaveRequest);
		verify(notificationRepository, times(1)).save(any(Notification.class));
	}

	@Test
	public void createNotificationOptionsIsInvalidDefinedTest()
			throws ValidationException, UserNotFoundException, IdentifierExistException {
		notificationSaveRequest.getOptions().get(0).setLink("dawdadw");

		when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
		when(optionRepository.save(any(Option.class))).thenReturn(option);

		assertThrows(ValidationException.class, () -> {
			notificationService.save(notificationSaveRequest);
		});
		verify(notificationRepository, times(1)).save(any(Notification.class));
		verify(optionRepository, times(0)).save(any(Option.class));
	}

	@Test
	public void fetchNotificationsTest() throws ValidationException, UserNotFoundException, IdentifierExistException {
		when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
		when(optionRepository.save(any(Option.class))).thenReturn(option);

		notificationService.findByToUserId(1L, 1, 25);
		verify(notificationRepository, times(1)).findByToUserId(any(Long.class), any(PageRequest.class));
	}

	@Test
	public void fetchNotificationsWithIncorrectPageTest()
			throws ValidationException, UserNotFoundException, IdentifierExistException {
		when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
		when(optionRepository.save(any(Option.class))).thenReturn(option);

		assertThrows(ValidationException.class, () -> {
			notificationService.findByToUserId(1L, 0, 25);
		});
		verify(notificationRepository, times(0)).findByToUserId(any(Long.class), any(PageRequest.class));
	}

	@Test
	public void fetchCountNotificationsTest() throws ValidationException {
		when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
		when(optionRepository.save(any(Option.class))).thenReturn(option);

		assertThrows(ValidationException.class, () -> {
			notificationService.countUnwatched(1L);
		});
		verify(notificationRepository, times(1)).countUnwatched(any(Long.class));
	}

}
