package com.osadchuk.worktimerserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity which represents time_log table in DB
 */
@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "time_log", schema = "public")
public class TimeLog {
	@GenericGenerator(
			name = "timeLogSequenceGenerator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "sequence_name", value = "timeLogSequence"),
					@org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
					@org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
			}
	)

	@Id
	@GeneratedValue(generator = "timeLogSequenceGenerator")
	private long id;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	@JoinColumn(name = "task_id", referencedColumnName = "id")
	@ManyToOne
	private Task task;

	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne
	private User user;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "timeLog")
	private List<Screenshot> screenshotList;

	@Override
	public String toString() {
		return "TimeLog{" +
				"id=" + id +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", task=" + task +
				", user=" + user +
				'}';
	}
}
