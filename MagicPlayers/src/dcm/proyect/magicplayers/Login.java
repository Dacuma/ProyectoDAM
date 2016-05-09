package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	static String nombreUsuario = "";
	static String contrasenaUsuario = "";
	EditText textUsername;
	EditText textPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}

	@Override
	protected void onResume() {
		// Si el usuario ya ha insertado datos correctos estos se quedan
		// guardados y se
		// autorrellenan los campos hasta que se cierre la aplicacion o se haga
		// un logout.
		super.onResume();
		textUsername = (EditText) findViewById(R.id.etUsername);
		textPassword = (EditText) findViewById(R.id.etPassword);
		if (nombreUsuario.length() > 0 && contrasenaUsuario.length() > 0) {
			textUsername.setText(nombreUsuario);
			textPassword.setText(contrasenaUsuario);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Evito que se pueda volver a atr�s.
	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

	public void startSignUp(View v) {
		if (!isOnline()) {
			String aviso = "No hay conexi�n a internet. Compruebe sus conexiones.";
			Toast.makeText(this, aviso, Toast.LENGTH_LONG).show();
		} else {
			Intent i = new Intent(this, SignUp.class);
			startActivity(i);
		}
	}
	
	// M�todo que comprueba si hay conexi�n a internet
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	public void startMenuPrincipal(View V) throws InterruptedException {
		// Si no hay conexi�n avisa al usuario de que no puede proseguir.
		if (!isOnline()) {
			String aviso = "No hay conexi�n a internet. Compruebe sus conexiones.";
			Toast.makeText(this, aviso, Toast.LENGTH_LONG).show();
		} else {
			// Obtiene el usuario y la contrase�a de la ventana de login
			nombreUsuario = textUsername.getText().toString();
			contrasenaUsuario = textPassword.getText().toString();
			// Hilo que comprueba si el usuario y la contrase�a son correctos
			ThreadLogin tl = new ThreadLogin(nombreUsuario, contrasenaUsuario);
			// Elimina el texto del campo contrase�a, deja el nombre de usuario
			textPassword.setText("");

			// Se ejecuta el hilo ThreadLogin
			tl.start();
			tl.join();
			// isBandera devuelve true si el login es correcto, false si no.
			if (tl.isBandera()) {
				Toast toast = Toast.makeText(this, "Conectado!",
						Toast.LENGTH_SHORT);
				toast.show();
				Intent i = new Intent(this, MenuPrincipal.class);
				startActivity(i);
			} else {
				Toast toast = Toast.makeText(this,
						"Error en nombre de usuario o contrase�a.",
						Toast.LENGTH_SHORT);
				toast.show();
				nombreUsuario = "";
				contrasenaUsuario = "";
			}
		}
	}

	// Hilo que comprueba si el usuario existe y la contrase�a introducida es
	// correcta.
	public class ThreadLogin extends Thread {
		String nombre = "";
		String passwd = "";
		boolean bandera = false;

		public ThreadLogin(String nombre, String passwd) {
			this.nombre = nombre;
			this.passwd = EncriptarPasswd.encriptar(passwd);
		}

		@Override
		public void run() {
			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(
						ConexionesDB.serverDB,
						ConexionesDB.usuarioDB, ConexionesDB.passDB);
			} catch (SQLException se) {

			} catch (ClassNotFoundException e) {

			} catch (Exception e) {

			}

			try {
				Statement stat = conn.createStatement();
				ResultSet rs = stat
						.executeQuery("SELECT contrasenaU from Usuario where nombreU='"
								+ nombre + "';");
				// Si el usuario existe y la contrase�a es correcta bandera =
				// true.
				while (rs.next()) {
					if (passwd.equals(rs.getString("contrasenaU"))) {
						bandera = true;
					}
				}
				rs.close();
			} catch (SQLException e) {

			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getPasswd() {
			return passwd;
		}

		public void setPasswd(String passwd) {
			this.passwd = passwd;
		}

		public boolean isBandera() {
			return bandera;
		}

		public void setBandera(boolean bandera) {
			this.bandera = bandera;
		}
	}
}
