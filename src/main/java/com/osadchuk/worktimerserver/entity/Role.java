package com.osadchuk.worktimerserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Entity which represents role table in DB
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "role", schema = "public")
public class Role {
	@GenericGenerator(
			name = "roleSequenceGenerator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "sequence_name", value = "roleSequence"),
					@org.hibernate.annotations.Parameter(name = "initial_value", value = "3"),
					@org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
			}
	)

	@Id
	@GeneratedValue(generator = "roleSequenceGenerator")
	private long id;
	private String name;
	private String description;

	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;
}
