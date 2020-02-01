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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Entity which represents user table in DB
 */
@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "user", schema = "public")
public class User {
	@GenericGenerator(
			name = "userSequenceGenerator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "sequence_name", value = "userSequence"),
					@org.hibernate.annotations.Parameter(name = "initial_value", value = "2"),
					@org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
			}
	)

	@Id
	@GeneratedValue(generator = "userSequenceGenerator")
	private long id;
	private String username;
	private String firstName;
	private String lastName;
	private boolean enabled;
	private String phoneNumber;
	private String password;
	private String token;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
	)
	private Set<Role> roles;
}
