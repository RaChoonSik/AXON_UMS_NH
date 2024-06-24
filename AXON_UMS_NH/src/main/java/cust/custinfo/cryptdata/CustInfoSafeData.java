package cust.custinfo.cryptdata;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import kr.co.enders.util.EncryptUtil;
import kr.co.enders.util.PropertiesUtil;
import kr.co.enders.util.StringUtil;

public class CustInfoSafeData {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private PropertiesUtil properties; 
	/**
	 * 암호화
	 * @param data
	 * @param type (RNNO/NOT_RNNO)
	 * @return
	 * @throws Exception
	 */
	public synchronized String getEncrypt(String data, String type) throws Exception {
		//return EncryptUtil.getJasyptEncryptedString("PBEWithMD5AndDES", type, data);
		//return EncryptUtil.getJasyptEncryptedFixString("PBEWithMD5AndDES", type, data);
		
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		properties = (PropertiesUtil) context.getBean("properties");
		
		//NETWORK.ENV
		String networkEv = "PRD";
		
		if(!StringUtil.isNull(properties.getProperty("NETWORK.ENV"))) {
			networkEv = properties.getProperty("NETWORK.ENV");
		} 
		
		if ("INTERNAL".equals(networkEv)) {
			String domainName  = "";
			String serverIP  = "";
			String schema  = "";
			String table  = "";
			String column  = "";
			int serverPort  =  0; 
			
			int cntCryptoInfo = 0;
			
			if(!StringUtil.isNull(properties.getProperty("CRYPT.DOMAIN_NAME"))) {
				domainName =properties.getProperty("CRYPT.DOMAIN_NAME");
				cntCryptoInfo++;
			} 
			if(!StringUtil.isNull(properties.getProperty("CRYPT.SERVER_IP"))) {
				serverIP =properties.getProperty("CRYPT.SERVER_IP");
				cntCryptoInfo++;
			} 
			if(!StringUtil.isNull(properties.getProperty("CRYPT.SCHEMA"))) {
				schema =properties.getProperty("CRYPT.SCHEMA");
				cntCryptoInfo++;
			} 
			if(!StringUtil.isNull(properties.getProperty("CRYPT.TABLE"))) {
				table =properties.getProperty("CRYPT.TABLE");
				cntCryptoInfo++;
			} 
			if(!StringUtil.isNull(properties.getProperty("CRYPT.COLUMN"))) {
				column =properties.getProperty("CRYPT.COLUMN");
				cntCryptoInfo++;
			} 
			if( StringUtil.setNullToInt(properties.getProperty("CRYPT.SERVER_PORT")) > 0 ){
				serverPort = StringUtil.setNullToInt(properties.getProperty("CRYPT.SERVER_PORT"));
				cntCryptoInfo++;
			}
			
			if (cntCryptoInfo  == 6 ) {
				return EncryptUtil.getGrsEncryptedString(domainName, serverIP, serverPort, schema, table, column, data);
			} else {
				return data;
			}
		} else {
			return EncryptUtil.getJasyptEncryptedFixString("PBEWithMD5AndDES", type, data);
		}

	}
	
	/**
	 * 복호화
	 * @param data
	 * @param type (RNNO/NOT_RNNO)
	 * @return
	 * @throws Exception
	 */
	public synchronized String getDecrypt(String data, String type) throws Exception {
		//return EncryptUtil.getJasyptDecryptedString("PBEWithMD5AndDES", type, data);
		//return EncryptUtil.getJasyptDecryptedFixString("PBEWithMD5AndDES", type, data);
		
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		properties = (PropertiesUtil) context.getBean("properties");
		
		//NETWORK_ENV
		String networkEv = "PRD";
		
		if(!StringUtil.isNull(properties.getProperty("NETWORK.ENV"))) {
			networkEv = properties.getProperty("NETWORK.ENV");
		} 
		
		if ("PRD".equals(networkEv)) {
			String domainName  = "";
			String serverIP  = "";
			String schema  = "";
			String table  = "";
			String column  = "";
			int serverPort  =  0; 
			
			int cntCryptoInfo = 0;
			
			if(!StringUtil.isNull(properties.getProperty("CRYPT.DOMAIN_NAME"))) {
				domainName =properties.getProperty("CRYPT.DOMAIN_NAME");
				cntCryptoInfo++;
			} 
			if(!StringUtil.isNull(properties.getProperty("CRYPT.SERVER_IP"))) {
				serverIP =properties.getProperty("CRYPT.SERVER_IP");
				cntCryptoInfo++;
			} 
			if(!StringUtil.isNull(properties.getProperty("CRYPT.SCHEMA"))) {
				schema =properties.getProperty("CRYPT.SCHEMA");
				cntCryptoInfo++;
			} 
			if(!StringUtil.isNull(properties.getProperty("CRYPT.TABLE"))) {
				table =properties.getProperty("CRYPT.TABLE");
				cntCryptoInfo++;
			} 
			if(!StringUtil.isNull(properties.getProperty("CRYPT.COLUMN"))) {
				column =properties.getProperty("CRYPT.COLUMN");
				cntCryptoInfo++;
			} 
			if( StringUtil.setNullToInt(properties.getProperty("CRYPT.SERVER_PORT")) > 0 ){
				serverPort = StringUtil.setNullToInt(properties.getProperty("CRYPT.SERVER_PORT"));
				cntCryptoInfo++;
			}
			
			if (cntCryptoInfo  == 6 ) {
				return EncryptUtil.getGrsDecryptedString(domainName, serverIP, serverPort, schema, table, column, data);
			} else {
				return data;
			}
		} else {
			return EncryptUtil.getJasyptDecryptedFixString("PBEWithMD5AndDES", type, data);
		}
	}

}


