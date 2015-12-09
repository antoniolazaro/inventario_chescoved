package br.com.vortice.chescoved.inventario.main;
	
import org.apache.commons.lang3.StringUtils;

import br.com.vortice.chescoved.inventario.business.UsuarioBusiness;
import br.com.vortice.chescoved.inventario.model.UsuarioModel;
import br.com.vortice.chescoved.inventario.sqlloader.CreateDatabaseStructure;
import br.com.vortice.chescoved.inventario.view.ShowAlertUtil;
import br.com.vortice.chescoved.inventario.view.menu.MenuBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class InventarioMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			CreateDatabaseStructure.initDatabase();
			
			GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(50, 50, 50, 50));

	        Text scenetitle = new Text("Bem vindo ao sistema de Estoque da Chescoved");
	        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        grid.add(scenetitle, 0, 0, 2, 1);

	        Label userName = new Label("Usuário:");
	        grid.add(userName, 0, 1);

	        TextField userTextField = new TextField();
	        grid.add(userTextField, 1, 1);

	        Label pw = new Label("Senha:");
	        grid.add(pw, 0, 2);

	        PasswordField pwBox = new PasswordField();
	        grid.add(pwBox, 1, 2);

	        Button btn = new Button("Login");
	        HBox hbBtn = new HBox(10);
	        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
	        hbBtn.getChildren().add(btn);
	        grid.add(hbBtn, 1, 4);

	        final Text actiontarget = new Text();
	        grid.add(actiontarget, 1, 6);

	        Scene scene = new Scene(grid, 600, 350);
	        primaryStage.setScene(scene);
	        primaryStage.show();

	        btn.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent e) {
	                actiontarget.setFill(Color.FIREBRICK);
	               
	                try{
	            		
	            		if(StringUtils.isEmpty(userTextField.getText())){
	            			ShowAlertUtil.exibirMensagemErro("Campo usuário é obrigatório.");
	            			return;
	            		}
	            		if(StringUtils.isEmpty(pwBox.getText())){
	            			ShowAlertUtil.exibirMensagemErro("Campo senha é obrigatório.");
	            			return;
	            		}
	            		
	            		UsuarioModel usuario = new UsuarioModel();
	 	                
	 	                usuario.setLogin(userTextField.getText());
	 	                usuario.setSenha(pwBox.getText());
	 	                actiontarget.setText("Login/Senha inválidos");
	 	                
	 	                new UsuarioBusiness().login(usuario);
	 	                
	 	                BorderPane root = new BorderPane();
		 	  		    Scene scene = new Scene(root, 1024, 2048, Color.BLACK);
		 	  		    MenuBuilder menuBuilder = new  MenuBuilder(primaryStage, root);
		 	  			menuBuilder.buildMenu();
		 	  			primaryStage.setScene(scene);
		 	  			primaryStage.show();
	            	}catch(Exception ex){
	            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
	                }
	            }
	        });

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
