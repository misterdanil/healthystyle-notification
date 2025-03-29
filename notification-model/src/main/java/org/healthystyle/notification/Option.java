package org.healthystyle.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(indexes = @Index(name = "option_notification_id_idx", columnList = "notification_id"))
public class Option {
	@Id
	@SequenceGenerator(name = "option_generator", sequenceName = "option_seq", initialValue = 1, allocationSize = 10)
	@GeneratedValue(generator = "option_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String link;
	@ManyToOne
	@JoinColumn(name = "notification_id", nullable = false)
	private Notification notification;

	public Option() {
		super();
	}

	public Option(String title, String link, Notification notification) {
		super();
		this.title = title;
		this.link = link;
		this.notification = notification;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

}
