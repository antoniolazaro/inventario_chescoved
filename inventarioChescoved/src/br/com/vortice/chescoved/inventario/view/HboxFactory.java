package br.com.vortice.chescoved.inventario.view;

import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class HboxFactory {
	
	private HboxFactory() {
	}
	
	public static HBox getHBoxPadrao(Label label,Control componente){
		HBox hb = new HBox();
		hb.setPadding(new Insets(15, 12, 15, 12));
		hb.setSpacing(10);
		hb.getChildren().addAll(label,componente);
		return hb;
	}

}
