package br.com.vortice.chescoved.inventario.view.menu.menuitem;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.vortice.chescoved.inventario.business.InventarioBusiness;
import br.com.vortice.chescoved.inventario.model.InventarioModel;
import br.com.vortice.chescoved.inventario.model.InventarioProdutoModel;
import br.com.vortice.chescoved.inventario.view.ShowAlertUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class InventarioMenuItem {
	
	private InventarioBusiness inventarioBusiness;
	private ObservableList<InventarioProdutoModel> conteudoTabela;
	private InventarioModel inventario;
	private TableView<InventarioProdutoModel> table;
	private HBox confirmacaoInventario;
	private List<InventarioProdutoModel> listaProdutos;
	
	public InventarioMenuItem() {
		inventarioBusiness = new InventarioBusiness();
		inventario = new InventarioModel();
	}
	
	private TableView<InventarioProdutoModel> createTableView(){
		TableView<InventarioProdutoModel> table = new TableView<InventarioProdutoModel>();

        table.setEditable(true);
        
        TableColumn<InventarioProdutoModel, Date> codigoCol = new TableColumn<InventarioProdutoModel, Date>("Data Inventário");
        codigoCol.setMaxWidth(100);
        codigoCol.setCellValueFactory(new Callback<CellDataFeatures<InventarioProdutoModel, Date>, ObservableValue<Date>>() {
            public ObservableValue<Date> call(CellDataFeatures<InventarioProdutoModel, Date> p) {
                return new ReadOnlyObjectWrapper(new SimpleDateFormat("dd/MM/yyyy").format(p.getValue().getInventario().getDataInventario()));
            }
         });
        
        
        TableColumn<InventarioProdutoModel, String> nomeCol = new TableColumn<InventarioProdutoModel, String>("Produto");
        nomeCol.setMaxWidth(300);
        nomeCol.setCellValueFactory(new Callback<CellDataFeatures<InventarioProdutoModel, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<InventarioProdutoModel, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getNome());
            }
         });
        
        TableColumn<InventarioProdutoModel, Long> codigoProdutoCol = new TableColumn<InventarioProdutoModel, Long>("Código");
        codigoProdutoCol.setMaxWidth(300);
        codigoProdutoCol.setCellValueFactory(new Callback<CellDataFeatures<InventarioProdutoModel, Long>, ObservableValue<Long>>() {
            public ObservableValue<Long> call(CellDataFeatures<InventarioProdutoModel, Long> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getCodigo());
            }
         });

        TableColumn<InventarioProdutoModel, String> quantidadeContada = new TableColumn<InventarioProdutoModel, String>("Quantidade Contada");
        quantidadeContada.setMaxWidth(300);
        quantidadeContada.setCellValueFactory(new PropertyValueFactory<InventarioProdutoModel, String>("quantidade"));
        quantidadeContada.setEditable(true);
        quantidadeContada.setCellFactory(TextFieldTableCell.<InventarioProdutoModel>forTableColumn());
        quantidadeContada.setOnEditCommit(
            (CellEditEvent<InventarioProdutoModel, String> t) -> {
                ((InventarioProdutoModel) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setQuantidade(Integer.valueOf(t.getNewValue()));
        });



        TableColumn<InventarioProdutoModel, Integer> quantidadeEstoque = new TableColumn<InventarioProdutoModel, Integer>("Quantidade Estoque");
        quantidadeEstoque.setMaxWidth(150);
        quantidadeEstoque.setCellValueFactory(new PropertyValueFactory<InventarioProdutoModel, Integer>("quantidadeEstoque"));
        
        TableColumn<InventarioProdutoModel, Integer> quantidadeDivergencia = new TableColumn<InventarioProdutoModel, Integer>("Divergência");
        quantidadeDivergencia.setMaxWidth(150);
        quantidadeDivergencia.setCellValueFactory(new PropertyValueFactory<InventarioProdutoModel, Integer>("quantidadeDivergencia"));
        
        TableColumn<InventarioProdutoModel, Integer> quantidadeFinal = new TableColumn<InventarioProdutoModel, Integer>("Quantidade Final");
        quantidadeFinal.setMaxWidth(150);
        quantidadeFinal.setCellValueFactory(new PropertyValueFactory<InventarioProdutoModel, Integer>("quantidadeFinal"));
        
        TableColumn<InventarioProdutoModel, BigDecimal> valorCusto = new TableColumn<InventarioProdutoModel, BigDecimal>("Valor Custo");
        valorCusto.setMaxWidth(150);
        valorCusto.setCellValueFactory(new Callback<CellDataFeatures<InventarioProdutoModel, BigDecimal>, ObservableValue<BigDecimal>>() {
            public ObservableValue<BigDecimal> call(CellDataFeatures<InventarioProdutoModel, BigDecimal> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getValorCusto());
            }
         });

        TableColumn<InventarioProdutoModel, BigDecimal> totalCusto = new TableColumn<InventarioProdutoModel, BigDecimal>("Total Custo");
        totalCusto.setMaxWidth(150);
        totalCusto.setCellValueFactory(new PropertyValueFactory<InventarioProdutoModel, BigDecimal>("totalCusto"));
        
        TableColumn<InventarioProdutoModel, BigDecimal> valorVenda = new TableColumn<InventarioProdutoModel, BigDecimal>("Valor Venda");
        valorVenda.setMaxWidth(150);
        valorVenda.setCellValueFactory(new Callback<CellDataFeatures<InventarioProdutoModel, BigDecimal>, ObservableValue<BigDecimal>>() {
            public ObservableValue<BigDecimal> call(CellDataFeatures<InventarioProdutoModel, BigDecimal> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getValorVenda());
            }
         });

        TableColumn<InventarioProdutoModel, BigDecimal> totalVenda = new TableColumn<InventarioProdutoModel, BigDecimal>("Total Venda");
        totalVenda.setMaxWidth(150);
        totalVenda.setCellValueFactory(new PropertyValueFactory<InventarioProdutoModel, BigDecimal>("totalVenda"));
        
        conteudoTabela = FXCollections.observableArrayList(new ArrayList<InventarioProdutoModel>());
       
        table.setItems(conteudoTabela);
        table.getColumns().addAll(codigoCol,nomeCol,codigoProdutoCol,quantidadeContada,quantidadeEstoque,quantidadeDivergencia,quantidadeFinal,valorCusto,totalCusto,valorVenda,totalVenda);
        return table;
	}

	
	private HBox createFiltroProduto(){
		final HBox hb = new HBox();
        
		final TextField dataInventario = new TextField();
		dataInventario.setPromptText("Data do Inventário");
		dataInventario.setMaxWidth(100);
       
        final Button addButton = new Button("Criar Inventário");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            		
            		if(StringUtils.isNotEmpty(dataInventario.getText())){
            			inventario.setDataInventario(simpleDateFormat.parse(dataInventario.getText()));	
            		}else{
            			ShowAlertUtil.exibirMensagemErro("Campo data do inventário é obrigatório.");
            		}
            	    
            		listaProdutos = inventarioBusiness.findAllProdutos(inventario);
        	        if(listaProdutos == null){
        	        	listaProdutos = new ArrayList<InventarioProdutoModel>();
        	        }
        	        conteudoTabela = FXCollections.observableArrayList(listaProdutos);
        	        table.setItems(conteudoTabela);
            		table.setVisible(true);
            		confirmacaoInventario.setVisible(true);
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        
        hb.getChildren().addAll(dataInventario,addButton);
        hb.setSpacing(5);
        return hb;
	}
	
	private HBox confirmeInventario(){
		final HBox hb = new HBox();
        
        final Button addButton = new Button("Confirmar Inventário");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		inventario.setListaProdutos(listaProdutos);
            		inventarioBusiness.insert(inventario);
            		ShowAlertUtil.exibirMensagemInfo("Inventário executado com sucesso. O estoque passou a ter a quantidade auditada.");
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        
        hb.getChildren().addAll(addButton);
        hb.setSpacing(5);
        return hb;
	}
	
	public void buildMenuItem(Stage primaryStage){
		Scene scene = new Scene(new Group());
	    final Label label = new Label("Inventário");
        label.setFont(new Font("Arial", 20));

        HBox pesquisar = createFiltroProduto();
        table = createTableView();
        table.setVisible(false);
        
        confirmacaoInventario = confirmeInventario();
        confirmacaoInventario.setVisible(false);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label,pesquisar,table,confirmacaoInventario);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        primaryStage.setScene(scene);
        primaryStage.show();

	}
}
