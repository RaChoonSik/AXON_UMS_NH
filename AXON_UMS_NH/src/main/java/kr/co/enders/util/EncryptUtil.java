/**
 * 작성자 : 김준희
 * 작성일시 : 2021.07.06
 * 설명 : 문자열을 암호화 처리
 */
package kr.co.enders.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.StringFixedSaltGenerator;

import com.ksign.securedb.api.SDBCrypto;

public class EncryptUtil {
	private static Logger logger = Logger.getLogger(EncryptUtil.class);
	
	/**
	 * 문자열을 SHA256으로 암호화(해싱)한다.
	 * @param str
	 * @return
	 */
	public static String getEncryptedSHA256(String str) {
		String result = "";
		if(str == null) {
			return "";
		} else {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				digest.reset();
				digest.update(str.getBytes());
				byte[] hash = digest.digest();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < hash.length; i++) {
					sb.append(Integer.toString((hash[i]&0xff) + 0x100, 16).substring(1));
				}
				result = sb.toString();
			} catch (NoSuchAlgorithmException nsae) {
				result = str;
			}
			return result;
		}
	}
	
	/**
	 * 문자열을 Jasypt library로 암호화한다.(가변)
	 * @param algorithm
	 * @param password
	 * @param str
	 * @return
	 */
	public static String getJasyptEncryptedString(String algorithm, String password, String str) {
		try {
			StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
			encryptor.setAlgorithm(algorithm);
			encryptor.setPassword(password);
			return encryptor.encrypt(str);
		} catch(Exception e) {
			logger.error("getJasyptEncryptedString error = " + e);
			return str;
		}
	}

	/**
	 * 문자열을 Jasypt library로 복호화한다.(가변)
	 * @param algorithm
	 * @param password
	 * @param str
	 * @return
	 */
	public static String getJasyptDecryptedString(String algorithm, String password, String str) {
		try {
			StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
			encryptor.setAlgorithm(algorithm);
			encryptor.setPassword(password);
			return encryptor.decrypt(str);
		} catch(Exception e) {
			logger.error("getJasyptDecryptedString error = " + e);
			return str;
		}
	}
	
	/**
	 * 문자열을 Jasypt library로 암호화한다.(고정)
	 * @param algorithm
	 * @param password
	 * @param str
	 * @return
	 */
	public static String getJasyptEncryptedFixString(String algorithm, String password, String str) {
		try {
			StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
			encryptor.setAlgorithm(algorithm);
			encryptor.setPassword(password);
			encryptor.setSaltGenerator(new StringFixedSaltGenerator(password));
			return encryptor.encrypt(str);
		} catch(Exception e) {
			logger.error("getJasyptEncryptedUnFixString error = " + e);
			return str;
		}
	}
	
	/**
	 * 문자열을 Jasypt library로 복호화한다.(고정)
	 * @param algorithm
	 * @param password
	 * @param str
	 * @return
	 */
	public static String getJasyptDecryptedFixString(String algorithm, String password, String str) {
		try {
			StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
			encryptor.setAlgorithm(algorithm);
			encryptor.setPassword(password);
			encryptor.setSaltGenerator(new StringFixedSaltGenerator(password));
			return encryptor.decrypt(str);
		} catch(Exception e) {
			logger.error("getJasyptDecryptedUnFixString error = " + e);
			return str;
		}
	}
	
	/**
	 * 문자열을 Base64로 인코딩한다.
	 * @param str
	 * @return
	 */
	public static String getBase64EncodedString(String str) {
		try {
			Encoder encoder = Base64.getEncoder();
			return new String(encoder.encode(str.getBytes()));
		} catch(Exception e) {
			logger.error("getBase64EncodedString Error = " + e.getMessage());
			return str;
		}
	}
	
	/**
	 * 문자열을 Base64로 디코딩한다.
	 * @param str
	 * @return
	 */
	public static String getBase64DecodedString(String str) {
		try {
			Decoder decoder = Base64.getDecoder();
			return new String(decoder.decode(str.getBytes()));
		} catch(Exception e) {
			logger.error("getBase64DecodedString Error = " + e.getMessage());
			return str;			
		}
	}

	/**
	 * GRS 암호화한다.
	 * @param algorithm
	 * @param password
	 * @param str
	 * @return
	 */
	public static String getGrsEncryptedString(String domainName, String serverIP, int serverPort,  String schema , String table, String column, String str) {
		try {
			
			SDBCrypto crypto =  new SDBCrypto();
			crypto = SDBCrypto.getInstanceDomain(domainName, serverIP, serverPort); 
			return crypto.encryptDP(schema, table, column, str, null, 0); 
		} catch(Exception e) {
			logger.error("getGrsEncryptedString error = " + e);
			return str;
		}
	}
	
	/**
	 * GRS복호화
	 * @param algorithm
	 * @param password
	 * @param str
	 * @return
	 */
	public static String getGrsDecryptedString(String domainName, String serverIP, int serverPort,  String schema , String table, String column, String str) {
		try {
			
			SDBCrypto crypto =  new SDBCrypto(); 
			crypto = SDBCrypto.getInstanceDomain(domainName, serverIP, serverPort);  
			return crypto.decryptDP(schema, table, column, str, null, 0); 
		} catch(Exception e) {
			logger.error("getGrsDecryptedString error = " + e);
			return str;
		}
	}
}
