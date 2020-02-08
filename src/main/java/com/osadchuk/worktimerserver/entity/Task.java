package com.osadchuk.worktimerserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Entity which represents task table in DB
 */
@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "task", schema = "public")
public class Task {
	@GenericGenerator(
			name = "taskSequenceGenerator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "sequence_name", value = "taskSequence"),
					@org.hibernate.annotations.Parameter(name = "initial_value", value = "2"),
					@org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
			}
	)

	@Id
	@GeneratedValue(generator = "taskSequenceGenerator")
	private long id;

	private String name;

	private String description;

	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;

	@JsonIgnore
	@OneToMany(mappedBy = "task")
	private List<TimeLog> timeLogList;

	@Override
	public String toString() {
		return "Task {" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", user=" + user +
				'}';
	}
}
