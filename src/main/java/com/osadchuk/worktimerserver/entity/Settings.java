package com.osadchuk.worktimerserver.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity which represents settings table in DB
 */
@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "settings", schema = "public")
public class Settings {
	@GenericGenerator(
			name = "userSequenceGenerator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "sequence_name", value = "settingsSequence"),
					@org.hibernate.annotations.Parameter(name = "initial_value", value = "5"),
					@org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
			}
	)

	@Id
	@GeneratedValue(generator = "userSequenceGenerator")
	private long id;

	private String name;

	private String value;

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", value='" + value +
				'}';
	}

}
