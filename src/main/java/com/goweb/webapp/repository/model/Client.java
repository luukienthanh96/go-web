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

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.goweb.webapp.core.util.DateUtils;
import com.goweb.webapp.model.ClientConfiguration;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

/**
 * @author Krakenpham
 *
 */
@Entity
@Table(name = "client")
@TypeDefs({
	@TypeDef(name = "json", typeClass = JsonStringType.class),
	@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2667440940062397859L;

	private Long id;
	private String fullname;
	private String email;
	private String phoneNumber;
	private boolean gender;
	private boolean enabled;
	private String client_serve_user;
	private ClientConfiguration client_config;
	private String regis_dt;
	private String regis_time;
	private String regis_by;
	private String updated_dt;
	private String updated_time;
	private String updated_by;

	/**
	 * 
	 */
	public Client() {
		super();
		this.client_config = new ClientConfiguration();
	}

	/**
	 * @param regis_by
	 */
	public Client(String client_serve_user) {
		super();
		this.client_serve_user = client_serve_user;
		this.regis_dt = DateUtils.getSystemDateStr();
		this.regis_time = DateUtils.getSystemTimeStr();
		this.regis_by = client_serve_user;
		this.updated_dt = this.regis_dt;
		this.updated_time = this.regis_time;
		this.updated_by = client_serve_user;
		this.client_config = new ClientConfiguration();
		
	}

	/**
	 * @param fullname
	 * @param email
	 * @param phoneNumber
	 * @param gender
	 * @param enabled
	 * @param client_serve_user
	 * @param client_config
	 * @param regis_dt
	 * @param regis_time
	 * @param regis_by
	 * @param updated_dt
	 * @param updated_time
	 * @param updated_by
	 */
	public Client(String fullname, String email, String phoneNumber, boolean gender, boolean enabled,
			String client_serve_user, ClientConfiguration client_config, String regis_dt, String regis_time, String regis_by,
			String updated_dt, String updated_time, String updated_by) {
		super();
		this.fullname = fullname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.enabled = enabled;
		this.client_serve_user = client_serve_user;
		this.client_config = client_config;
		this.regis_dt = regis_dt;
		this.regis_time = regis_time;
		this.regis_by = regis_by;
		this.updated_dt = updated_dt;
		this.updated_time = updated_time;
		this.updated_by = updated_by;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the fullname
	 */
	@Column(name = "fullname")
	public String getFullname() {
		return fullname;
	}

	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @return the email
	 */
	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNumber
	 */
	@Column(name = "phoneNumber")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the gender
	 */
	@Column(name = "gender")
	public boolean isGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(boolean gender) {
		this.gender = gender;
	}

	/**
	 * @return the enabled
	 */
	@Column(name = "enabled")
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the client_serve_user
	 */
	@Column(name = "client_serve_user")
	public String getClient_serve_user() {
		return client_serve_user;
	}

	/**
	 * @param client_serve_user the client_serve_user to set
	 */
	public void setClient_serve_user(String client_serve_user) {
		this.client_serve_user = client_serve_user;
	}

	/**
	 * @return the client_config
	 */
	@Type(type = "json")
	@Column(name = "client_config", columnDefinition = "json")
	public ClientConfiguration getClient_config() {
		return client_config;
	}

	/**
	 * @param client_config the client_config to set
	 */
	public void setClient_config(ClientConfiguration client_config) {
		this.client_config = client_config;
	}

	/**
	 * @return the regis_dt
	 */
	@Column(name = "regis_dt")
	public String getRegis_dt() {
		return regis_dt;
	}

	/**
	 * @param regis_dt the regis_dt to set
	 */
	public void setRegis_dt(String regis_dt) {
		this.regis_dt = regis_dt;
	}

	/**
	 * @return the regis_time
	 */
	@Column(name = "regis_time")
	public String getRegis_time() {
		return regis_time;
	}

	/**
	 * @param regis_time the regis_time to set
	 */
	public void setRegis_time(String regis_time) {
		this.regis_time = regis_time;
	}

	/**
	 * @return the regis_by
	 */
	@Column(name = "regis_by")
	public String getRegis_by() {
		return regis_by;
	}

	/**
	 * @param regis_by the regis_by to set
	 */
	public void setRegis_by(String regis_by) {
		this.regis_by = regis_by;
	}

	/**
	 * @return the updated_dt
	 */
	@Column(name = "updated_dt")
	public String getUpdated_dt() {
		return updated_dt;
	}

	/**
	 * @param updated_dt the updated_dt to set
	 */
	public void setUpdated_dt(String updated_dt) {
		this.updated_dt = updated_dt;
	}

	/**
	 * @return the updated_time
	 */
	@Column(name = "updated_time")
	public String getUpdated_time() {
		return updated_time;
	}

	/**
	 * @param updated_time the updated_time to set
	 */
	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	/**
	 * @return the updated_by
	 */
	@Column(name = "updated_by")
	public String getUpdated_by() {
		return updated_by;
	}

	/**
	 * @param updated_by the updated_by to set
	 */
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

}
