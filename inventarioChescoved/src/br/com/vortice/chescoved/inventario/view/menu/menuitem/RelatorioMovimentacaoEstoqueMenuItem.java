package br.com.vortice.chescoved.inventario.view.menu.menuitem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.vortice.chescoved.inventario.business.MovimentacaoEstoqueBusiness;
import br.com.vortice.chescoved.inventario.business.ProdutoBusiness;
import br.com.vortice.chescoved.inventario.model.MovimentacaoEstoqueModel;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class RelatorioMovimentacaoEstoqueMenuItem {
	
	private ObservableList<MovimentacaoEstoqueModel> conteudoTabela;
	private TableView<MovimentacaoEstoqueModel> table;
	private MovimentacaoEstoqueModel filtro;
	private MovimentacaoEstoqueBusiness movimentacaoEstoqueBusiness;
	private ProdutoBusiness produtoBusiness;
	private final Label produtoLabel = LabelFactory.getLabelPadrao("Produto");
	private final Label dataInicioLabel = LabelFactory.getLabelPadrao("Data Início");
	private final Label dataFimLabel = LabelFactory.getLabelPadrao("Data Fim");
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	
	public RelatorioMovimentacaoEstoqueMenuItem() {
		filtro = new MovimentacaoEstoqueModel();
		produtoBusiness = new ProdutoBusiness();
		movimentacaoEstoqueBusiness = new MovimentacaoEstoqueBusiness();
	}

	private TableView<MovimentacaoEstoqueModel> createTableView(){
		TableView<MovimentacaoEstoqueModel> table = new TableView<MovimentacaoEstoqueModel>();

        table.setEditable(false);
        
        
        TableColumn<MovimentacaoEstoqueModel, String> nomeCol = new TableColumn<MovimentacaoEstoqueModel, String>("Tipo de Movimentação");
        nomeCol.setPrefWidth(200);
        nomeCol.setCellValueFactory(new Callback<CellDataFeatures<MovimentacaoEstoqueModel, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<MovimentacaoEstoqueModel, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getTipoMovimentacaoEstoque().getNome());
            }
         });
        
        TableColumn<MovimentacaoEstoqueModel, String> produtoCol = new TableColumn<MovimentacaoEstoqueModel, String>("Produto");
        produtoCol.setPrefWidth(150);
        produtoCol.setCellValueFactory(new Callback<CellDataFeatures<MovimentacaoEstoqueModel, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<MovimentacaoEstoqueModel, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getNome());
            }
         });
        
        TableColumn<MovimentacaoEstoqueModel, Long> codigoProdutoCol = new TableColumn<MovimentacaoEstoqueModel, Long>("Código");
        codigoProdutoCol.setPrefWidth(150);
        codigoProdutoCol.setCellValueFactory(new Callback<CellDataFeatures<MovimentacaoEstoqueModel, Long>, ObservableValue<Long>>() {
            public ObservableValue<Long> call(CellDataFeatures<MovimentacaoEstoqueModel, Long> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getCodigo());
            }
         });

        TableColumn<MovimentacaoEstoqueModel, Date> dataMovimentacao = new TableColumn<MovimentacaoEstoqueModel, Date>("Data Movimentação");
        dataMovimentacao.setPrefWidth(150);
        dataMovimentacao.setCellValueFactory(new Callback<CellDataFeatures<MovimentacaoEstoqueModel, Date>, ObservableValue<Date>>() {
            public ObservableValue<Date> call(CellDataFeatures<MovimentacaoEstoqueModel, Date> p) {
            	if(p.getValue().getDataMovimentacao() != null){
            		return new ReadOnlyObjectWrapper(new SimpleDateFormat("dd/MM/yyyy").format(p.getValue().getDataMovimentacao()));
            	}
            	return new ReadOnlyObjectWrapper("");
            }
         });
        
        TableColumn<MovimentacaoEstoqueModel, Date> dataRecebimento = new TableColumn<MovimentacaoEstoqueModel, Date>("Data Recebimento");
        dataRecebimento.setPrefWidth(150);
        dataRecebimento.setCellValueFactory(new Callback<CellDataFeatures<MovimentacaoEstoqueModel, Date>, ObservableValue<Date>>() {
            public ObservableValue<Date> call(CellDataFeatures<MovimentacaoEstoqueModel, Date> p) {
            	if(p.getValue().getDataRecebimento() != null){
            		return new ReadOnlyObjectWrapper(new SimpleDateFormat("dd/MM/yyyy").format(p.getValue().getDataRecebimento()));
            	}
            	return new ReadOnlyObjectWrapper("");
            }
         });
        
        TableColumn<MovimentacaoEstoqueModel, String> notaFiscal = new TableColumn<MovimentacaoEstoqueModel, String>("Nota Fiscal");
        notaFiscal.setPrefWidth(150);
        notaFiscal.setCellValueFactory(new PropertyValueFactory<MovimentacaoEstoqueModel, String>("notaFiscal"));
        
        TableColumn<MovimentacaoEstoqueModel, String> quantidade = new TableColumn<MovimentacaoEstoqueModel, String>("Quantidade");
        quantidade.setPrefWidth(150);
        quantidade.setCellValueFactory(new PropertyValueFactory<MovimentacaoEstoqueModel, String>("quantidade"));
        
        conteudoTabela = FXCollections.observableArrayList(new ArrayList<MovimentacaoEstoqueModel>());
        table.setItems(conteudoTabela);
        table.getColumns().addAll(nomeCol,produtoCol,codigoProdutoCol,dataMovimentacao,dataRecebimento,notaFiscal,quantidade);
        return table;
	}

	
	private VBox createFiltroProduto() throws Exception{
		final VBox vb = new VBox();
        
		final TextField dataInicio = new TextField();
		dataInicio.setPromptText("Data Início");
		dataInicio.setPrefWidth(100);
		HBox hb = HboxFactory.getHBoxPadrao(dataInicioLabel, dataInicio);
		vb.getChildren().add(hb);
		
		final TextField dataFim = new TextField();
		dataFim.setPromptText("Data Fim");
		dataFim.setPrefWidth(100);
		hb = HboxFactory.getHBoxPadrao(dataFimLabel, dataFim);
		vb.getChildren().add(hb);
		
		ObservableList<ProdutoModel> options = FXCollections.observableArrayList(produtoBusiness.selectAll());
	    ComboBox<ProdutoModel> comboBox = new ComboBox<ProdutoModel>();
	    comboBox.setItems(options);
	 
	    new AutoCompleteComboBoxListener<ProdutoModel>(comboBox);

	    hb = HboxFactory.getHBoxPadrao(produtoLabel, comboBox);
		vb.getChildren().add(hb);
	   
        final Button addButton = new Button("PESQUISAR");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		
            		filtro.setProduto(comboBox.getValue());
            		if(StringUtils.isNotEmpty(dataInicio.getText())){
            			filtro.setDataInicio(simpleDateFormat.parse(dataInicio.getText()));	
            		}else{
            			ShowAlertUtil.exibirMensagemErro("Campo data início é obrigatório.");
            			return;
            		}
            		if(StringUtils.isNotEmpty(dataFim.getText())){
            			filtro.setDataFim(simpleDateFormat.parse(dataFim.getText()));	
            		}else{
            			ShowAlertUtil.exibirMensagemErro("Campo data fim é obrigatório.");
            			return;
            		}
            	    
            		List<MovimentacaoEstoqueModel> listaMovimentacoes = movimentacaoEstoqueBusiness.pesquisarMovimentacoes(filtro);
        	        if(listaMovimentacoes == null){
        	        	listaMovimentacoes = new ArrayList<MovimentacaoEstoqueModel>();
        	        }
        	        conteudoTabela.clear();
        	        conteudoTabela = FXCollections.observableArrayList(listaMovimentacoes);
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
	    final Label label = new Label("Relatório de Movimentação de Estoque");
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
