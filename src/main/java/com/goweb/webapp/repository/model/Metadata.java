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
 * @author GVL00940
 *
 */
@Entity
@Table(name = "Metadata")
public class Metadata implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5988804665611585304L;
	
	private Long id;
	private String lookupCode;
	private String lookupCodeId;
	private String language;
	private String value;
	private String orderBy;
	
	/**
	 * 
	 */
	public Metadata() {
		super();
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
	 * @return the lookupCode
	 */
	@Column(name = "LOOKUPCODE")
	public String getLookupCode() {
		return lookupCode;
	}
	/**
	 * @param lookupCode the lookupCode to set
	 */
	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}
	/**
	 * @return the lookupCodeId
	 */
	@Column(name = "LOOKUPCODEID")
	public String getLookupCodeId() {
		return lookupCodeId;
	}
	/**
	 * @param lookupCodeId the lookupCodeId to set
	 */
	public void setLookupCodeId(String lookupCodeId) {
		this.lookupCodeId = lookupCodeId;
	}
	/**
	 * @return the language
	 */
	@Column(name = "LANG")
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the value
	 */
	@Column(name = "VALUE")
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the orderBy
	 */
	@Column(name = "ORDERBY")
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Metadata [lookupCode=" + lookupCode + ", lookupCodeId=" + lookupCodeId + ", language=" + language
				+ ", value=" + value + ", orderBy=" + orderBy + "]";
	}
	
}

