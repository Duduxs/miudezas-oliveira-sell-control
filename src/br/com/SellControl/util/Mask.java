package br.com.SellControl.util;

import javafx.application.Platform;
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

	// PHONE
	private static void positionCaret(TextField textField) {
		Platform.runLater(() -> {
			if (textField.getText().length() != 0) {
				textField.positionCaret(textField.getText().length());
			}
		});
	}

	public static void maxField(TextField textField, Integer length) {
		textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue == null || newValue.length() > length) {
				textField.setText(oldValue);
			}
		});
	}

	public static void maskPhone(TextField textField) {
		Mask.maxField(textField, 14);
		textField.lengthProperty().addListener((observableValue, number, number2) -> {
			try {
				String value = textField.getText();
				value = value.replaceAll("[^0-9]", "");
				int tam = value.length();
				value = value.replaceFirst("(\\d{2})(\\d)", "($1)$2");
				value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
				if (tam > 10) {
					value = value.replaceAll("-", "");
					value = value.replaceFirst("(\\d{5})(\\d)", "$1-$2");
				}
				textField.setText(value);
				Mask.positionCaret(textField);

			} catch (Exception ex) {
			}
		});
	}

	// PHONE

	public static void maskCNPJ(TextField textField) {
		// Only numbers
		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}
			// divide my string, spare the spaces
			if (event.getCharacter().trim().length() == 0) { // erasing

				if (textField.getText().length() == 3) {
					textField.setText(textField.getText().substring(0, 2));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 7) {
					textField.setText(textField.getText().substring(0, 6));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 11) {
					textField.setText(textField.getText().substring(0, 10));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 16) {
					textField.setText(textField.getText().substring(0, 15));
					textField.positionCaret(textField.getText().length());
				}

			} else { // typing
						// this if allow only 18 char in CNPJ (Including the " ")
				if (textField.getText().length() == 18)
					event.consume();
				// now add "." and "/" in my numbers in correct position.
				if (textField.getText().length() == 2) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 6) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 10) {
					textField.setText(textField.getText() + "/");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 15) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d./-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d./-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void DateMask(TextField textField) {
		// Only numbers
		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}
			// divide my string, spare the spaces
			if (event.getCharacter().trim().length() == 0) { // Erasing

				if (textField.getText().length() == 5) {
					textField.setText(textField.getText().substring(0, 4));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 8) {
					textField.setText(textField.getText().substring(0, 7));
					textField.positionCaret(textField.getText().length());
				}

			} else { // typing
						// My date it has to be only ten numbers
				if (textField.getText().length() == 10)
					event.consume();
				// now add "/" in correct position
				if (textField.getText().length() == 4) {
					textField.setText(textField.getText() + "/");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 7) {
					textField.setText(textField.getText() + "/");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d/*")) {
				textField.setText(textField.getText().replaceAll("[^\\d/]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

}
