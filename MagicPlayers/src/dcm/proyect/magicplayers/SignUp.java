package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//Actividad que corresponde al registro de usuarios
public class SignUp extends Activity {
	Login login = new Login();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void startLogin(View v) {
		Intent i = new Intent(this, Login.class);
		startActivity(i);
	}

	public void registrarUsuario(View v) throws InterruptedException {
		// Se captan los catos introducidos en la ventana de registro.
		EditText text = (EditText) findViewById(R.id.etNombreUsuarioSI);
		String usuario = "";
		usuario = text.getText().toString();
		text = (EditText) findViewById(R.id.etPasswordSI);
		String passwd = "";
		passwd = text.getText().toString();
		text = (EditText) findViewById(R.id.etPassword2SI);
		String passwd2 = text.getText().toString();
		text = (EditText) findViewById(R.id.etEmailSI);
		String mail = "";
		mail = text.getText().toString();
		Spinner spinner = (Spinner) findViewById(R.id.spnPais);
		String pais = "";
		pais = String.valueOf(spinner.getSelectedItemPosition());
		spinner = (Spinner) findViewById(R.id.spnProvincia);
		String provincia = "";
		provincia = String.valueOf(spinner.getSelectedItemPosition());
		text = (EditText) findViewById(R.id.etCP);
		String cP = "";
		cP = text.getText().toString();
		text = (EditText) findViewById(R.id.etDCI);
		String dci = "";
		dci = text.getText().toString();

		// Se comprueban los datos, si son correcto el usuario se registra.
		// Comprueba que no haya campos vacíos
		if (usuario.length() == 0 | passwd.length() == 0
				| passwd2.length() == 0 | mail.length() == 0) {
			Toast toast = Toast
					.makeText(this, "Por favor, complete todos los campos.",
							Toast.LENGTH_SHORT);
			toast.show();
			// Comprueba nombre de usuario entre 4 y 13 caracteres
		} else if (usuario.length() < 4 | usuario.length() > 13) {
			Toast toast = Toast
					.makeText(
							this,
							"El nombre de usuario ha de tener entre 4 y 13 caracteres.",
							Toast.LENGTH_SHORT);
			toast.show();
			// Comprueba contraseña entre 6 y 13 caracteres
		} else if (passwd.length() < 6 | passwd.length() > 13) {
			Toast toast = Toast.makeText(this,
					"La contraseña ha de tener entre 6 y 13 caracteres.",
					Toast.LENGTH_SHORT);
			toast.show();
			// Comprueba que las contraseñas coinciden en los dos campos
		} else if (!passwd.equals(passwd2)) {
			Toast toast = Toast.makeText(this, "Las contraseñas no coinciden.",
					Toast.LENGTH_SHORT);
			toast.show();
			// Comprueba que la dirección de correo electrónico es correcta
			// mediante una exp. regular
		} else if (!Validaciones.validarEmail(mail)) {
			Toast toast = Toast
					.makeText(
							this,
							"La cuenta de correo electrónico introducida es incorrecta.",
							Toast.LENGTH_SHORT);
			toast.show();
			// Comprueba el código postal en caso de haberse introducido
		} else if (cP.length() > 0 && !Validaciones.validarCP(cP)) {
			Toast toast = Toast.makeText(this, "Código Postal incorrecto.",
					Toast.LENGTH_SHORT);
			toast.show();
		} else if (dci.length() > 0 && !Validaciones.validarDCI(dci)) {
			Toast toast = Toast.makeText(this, "Numero DCI incorrecto.",
					Toast.LENGTH_SHORT);
			toast.show();
		} else {
			// Se comprueba que el nombre de usuario no existe ya en la
			// bbdd.
			ThreadUsuariosRepeBBDD cDB = new ThreadUsuariosRepeBBDD(usuario,
					passwd, mail, pais, provincia, cP, dci);
			cDB.start();
			cDB.join();
			if (cDB.isBandera()) {
				Toast toast = Toast.makeText(this,
						"El nombre de Usuario ya existe.", Toast.LENGTH_SHORT);
				toast.show();
			} else if (cDB.isBanderaEmail()) {
				Toast toast = Toast
						.makeText(
								this,
								"El correo electrónico introducido ya está asociado a una cuenta.",
								Toast.LENGTH_SHORT);
				toast.show();
			} else {
				Toast toast = Toast
						.makeText(
								this,
								"¡Enhorabuena!, te has registrado con éxito. Logea con tu nombre de usuario y contraseña.",
								Toast.LENGTH_SHORT);
				toast.show();
				finish();
			}
		}
	}

	// Hilo que comprueba que el nombre de usuario no existe en la bbdd ya.
	public class ThreadUsuariosRepeBBDD extends Thread {
		String nombre = "";
		String passwd = "";
		String email = "";
		String pais = "";
		String provincia = "";
		String cp = "";
		String dci = "";
		boolean bandera = false;
		boolean banderaEmail = false;

		public ThreadUsuariosRepeBBDD(String nombre, String passwd,
				String email, String pais, String provincia, String cp,
				String dci) {
			this.nombre = nombre;
			this.passwd = passwd;
			this.email = email;
			this.pais = pais;
			this.provincia = provincia;
			this.cp = cp;
			this.dci = dci;
		}

		@Override
		public void run() {
			Connection conn = null;
			try {
				// Conexion con la base de datos.
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(
						"jdbc:mysql://db4free.net:3306/magicplayers",
						"dcuellar", "QAZwsx123");
			} catch (SQLException se) {

			} catch (ClassNotFoundException e) {

			} catch (Exception e) {

			}

			try {
				Statement stat = conn.createStatement();
				// Comprueba si el nombre de usuario ya existe.
				ResultSet rs = stat
						.executeQuery("SELECT nombreU from Usuario where nombreU='"
								+ nombre + "';");
				while (rs.next()) {
					bandera = true;
				}
				// Comprueba si el correo ya existe.
				rs = stat
						.executeQuery("SELECT email from Usuario where email='"
								+ email + "';");
				while (rs.next()) {
					banderaEmail = true;
				}
				// Seleccionamos una imágen al azar.
				Random generator = new Random();
				int imagenPerfil = generator.nextInt(10);
				// Si no existen ni el nombre ni el correo, registra al usuario
				// en la base de datos.
				if (!bandera && !banderaEmail) {
					// Encriptamos la contraseña para aumentar la seguridad.
					passwd = EncriptarPasswd.encriptar(passwd);
					stat.executeUpdate("INSERT INTO Usuario VALUES ('" + nombre
							+ "','" + passwd + "', NULL,'" + dci + "','" + pais
							+ "','" + provincia + "','" + cp
							+ "', NULL, NULL, NULL, 1, NULL,'" + email + "',"
							+ imagenPerfil + ")");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
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

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public boolean isBanderaEmail() {
			return banderaEmail;
		}

		public void setBanderaEmail(boolean banderaEmail) {
			this.banderaEmail = banderaEmail;
		}

	}

}
