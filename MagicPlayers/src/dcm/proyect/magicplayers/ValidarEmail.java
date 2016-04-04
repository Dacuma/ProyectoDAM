package dcm.proyect.magicplayers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ValidarEmail {
	// Expresion regular comprueba el Email.
	private static final String PATRON_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static boolean validarEmail(String email) {
		Pattern pattern = Pattern.compile(PATRON_EMAIL);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
