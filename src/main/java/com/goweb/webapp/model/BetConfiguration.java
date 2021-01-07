/**
 * 
 */
package com.goweb.webapp.model;

import java.io.Serializable;

/**
 * @author Krakenpham
 *
 */
public class BetConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3285500686178344247L;

	private String betType = "";

	
	/**
	 * 
	 */
	public BetConfiguration() {
		super();
	}

	/**
	 * @param betType
	 */
	public BetConfiguration(String betType) {
		super();
		this.betType = betType;
	}

	/**
	 * @return the betType
	 */
	public String getBetType() {
		return betType;
	}

	/**
	 * @param betType the betType to set
	 */
	public void setBetType(String betType) {
		this.betType = betType;
	}

}
