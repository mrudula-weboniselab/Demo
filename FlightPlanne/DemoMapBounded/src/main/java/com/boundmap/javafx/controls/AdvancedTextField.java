package com.boundmap.javafx.controls;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;



public class AdvancedTextField extends TextField {
    public static final String DOUBLE_REGEX = "^-?[0-9]*\\.?[0-9]*";
    public static final String UINT_REGEX = "[0-9]*";

    private StringProperty restrict = new SimpleStringProperty();

    private IntegerProperty maxLength = new SimpleIntegerProperty(-1);

    public AdvancedTextField(String string) {
        super(string);
        textProperty().addListener(new AdvancedTextFieldChangeListener());
    }

    public int getMaxLength() {
        return maxLength.get();
    }

    /**
     * Sets the max length of the text field.
     *
     * @param maxLength The max length.
     */
    public void setMaxLength(int maxLength) {
        this.maxLength.set(maxLength);
    }

    /**
     * Sets a regular expression character class which restricts the user input.<br/>
     * E.g. [0-9]* only allows numeric values.
     *
     * @param restrict The regular expression.
     */
    public void setRestrict(String restrict) {
        this.restrict.set(restrict);
    }

    public String getRestrict() {
        return restrict.get();
    }

    public StringProperty restrictProperty() {
        return restrict;
    }

    public IntegerProperty maxLengthProperty() {
        return maxLength;
    }

    public AdvancedTextField() {
        super();
        textProperty().addListener(new AdvancedTextFieldChangeListener());
    }

    class AdvancedTextFieldChangeListener implements ChangeListener<String> {
        private boolean ignore;

        @Override
        public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
            if (ignore)
                return;
            if (s1.equals("")) {
                ignore = true;
                setText(s1);
                ignore = false;
                return;
            }
            if (maxLength.get() > -1 && s1.length() > maxLength.get()) {
                ignore = true;
                setText(s1.substring(0, maxLength.get()));
                ignore = false;
            }

            if (restrict.get() != null && !restrict.get().equals("") && !s1.matches(restrict.get())) {
                ignore = true;
                setText(s);
                ignore = false;
            }
        }
    }
}
