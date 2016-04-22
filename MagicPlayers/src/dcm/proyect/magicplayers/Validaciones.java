package dcm.proyect.magicplayers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class Validaciones {
	// Expresion regular comprueba el Email.
	private static final String PATRON_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PATRON_CP = "^([1-9]{2}|[0-9][1-9]|[1-9][0-9])[0-9]{3}$";
	private static final String PATRON_DCI = "^[0-9]{10}$";

	public static boolean validarEmail(String email) {
		Pattern pattern = Pattern.compile(PATRON_EMAIL);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public static boolean validarCP(String cp) {
		Pattern pattern = Pattern.compile(PATRON_CP);
		Matcher matcher = pattern.matcher(cp);
		return matcher.matches();
	}
	
	public static boolean validarDCI(String dci) {
		Pattern pattern = Pattern.compile(PATRON_DCI);
		Matcher matcher = pattern.matcher(dci);
		return matcher.matches();
	}
	

}
