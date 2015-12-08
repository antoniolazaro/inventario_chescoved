package br.com.vortice.chescoved.inventario.view.custom;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class DateMaskInputTextListener extends ComponentCustomAb implements ChangeListener<Number>{
	
	private TextField textField;
	
	public DateMaskInputTextListener(TextField textField) {
		maxField(textField, 10);
		this.textField = textField;
	}
	
	@Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		if (newValue.intValue() < 11) {
            String value = textField.getText();
            value = value.replaceAll("[^0-9]", "");
            value = value.replaceFirst("(\\d{2})(\\d)", "$1/$2");
            value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "$1/$2/$3");
            textField.setText(value);
            positionCaret(textField);
        }
    }

}
