package br.com.vortice.chescoved.inventario.sqlloader;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;

import org.h2.store.fs.FileUtils;
import org.h2.tools.RunScript;

import br.com.vortice.chescoved.inventario.dao.DAOAb;

public class CreateDatabaseStructure {
	
	public static final String DATABASE_PATH = "~/inventario_chescoved";
	
	public static void initDatabase() throws Exception{
		Connection connection = new DAOAb().getDBConnection();
		if(!FileUtils.exists(DATABASE_PATH+".h2.db")){
			executeScript(connection,"database/create-database.sql");
			executeScript(connection,"database/carga-tabelas_basicas.sql");
			//executeScript(connection,"database/carga-pallete.sql");
			connection.close();
		}
	}
	
	private static void executeScript(Connection connection,String fileReaderPath){
		try{
			RunScript.execute(connection,new FileReader(fileReaderPath)); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}
