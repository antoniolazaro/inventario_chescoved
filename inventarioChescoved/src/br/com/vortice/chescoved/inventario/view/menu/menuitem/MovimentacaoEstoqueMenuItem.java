package br.com.vortice.chescoved.inventario.view.menu.menuitem;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import br.com.vortice.chescoved.inventario.business.MovimentacaoEstoqueBusiness;
import br.com.vortice.chescoved.inventario.business.ProdutoBusiness;
import br.com.vortice.chescoved.inventario.model.MovimentacaoEstoqueModel;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;
import br.com.vortice.chescoved.inventario.model.TipoMovimentacaoEstoqueModel;
import br.com.vortice.chescoved.inventario.util.DateUtil;
import br.com.vortice.chescoved.inventario.view.AutoCompleteComboBoxListener;
import br.com.vortice.chescoved.inventario.view.HboxFactory;
import br.com.vortice.chescoved.inventario.view.LabelFactory;
import br.com.vortice.chescoved.inventario.view.ShowAlertUtil;
import br.com.vortice.chescoved.inventario.view.custom.DateMaskInputTextListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MovimentacaoEstoqueMenuItem {
	
	private MovimentacaoEstoqueBusiness movimentacaoEstoqueBusiness;
	private final ProdutoBusiness produtoBusiness;
	private final Label tipoMovimentacaoLabel = LabelFactory.getLabelPadrao("Tipo de movimentação");
	private final Label dataMovimentacaoLabel = LabelFactory.getLabelPadrao("Data de movimentação");
	private final Label produtoLabel = LabelFactory.getLabelPadrao("Produto");
	private final Label quantidadeLabel = LabelFactory.getLabelPadrao("Quantidade");
	private final Label notaFiscalLabel = LabelFactory.getLabelPadrao("Nota Fiscal");
	private final Label dataRecebimentoLabel = LabelFactory.getLabelPadrao("Data de recebimento");
	private final VBox vb = new VBox();
	
	public MovimentacaoEstoqueMenuItem() {
		movimentacaoEstoqueBusiness = new MovimentacaoEstoqueBusiness();
		produtoBusiness = new ProdutoBusiness();
	}
	
	private VBox createRemoverPalleteSection() throws Exception{
		
		ToggleGroup group = new ToggleGroup();
		
		RadioButton entradaRadioButton = new RadioButton();
		entradaRadioButton.setText("Entrada");
		entradaRadioButton.setSelected(true);
		entradaRadioButton.setToggleGroup(group);

		RadioButton saidaRadioButton = new RadioButton();
		saidaRadioButton.setText("Saida");
		saidaRadioButton.setToggleGroup(group);
		
		HBox hb = HboxFactory.getHBoxPadrao(tipoMovimentacaoLabel, entradaRadioButton);
		hb.getChildren().add(saidaRadioButton);
		vb.getChildren().add(hb);
		
		final TextField dataMovimentacao = new TextField();
        dataMovimentacao.setPromptText("Data de movimentação");
        dataMovimentacao.setMaxWidth(100);
        dataMovimentacao.lengthProperty().addListener(new DateMaskInputTextListener(dataMovimentacao));
        hb = HboxFactory.getHBoxPadrao(dataMovimentacaoLabel, dataMovimentacao);
        vb.getChildren().add(hb);
        
        ObservableList<ProdutoModel> options = FXCollections.observableArrayList(produtoBusiness.selectAll());
        ComboBox<ProdutoModel> comboBox = new ComboBox<ProdutoModel>();      
        comboBox.setItems(options);
        new AutoCompleteComboBoxListener<ProdutoModel>(comboBox);
        hb = HboxFactory.getHBoxPadrao(produtoLabel, comboBox);
        vb.getChildren().add(hb);
        
        final TextField quantidade = new TextField();
        quantidade.setPromptText("Quantidade");
        quantidade.setMaxWidth(100);
        hb = HboxFactory.getHBoxPadrao(quantidadeLabel, quantidade);
        vb.getChildren().add(hb);
        
        final TextField notaFiscal = new TextField();
        notaFiscal.setPromptText("Nota Fiscal");
        notaFiscal.setMaxWidth(100);
        hb = HboxFactory.getHBoxPadrao(notaFiscalLabel, notaFiscal);
        vb.getChildren().add(hb);
        
        final TextField dataRecebimento = new TextField();
        dataRecebimento.setPromptText("Data Recebimento");
        dataRecebimento.setMaxWidth(100);
        dataRecebimento.lengthProperty().addListener(new DateMaskInputTextListener(dataRecebimento));
        hb = HboxFactory.getHBoxPadrao(dataRecebimentoLabel, dataRecebimento);
        vb.getChildren().add(hb);
        
        
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
            			movimentacaoEstoque.setDataMovimentacao(DateUtil.parse(dataMovimentacao.getText()));	
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
            			movimentacaoEstoque.setDataRecebimento(DateUtil.parse(dataRecebimento.getText()));	
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
        
        
        vb.getChildren().add(addButton);
        vb.setSpacing(8);
        return vb;
	}
	

	public void buildMenuItem(BorderPane root) {
	    final Label label = new Label("Estoque Principal -> Movimentação de Estoque");
        label.setFont(new Font("Arial", 20));

        try{
        	VBox removerPalleteSection = createRemoverPalleteSection();
    		final VBox vbox = new VBox();
            vbox.getChildren().addAll(label,removerPalleteSection);
            
            root.setCenter(vbox);
    	}catch(Exception ex){
    		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
        }
        
        
        

	}
}
