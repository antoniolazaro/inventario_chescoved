package br.com.vortice.chescoved.inventario.sqlloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Properties;

import org.h2.store.fs.FileUtils;
import org.h2.tools.RunScript;

import br.com.vortice.chescoved.inventario.dao.DAOAb;

public class CreateDatabaseStructure {
	
	public static String DATABASE_PATH = null;
	static{
		try {
			DATABASE_PATH = getPath()+File.separator+"inventario_chescoved";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void initDatabase() throws Exception{
		if(!FileUtils.exists(DATABASE_PATH+".h2.db")){
			Connection connection = new DAOAb().getDBConnection();
			executeScript(connection,"database/create-database.sql");
			executeScript(connection,"database/create-database-arc.sql");
			executeScript(connection,"database/create-database-material_interno.sql");
			executeScript(connection,"database/carga-tabelas-basicas.sql");
			executeScript(connection,"database/carga-tabelas_produtos_estoque_principal.sql");
			executeScript(connection,"database/carga-tabelas_movimentacao_entrada-estoque-principal.sql");
			executeScript(connection,"database/carga-tabelas_produtos_estoque_arc.sql");
			executeScript(connection,"database/carga-tabelas_movimentacao_entrada-estoque-arc.sql");
			executeScript(connection,"database/carga-tabelas_produtos_estoque_material_interno.sql");
			
			connection.close();
		}
	}
	
	private static String getPath() throws Exception{
		Properties mainProperties = new Properties();

		String path = "./path.properties";
	    FileInputStream file = new FileInputStream(path);
	    mainProperties.load(file);
	    file.close();
	    return mainProperties.getProperty("path");

	}
	
	private static void executeScript(Connection connection,String fileReaderPath){
		try{
			RunScript.execute(connection,new FileReader(fileReaderPath)); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}
