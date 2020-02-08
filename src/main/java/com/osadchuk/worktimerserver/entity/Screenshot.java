package com.osadchuk.worktimerserver.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Entity which represents screenshot table in DB
 */
@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "screenshot", schema = "public")
public class Screenshot {
	@GenericGenerator(
			name = "screenshotSequenceGenerator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "sequence_name", value = "screenshotSequence"),
					@org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
					@org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
			}
	)

	@Id
	@GeneratedValue(generator = "screenshotSequenceGenerator")
	private long id;

	private String base64;

	private LocalDateTime date;

	@JoinColumn(name = "time_log_id", referencedColumnName = "id")
	@ManyToOne
	private TimeLog timeLog;

	@Override
	public String toString() {
		return "Screenshot{" +
				"id=" + id +
				", base64='" + base64 + '\'' +
				", date=" + date +
				", timeLog=" + timeLog +
				'}';
	}
}
