package org.healthystyle.notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.healthystyle.notification.status.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(indexes = { @Index(name = "notificaion_to_user_id_idx", columnList = "to_user_id"),
		@Index(name = "notificaion_type_idx", columnList = "type") })
public class Notification {
	@Id
	@SequenceGenerator(name = "notification_generator", sequenceName = "notification_seq", initialValue = 1, allocationSize = 5)
	@GeneratedValue(generator = "notification_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String type;
	@Column
	private Long fromUserId;
	@Column(nullable = false)
	private Long toUserId;
	@OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Option> options;
	@ManyToOne
	@JoinColumn(name = "status_id", nullable = false)
	private Status status;
	@Column(nullable = false, unique = true)
	private String identifier;
	@Column(nullable = false)
	private Instant createdOn = Instant.now();

	public Notification() {
		super();
	}

	public Notification(String title, String type, Long fromUserId, Long toUserId, Status status, String identifier) {
		super();
		this.title = title;
		this.type = type;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.status = status;
		this.identifier = identifier;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public Long getFromUserId() {
		return fromUserId;
	}

	public Long getToUserId() {
		return toUserId;
	}

	public List<Option> getOptions() {
		if (options == null) {
			options = new ArrayList<>();
		}
		return options;
	}

	public void addOptions(List<Option> options) {
		getOptions().addAll(options);
	}

	public void addOption(Option option) {
		getOptions().add(option);
	}

	public String getIdentifier() {
		return identifier;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
