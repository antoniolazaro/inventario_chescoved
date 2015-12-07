package br.com.vortice.chescoved.inventario.view.menu.menuitem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.vortice.chescoved.inventario.business.PepsBusiness;
import br.com.vortice.chescoved.inventario.business.ProdutoBusiness;
import br.com.vortice.chescoved.inventario.model.PepsModel;
import br.com.vortice.chescoved.inventario.model.ProdutoModel;
import br.com.vortice.chescoved.inventario.view.AutoCompleteComboBoxListener;
import br.com.vortice.chescoved.inventario.view.HboxFactory;
import br.com.vortice.chescoved.inventario.view.LabelFactory;
import br.com.vortice.chescoved.inventario.view.ShowAlertUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class PepsMenuItem {
	
	private ObservableList<PepsModel> conteudoTabela;
	private TableView<PepsModel> table;
	private PepsBusiness pepsBusiness;
	private ProdutoBusiness produtoBusiness;
	private final Label produtoLabel = LabelFactory.getLabelPadrao("Produto");
	
	
	public PepsMenuItem() {
		pepsBusiness = new PepsBusiness();
		produtoBusiness = new ProdutoBusiness();
	}

	private TableView<PepsModel> createTableView(){
		TableView<PepsModel> table = new TableView<PepsModel>();

        table.setEditable(false);
        
        
        TableColumn<PepsModel, String> nomeCol = new TableColumn<PepsModel, String>("Produto");
        nomeCol.setPrefWidth(300);
        nomeCol.setCellValueFactory(new Callback<CellDataFeatures<PepsModel, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<PepsModel, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getNome());
            }
         });
        
        TableColumn<PepsModel, Long> codigoProdutoCol = new TableColumn<PepsModel, Long>("Código");
        codigoProdutoCol.setPrefWidth(150);
        codigoProdutoCol.setCellValueFactory(new Callback<CellDataFeatures<PepsModel, Long>, ObservableValue<Long>>() {
            public ObservableValue<Long> call(CellDataFeatures<PepsModel, Long> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getCodigo());
            }
         });

        TableColumn<PepsModel, Date> quantidadeEstoque = new TableColumn<PepsModel, Date>("Data Movimentação");
        quantidadeEstoque.setPrefWidth(150);
        quantidadeEstoque.setCellValueFactory(new Callback<CellDataFeatures<PepsModel, Date>, ObservableValue<Date>>() {
            public ObservableValue<Date> call(CellDataFeatures<PepsModel, Date> p) {
            	if(p.getValue().getDataMovimentacao() != null){
            		return new ReadOnlyObjectWrapper(new SimpleDateFormat("dd/MM/yyyy").format(p.getValue().getDataMovimentacao()));
            	}
            	return new ReadOnlyObjectWrapper("");
            }
         });
        
        TableColumn<PepsModel, Date> quantidadeDivergencia = new TableColumn<PepsModel, Date>("Data Recebimento");
        quantidadeDivergencia.setPrefWidth(150);
        quantidadeDivergencia.setCellValueFactory(new Callback<CellDataFeatures<PepsModel, Date>, ObservableValue<Date>>() {
            public ObservableValue<Date> call(CellDataFeatures<PepsModel, Date> p) {
            	if(p.getValue().getDataRecebimento() != null){
            		return new ReadOnlyObjectWrapper(new SimpleDateFormat("dd/MM/yyyy").format(p.getValue().getDataRecebimento()));
            	}
            	return new ReadOnlyObjectWrapper("");
            }
         });
        
        TableColumn<PepsModel, String> quantidadeFinal = new TableColumn<PepsModel, String>("Nota Fiscal");
        quantidadeFinal.setPrefWidth(150);
        quantidadeFinal.setCellValueFactory(new PropertyValueFactory<PepsModel, String>("notaFiscal"));
        
        TableColumn<PepsModel, String> quantidadeNota = new TableColumn<PepsModel, String>("Quantidade Nota");
        quantidadeNota.setPrefWidth(150);
        quantidadeNota.setCellValueFactory(new PropertyValueFactory<PepsModel, String>("quantidade"));
        
        TableColumn<PepsModel, String> quantidadeRestante = new TableColumn<PepsModel, String>("Quantidade Restante");
        quantidadeRestante.setPrefWidth(150);
        quantidadeRestante.setCellValueFactory(new PropertyValueFactory<PepsModel, String>("quantidadeRestante"));
        
        conteudoTabela = FXCollections.observableArrayList(new ArrayList<PepsModel>());
        table.setItems(conteudoTabela);
        table.getColumns().addAll(nomeCol,codigoProdutoCol,quantidadeEstoque,quantidadeDivergencia,quantidadeFinal,quantidadeNota,quantidadeRestante);
        return table;
	}

	
	private VBox createFiltroProduto() throws Exception{
		final VBox vb = new VBox();
        
		ObservableList<ProdutoModel> options = FXCollections.observableArrayList(produtoBusiness.selectAll());
	    ComboBox<ProdutoModel> comboBox = new ComboBox<ProdutoModel>();
	    comboBox.setItems(options);
	 
	    new AutoCompleteComboBoxListener<ProdutoModel>(comboBox);

	    HBox hb = HboxFactory.getHBoxPadrao(produtoLabel, comboBox);
		vb.getChildren().add(hb);
	   
        final Button addButton = new Button("PEPS");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		
            		if(comboBox.getValue() == null){
            			ShowAlertUtil.exibirMensagemErro("Campo produto é obrigatório.");
            			return;
            		}
            	    
            		List<PepsModel> listaProdutos = pepsBusiness.projetarPeps(comboBox.getValue());
        	        if(listaProdutos == null){
        	        	listaProdutos = new ArrayList<PepsModel>();
        	        }
        	        conteudoTabela.clear();
        	        conteudoTabela = FXCollections.observableArrayList(listaProdutos);
        	        table.setItems(conteudoTabela);
        	        table.setVisible(true);
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        
        vb.getChildren().addAll(addButton);
        return vb;
	}
	
	
	public void buildMenuItem(BorderPane root){
	    final Label label = new Label("PEPS");
        label.setFont(new Font("Arial", 20));

        VBox pesquisar = null;
		try {
			pesquisar = createFiltroProduto();
		} catch (Exception e) {
			ShowAlertUtil.exibirMensagemErro("Erro: "+e.getMessage());
		}
        table = createTableView();
        table.setVisible(false);
        
        final VBox vbox = new VBox();
        vbox.getChildren().addAll(label,pesquisar,table);
 
        root.setCenter(vbox);

	}
}
