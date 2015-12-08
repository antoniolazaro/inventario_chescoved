package br.com.vortice.chescoved.inventario.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	static{
		simpleDateFormat.setLenient(false);	
	}
	
	public static Date parse(String data) throws Exception{
		try{
			return simpleDateFormat.parse(data);
		}catch(Exception ex){
			throw new Exception("Data com valor inválido. "+ex.getMessage());
		}
	}
	
}
