package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CambiarPassword extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cambiocontrasena);
	}

	public void lanzarVolver(View v) {
		Intent i = new Intent(this, ConfigurarPerfil.class);
		startActivity(i);
	}

	public void metodoGuardar(View v) throws InterruptedException {

		String passwordActualVista = "";
		String passwordActualBBDD = "";
		String passwordNueva1 = "";
		String passwordNueva2 = "";
		ThreadRecuperarPassword tcp = new ThreadRecuperarPassword();
		tcp.start();
		tcp.join();
		passwordActualBBDD = tcp.getPassword();
		EditText etP1 = (EditText) findViewById(R.id.etPassNueva1);
		passwordNueva1 = etP1.getText().toString();
		EditText etP2 = (EditText) findViewById(R.id.etPassNueva2);
		passwordNueva2 = etP2.getText().toString();

		EditText etPActual = (EditText) findViewById(R.id.etPassVieja);
		passwordActualVista = EncriptarPasswd.encriptar(etPActual.getText()
				.toString());
		if (!passwordActualBBDD.equals(passwordActualVista)) {
			Toast.makeText(this, "La contraseña introducida no es correcta",
					Toast.LENGTH_SHORT).show();
			etPActual.setText("");
		} else if (passwordNueva1.length() < 6 | passwordNueva1.length() > 13) {
			Toast toast = Toast.makeText(this,
					"La contraseña nueva ha de tener entre 6 y 13 caracteres.",
					Toast.LENGTH_SHORT);
			toast.show();
			etP1.setText("");
			etP2.setText("");
			// Comprueba que las contraseñas coinciden en los dos campos
		} else if (!passwordNueva1.equals(passwordNueva2)) {
			Toast toast = Toast.makeText(this,
					"Las contraseñas nuevas no coinciden.", Toast.LENGTH_SHORT);
			toast.show();
			etP1.setText("");
			etP2.setText("");
		} else {
			ThreadGuardarPassword tgp = new ThreadGuardarPassword(EncriptarPasswd
					.encriptar(passwordNueva1));
			tgp.start();
			Toast toast = Toast.makeText(this,
					"Enhorabuena.Contraseña cambiada correctamente.",
					Toast.LENGTH_SHORT);
			toast.show();

			Intent i = new Intent(this, ConfigurarPerfil.class);
			startActivity(i);
		}
	}

	public class ThreadRecuperarPassword extends Thread {
		String nombre = Login.nombreUsuario;
		String password = "";

		@Override
		public void run() {
			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(ConexionesDB.serverDB,
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

				while (rs.next()) {
					password = rs.getString("contrasenaU");
				}
				rs.close();
			} catch (SQLException e) {

			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}

		public String getPassword() {
			return password;
		}
	}

	public class ThreadGuardarPassword extends Thread {
		String nombre = Login.nombreUsuario;
		String password = "";
		
		public ThreadGuardarPassword(String password){
			this.password = password;
		}

		public void run() {
			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(ConexionesDB.serverDB,
						ConexionesDB.usuarioDB, ConexionesDB.passDB);
			} catch (SQLException se) {

			} catch (ClassNotFoundException e) {

			} catch (Exception e) {

			}

			try {
				Statement stat = conn.createStatement();
				stat.executeUpdate("Update Usuario set contrasenaU='"
						+ password + "' where nombreU='" + nombre + "';");

			} catch (SQLException e) {

			}
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}

}
