package br.com.vortice.chescoved.inventario.sqlloader;

import java.io.FileReader;
import java.sql.Connection;

import org.h2.tools.RunScript;

import br.com.vortice.chescoved.inventario.dao.DAOAb;

public class CreateDatabaseStructure {
	
	public static final String DATABASE_PATH = "~/inventario_chescoved";
	
	public static void initDatabase() throws Exception{
		Connection connection = new DAOAb().getDBConnection();
//		if(!FileUtils.exists(DATABASE_PATH+".h2.db")){
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
//		}
	}
	
	private static void executeScript(Connection connection,String fileReaderPath){
		try{
			RunScript.execute(connection,new FileReader(fileReaderPath)); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}
