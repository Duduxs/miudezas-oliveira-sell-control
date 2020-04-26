package br.com.SellControl.util;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class Mask {

	public static void maskCEP(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			// Only allow numbers in my textField.
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // erasing
				// divide my string, spare the "-" from numbers in this moment.
				if (textField.getText().length() == 6) {
					textField.setText(textField.getText().substring(0, 5));
					textField.positionCaret(textField.getText().length());
				}

			} else { // Typing
						// this if allow only 9 char in CEP
				if (textField.getText().length() == 9)
					event.consume();

				// if my array is in five position, them add "-" and sum "-" with numbers.
				if (textField.getText().length() == 5) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}
			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});
	}

	public static void maskCPF(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			// only numbers
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // erasing
				// divide my string, spare the "-" and "." from numbers in this moment.
				if (textField.getText().length() == 4) {
					textField.setText(textField.getText().substring(0, 3));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 8) {
					textField.setText(textField.getText().substring(0, 7));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 12) {
					textField.setText(textField.getText().substring(0, 11));
					textField.positionCaret(textField.getText().length());
				}

			} else { // typing
						// this if allow only 14 char in CPF
				if (textField.getText().length() == 14)
					event.consume();

				// now add "." and "-" in my numbers in correct position.
				if (textField.getText().length() == 3) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 7) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 11) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}

			}
		});
		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d.-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d.-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void maskPhone(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			// Only numbers
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // erasing
				// divide my string, spare the "-" and "." from numbers in this moment.
				if (textField.getText().length() == 10 && textField.getText().substring(9, 10).equals("-")) {
					textField.setText(textField.getText().substring(0, 9));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 9 && textField.getText().substring(8, 9).equals("-")) {
					textField.setText(textField.getText().substring(0, 8));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 4) {
					textField.setText(textField.getText().substring(0, 3));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 1) {
					textField.setText("");
				}

			} else { // typing
						// this if allow only 15 char in phone (Including the " " )
				if (textField.getText().length() == 15)
					event.consume();
				// now add "-" in my numbers in correct position.
				if (textField.getText().length() == 0) {
					textField.setText("(" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 3) {
					// Set a space " " for better format
					textField.setText(textField.getText() + ") " + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 9) {
					textField.setText(textField.getText() + "-" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 10 && textField.getText().substring(9, 10) != "-") {
					textField.setText(textField.getText() + "-" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 14 && textField.getText().substring(9, 10).equals("-")) {
					textField.setText(textField.getText().substring(0, 9) + textField.getText().substring(10, 11) + "-"
							+ textField.getText().substring(11, 14) + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}

			}

		});


	}
}
