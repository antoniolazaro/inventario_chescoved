package br.com.vortice.chescoved.inventario.view.menu.menuitem;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import br.com.vortice.chescoved.inventario.business.MovimentacaoEstoqueBusiness;
import br.com.vortice.chescoved.inventario.business.ProdutoBusiness;
import br.com.vortice.chescoved.inventario.model.MovimentacaoEstoqueModel;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;
import br.com.vortice.chescoved.inventario.model.TipoMovimentacaoEstoqueModel;
import br.com.vortice.chescoved.inventario.view.ShowAlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MovimentacaoEstoqueMenuItem {
	
	private MovimentacaoEstoqueBusiness movimentacaoEstoqueBusiness;
	private ProdutoBusiness produtoBusiness;
	
	public MovimentacaoEstoqueMenuItem() {
		movimentacaoEstoqueBusiness = new MovimentacaoEstoqueBusiness();
		produtoBusiness = new ProdutoBusiness();
	}
	
	private VBox createRemoverPalleteSection() throws Exception{
		
        
		RadioButton entradaRadioButton = new RadioButton();
		entradaRadioButton.setText("Entrada");
		entradaRadioButton.setSelected(true);
		RadioButton saidaRadioButton = new RadioButton();
		saidaRadioButton.setText("Saida");
		
		HBox hb = new HBox();
		hb.getChildren().addAll(entradaRadioButton,saidaRadioButton);
		
        final TextField dataMovimentacao = new TextField();
        dataMovimentacao.setPromptText("Data de movimentação");
        dataMovimentacao.setMaxWidth(100);
        
        ObservableList<ProdutoModel> options = FXCollections.observableArrayList(produtoBusiness.selectAll());
        ComboBox<ProdutoModel> comboBox = new ComboBox<ProdutoModel>();
        comboBox.setItems(options);
        
        final TextField quantidade = new TextField();
        quantidade.setPromptText("Quantidade");
        quantidade.setMaxWidth(100);
        
        final TextField notaFiscal = new TextField();
        notaFiscal.setPromptText("Nota Fiscal");
        notaFiscal.setMaxWidth(100);
        
        final TextField dataRecebimento = new TextField();
        dataRecebimento.setPromptText("Data Recebimento");
        dataRecebimento.setMaxWidth(100);
        
        final Button addButton = new Button("Confirmar");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		
            		MovimentacaoEstoqueModel movimentacaoEstoque = new MovimentacaoEstoqueModel();
            		if(entradaRadioButton.isSelected()){
            			movimentacaoEstoque.setTipoMovimentacaoEstoque(new TipoMovimentacaoEstoqueModel(1L));
            		}else if(saidaRadioButton.isSelected()){
            			movimentacaoEstoque.setTipoMovimentacaoEstoque(new TipoMovimentacaoEstoqueModel(2L));
            		}
            		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            		if(StringUtils.isNotEmpty(dataMovimentacao.getText())){
            			movimentacaoEstoque.setDataMovimentacao(simpleDateFormat.parse(dataMovimentacao.getText()));	
            		}else{
            			ShowAlertUtil.exibirMensagemErro("Campo data de movimentação é obrigatório.");
            		}
            		if(StringUtils.isNotEmpty(quantidade.getText())){
            			movimentacaoEstoque.setQuantidade(Integer.valueOf(quantidade.getText()));	
            		}else{
            			ShowAlertUtil.exibirMensagemErro("Campo quantidade é obrigatório.");
            			return;
            		}
            		if(StringUtils.isNotEmpty(notaFiscal.getText())){
            			movimentacaoEstoque.setNotaFiscal(notaFiscal.getText());
            		}
            		if(StringUtils.isNotEmpty(dataRecebimento.getText())){
            			movimentacaoEstoque.setDataRecebimento(simpleDateFormat.parse(dataRecebimento.getText()));	
            		}
            		if(comboBox.getValue() != null){
            			movimentacaoEstoque.setProduto(comboBox.getValue());
            		}else{
            			ShowAlertUtil.exibirMensagemErro("Campo produto é obrigatório.");
            			return;
            		}
            		movimentacaoEstoqueBusiness.insert(movimentacaoEstoque);
            		dataMovimentacao.clear();
            		quantidade.clear();
            		notaFiscal.clear();
            		dataRecebimento.clear();
            		comboBox.setValue(null);
            		
	                ShowAlertUtil.exibirMensagemInfo("Ação realizada com sucesso!");
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        
        final VBox vb = new VBox();
        vb.getChildren().addAll(hb,dataMovimentacao,comboBox,quantidade,notaFiscal,dataRecebimento,addButton);
        vb.setSpacing(8);
        return vb;
	}
	

	public void buildMenuItem(Stage primaryStage) {
		Scene scene = new Scene(new Group());
	    final Label label = new Label("Estoque Principal -> Movimentação de Estoque");
        label.setFont(new Font("Arial", 20));

        try{
        	VBox removerPalleteSection = createRemoverPalleteSection();
    		final VBox vbox = new VBox();
            vbox.setSpacing(8);
            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(label,removerPalleteSection);
     
            ((Group) scene.getRoot()).getChildren().addAll(vbox);
            
            primaryStage.setScene(scene);
            primaryStage.show();
    	}catch(Exception ex){
    		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
        }
        
        
        

	}
}
