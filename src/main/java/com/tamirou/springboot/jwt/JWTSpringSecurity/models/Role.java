package com.tamirou.springboot.jwt.JWTSpringSecurity.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.NaturalId;


/**
 * The persistent class for the GROUPE database table.
 * 
 */
@Data
@Entity
@Table(name="Roles")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id_role", unique=true, nullable=false)
	@JsonIgnore
	private Long idRole;

	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(length = 60)
	private RoleName name;

	//bi-directional many-to-many association to UsersTable
	@JsonIgnore
	@ManyToMany(mappedBy="roles", fetch= FetchType.LAZY)
	private List<User> users;

	public Role() {
	}
}