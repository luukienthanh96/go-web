package com.goweb.webapp.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Aes encryption
 * 
 * @author kraken.
 */
public class AES {

	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";

	public static void encrypt(String key, File inputFile, File outputFile) {
		doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
	}

	public static void decrypt(String key, File inputFile, File outputFile) {
		doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
	}

	private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) {
		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);

			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);

			byte[] outputBytes = cipher.doFinal(inputBytes);

			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(outputBytes);
			
			inputStream.close();
			outputStream.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String encryptString(String key, String inputString) {
		return encript(Cipher.ENCRYPT_MODE, key, inputString);
	}

	public static String decryptString(String key, String inputString) {
		return encript(Cipher.DECRYPT_MODE, key, inputString);
	}

	private static String encript(int cipherMode, String key, String inputString) {
		String rs = null;
		try {

			Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);
			if (cipherMode == Cipher.ENCRYPT_MODE) {
				byte[] inputBytes = inputString.getBytes("utf-8");
				byte[] outputBytes = cipher.doFinal(inputBytes);
				rs = Base64.encode(outputBytes);
			} else {
				byte[] outputBytes = cipher.doFinal(Base64.decode(inputString));
				rs = new String(outputBytes, "utf-8");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rs;
	}

	public static void main(String[] args) {
		int a = 1;
		Integer b = 1;
		String c = "aaa";
		Integer d = 1;
		
		System.out.print(a == b);
		System.out.print(b == d);
		System.out.print(a == b);
		
		System.out.println("Decrypt request : " + decryptString("@GENERALIGENSIS#",
				"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"));
	}
}
