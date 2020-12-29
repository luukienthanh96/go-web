/**
 * 
 */
package com.goweb.webapp.repository.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Kraken
 *
 */
@Entity
@Table(name = "Users")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5399457077880624264L;

	private String username;
	private String password;
	private boolean enabled;

	private List<UserRole> userRoles;

	/**
	 * 
	 */
	public User() {
		super();
	}

	/**
	 * @param username
	 * @param password
	 * @param enabled
	 */
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = false; // No-active
	}

	/**
	 * @param username
	 * @param password
	 * @param userRoles
	 */
	public User(String username, String password, List<UserRole> userRoles) {
		super();
		this.username = username;
		this.password = password;
		this.userRoles = userRoles;
	}

	/**
	 * @return the username
	 */
	@Id
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
	 * @return the password
	 */
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enabled
	 */
	@Column(name = "enabled")
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the userRoles
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "username")
	@Fetch(value = FetchMode.SUBSELECT)
	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	/**
	 * @param userRoles
	 *            the userRoles to set
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}
