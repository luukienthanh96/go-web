/**
 * 
 */
package com.goweb.webapp.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kraken
 *
 */
@Entity
@Table(name = "User_roles")
public class UserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1081804444692204918L;

	private Long id;
	private String username;
	private String role;

	/**
	 * @param id
	 * @param username
	 * @param role
	 */
	public UserRole(Long id, String username, String role) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
	}

	/**
	 * 
	 */
	public UserRole() {
		super();
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_role_id")
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the role
	 */
	@Column(name = "role")
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

}
