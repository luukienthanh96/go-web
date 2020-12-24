/**
 * 
 */
package com.goweb.webapp.core.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Kraken
 *
 */
public class EncrytedPasswordUtils {


	public static String encrytePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	public static void main(String[] args) {
		String password = "123456";
		String encrytedPassword = encrytePassword(password);

		System.out.println("Encryted Password: " + encrytedPassword);
	}
}
