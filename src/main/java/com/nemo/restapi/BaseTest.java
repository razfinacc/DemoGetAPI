package com.nemo.restapi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
	public Properties prop;

	public int RESPONE_STATUS_CODE_200 = 200;
	public int RESPONE_STATUS_CODE_201 = 201;
	public int RESPONE_STATUS_CODE_204 = 204;
	public int RESPONE_STATUS_CODE_400 = 400;
	public int RESPONE_STATUS_CODE_401 = 401;
	public int RESPONE_STATUS_CODE_500 = 500;

	public BaseTest() {
	try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream("C:\\Users\\rajekumar\\eclipse-workspace\\RestApiDemo\\src\\main\\java\\com\\nemo\\restapi\\config.properties");
			prop.load(ip);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
}
