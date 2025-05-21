package org.healthystyle.notification.status;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(indexes = @Index(name = "status_type_idx", columnList = "type", unique = true))
public class Status {
	@Id
	@SequenceGenerator(name = "status_generator", sequenceName = "status_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "status_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false, unique = true)
	private Type type;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Instant createdOn = Instant.now();

	public Status() {
		super();
	}

	public Status(Type type) {
		super();
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public Type getType() {
		return type;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

}
