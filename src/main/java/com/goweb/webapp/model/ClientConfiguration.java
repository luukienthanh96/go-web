/**
 * 
 */
package com.goweb.webapp.model;

import java.io.Serializable;

/**
 * @author Krakenpham
 *
 */
public class ClientConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4100939058826009736L;
	private BetConfigurationNorth betNorth;
	private BetConfigurationMiddle betMiddle;
	private BetConfigurationSouth betSouth;
	private String regis_dt;
	private String regis_time;
	private String regis_by;
	private String updated_dt;
	private String updated_time;
	private String updated_by;

	/**
	 * 
	 */
	public ClientConfiguration() {
		super();
		this.betSouth = new BetConfigurationSouth();
		this.betMiddle = new BetConfigurationMiddle();
		this.betNorth = new BetConfigurationNorth();
	}

	/**
	 * @param betNorth
	 * @param betMiddle
	 * @param betSouth
	 * @param regis_dt
	 * @param regis_time
	 * @param regis_by
	 * @param updated_dt
	 * @param updated_time
	 * @param updated_by
	 */
	public ClientConfiguration(BetConfigurationNorth betNorth, BetConfigurationMiddle betMiddle,
			BetConfigurationSouth betSouth, String regis_dt, String regis_time, String regis_by, String updated_dt,
			String updated_time, String updated_by) {
		super();
		this.betNorth = betNorth;
		this.betMiddle = betMiddle;
		this.betSouth = betSouth;
		this.regis_dt = regis_dt;
		this.regis_time = regis_time;
		this.regis_by = regis_by;
		this.updated_dt = updated_dt;
		this.updated_time = updated_time;
		this.updated_by = updated_by;
	}

	/**
	 * @return the betNorth
	 */
	public BetConfigurationNorth getBetNorth() {
		return betNorth;
	}

	/**
	 * @param betNorth the betNorth to set
	 */
	public void setBetNorth(BetConfigurationNorth betNorth) {
		this.betNorth = betNorth;
	}

	/**
	 * @return the betMiddle
	 */
	public BetConfigurationMiddle getBetMiddle() {
		return betMiddle;
	}

	/**
	 * @param betMiddle the betMiddle to set
	 */
	public void setBetMiddle(BetConfigurationMiddle betMiddle) {
		this.betMiddle = betMiddle;
	}

	/**
	 * @return the betSouth
	 */
	public BetConfigurationSouth getBetSouth() {
		return betSouth;
	}

	/**
	 * @param betSouth the betSouth to set
	 */
	public void setBetSouth(BetConfigurationSouth betSouth) {
		this.betSouth = betSouth;
	}

	/**
	 * @return the regis_dt
	 */
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
