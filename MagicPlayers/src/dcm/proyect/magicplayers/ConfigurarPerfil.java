package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.ArrayUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class ConfigurarPerfil extends Activity {
	String[] provincias = { "A Coruna", "Alava", "Albacete", "Alicante",
			"Almeria", "Asturias", "Avila", "Badajoz", "Baleares", "Barcelona",
			"Burgos", "Caceres", "Cadiz", "Cantabria", "Castellon", "Ceuta",
			"Ciudad Real", "Cordoba", "Cuenca", "Girona", "Granada",
			"Guadalajara", "Guipuzcoa", "Huelva", "Huesca", "Jaen", "La Rioja",
			"Las Palmas", "Leon", "Lleida", "Lugo", "Madrid", "Malaga",
			"Melilla", "Murcia", "Navarra", "Ourense", "Palencia",
			"Pontevedra", "Salamanca", "Segovia", "Sevilla", "Soria",
			"Tarragona", "Tenerife", "Teruel", "Toledo", "Valencia",
			"Valladolid", "Vizcaya", "Zamora", "Zaragoza " };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configurarperfil);

		ThreadConfPerfil tcp = new ThreadConfPerfil();
		tcp.start();
		try {
			tcp.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// RELLENO LOS CAMPOS CON LOS DATOS OBTENIDOS DE LA BBDD
		String fj = tcp.getModalidadJugada();
		rellenarFormatosJugados(fj);
		Spinner spPais = (Spinner) findViewById(R.id.spnPais);
		spPais.setSelection(0);
		Spinner spProvincia = (Spinner) findViewById(R.id.spnProvincia);
		spProvincia.setSelection(ArrayUtils.indexOf(provincias,
				tcp.getProvincia()));
		EditText etCP = (EditText) findViewById(R.id.etCP);
		etCP.setText(tcp.getCodigoPostal());
		EditText etEmail = (EditText) findViewById(R.id.etEmail);
		etEmail.setText(tcp.getEmail());
		EditText etDCI = (EditText) findViewById(R.id.etDCI);
		etDCI.setText(tcp.getNumeroDCI());
		int colorFav;
		try {
			colorFav = Integer.parseInt(tcp.getColorFav());
		} catch (Exception e) {
			colorFav = -1;
		}
		rellenarColorFavorito(colorFav);

	}

	public void volverMenuPrincipal(View v) {
		Intent i = new Intent(this, MenuPrincipal.class);
		startActivity(i);
	}

	// Método que guarda todos los nuevos datos en la bbdd
	public void guardarBBDD(View v) throws InterruptedException {
		String formatosJugados = obtenerFormatosJugados();
		Spinner spPais = (Spinner) findViewById(R.id.spnPais);
		String pais = spPais.getSelectedItem().toString();
		Spinner spProvincia = (Spinner) findViewById(R.id.spnProvincia);
		String provincia = spProvincia.getSelectedItem().toString();
		EditText etCP = (EditText) findViewById(R.id.etCP);
		String cP = etCP.getText().toString();
		EditText etEmail = (EditText) findViewById(R.id.etEmail);
		String email = etEmail.getText().toString();
		EditText etDCI = (EditText) findViewById(R.id.etDCI);
		String dci = etDCI.getText().toString();
		String colorFav = obtenerColorFavorito();
		ThreadConfPerfil tcp = new ThreadConfPerfil();

		if (!Validaciones.validarEmail(email)) {
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
			ThreadGuardarDatos tgd = new ThreadGuardarDatos(formatosJugados,
					pais, provincia, cP, email, dci, colorFav);
			tgd.start();
			tgd.join();
			Toast.makeText(this, "Datos actualizados!", Toast.LENGTH_SHORT)
					.show();
			Intent i = new Intent(this, MenuPrincipal.class);
			startActivity(i);
		}
	}

	// Método para rellenar formatos jugados
	public void rellenarFormatosJugados(String fj) {
		CheckBox cb;
		String formato = "0";
		if (fj.indexOf(formato) != -1) {
			cb = (CheckBox) findViewById(R.id.cbVintage);
			cb.setChecked(true);
		}
		formato = "1";
		if (fj.indexOf(formato) != -1) {
			cb = (CheckBox) findViewById(R.id.cbLegacy);
			cb.setChecked(true);
		}
		formato = "2";
		if (fj.indexOf(formato) != -1) {
			cb = (CheckBox) findViewById(R.id.cbModern);
			cb.setChecked(true);
		}
		formato = "3";
		if (fj.indexOf(formato) != -1) {
			cb = (CheckBox) findViewById(R.id.cbStandard);
			cb.setChecked(true);
		}
		formato = "4";
		if (fj.indexOf(formato) != -1) {
			cb = (CheckBox) findViewById(R.id.cbPauper);
			cb.setChecked(true);
		}
		formato = "5";
		if (fj.indexOf(formato) != -1) {
			cb = (CheckBox) findViewById(R.id.cbCommander);
			cb.setChecked(true);
		}
		formato = "6";
		if (fj.indexOf(formato) != -1) {
			cb = (CheckBox) findViewById(R.id.cbCasual);
			cb.setChecked(true);
		}
		formato = "7";
		if (fj.indexOf(formato) != -1) {
			cb = (CheckBox) findViewById(R.id.cbOtros);
			cb.setChecked(true);
		}

	}

	// Método para rellenar color favorito
	public void rellenarColorFavorito(int colorFav) {
		RadioButton rb;
		switch (colorFav) {
		case 0:
			rb = (RadioButton) findViewById(R.id.rbRojo);
			rb.setChecked(true);
			break;
		case 1:
			rb = (RadioButton) findViewById(R.id.rbVerde);
			rb.setChecked(true);
			break;
		case 2:
			rb = (RadioButton) findViewById(R.id.rbBlanco);
			rb.setChecked(true);
			break;
		case 3:
			rb = (RadioButton) findViewById(R.id.rbAzul);
			rb.setChecked(true);
			break;
		case 4:
			rb = (RadioButton) findViewById(R.id.rbNegro);
			rb.setChecked(true);
			break;
		case 5:
			rb = (RadioButton) findViewById(R.id.rbIncoloro);
			rb.setChecked(true);
			break;
		}
	}

	public String obtenerFormatosJugados() {
		String formatosJugados = "";
		CheckBox cb;
		cb = (CheckBox) findViewById(R.id.cbVintage);
		if (cb.isChecked()) {
			formatosJugados = formatosJugados + "0";
		}

		cb = (CheckBox) findViewById(R.id.cbLegacy);
		if (cb.isChecked()) {
			formatosJugados = formatosJugados + "1";
		}

		cb = (CheckBox) findViewById(R.id.cbModern);
		if (cb.isChecked()) {
			formatosJugados = formatosJugados + "2";
		}
		cb = (CheckBox) findViewById(R.id.cbStandard);
		if (cb.isChecked()) {
			formatosJugados = formatosJugados + "3";
		}
		cb = (CheckBox) findViewById(R.id.cbPauper);
		if (cb.isChecked()) {
			formatosJugados = formatosJugados + "4";
		}
		cb = (CheckBox) findViewById(R.id.cbCommander);
		if (cb.isChecked()) {
			formatosJugados = formatosJugados + "5";
		}
		cb = (CheckBox) findViewById(R.id.cbCasual);
		if (cb.isChecked()) {
			formatosJugados = formatosJugados + "6";
		}
		cb = (CheckBox) findViewById(R.id.cbOtros);
		if (cb.isChecked()) {
			formatosJugados = formatosJugados + "7";
		}
		return formatosJugados;
	}

	public String obtenerColorFavorito() {
		RadioButton rb;
		String colorFav = "-1";

		rb = (RadioButton) findViewById(R.id.rbRojo);
		if (rb.isChecked()) {
			colorFav = "0";
		}

		rb = (RadioButton) findViewById(R.id.rbVerde);
		if (rb.isChecked()) {
			colorFav = "1";
		}

		rb = (RadioButton) findViewById(R.id.rbBlanco);
		if (rb.isChecked()) {
			colorFav = "2";
		}

		rb = (RadioButton) findViewById(R.id.rbAzul);
		if (rb.isChecked()) {
			colorFav = "3";
		}

		rb = (RadioButton) findViewById(R.id.rbNegro);
		if (rb.isChecked()) {
			colorFav = "4";
		}

		rb = (RadioButton) findViewById(R.id.rbIncoloro);
		if (rb.isChecked()) {
			colorFav = "5";
		}

		return colorFav;
	}

	// Hilo que recoge los datos de la base de datos y los almacena si tienen
	// cambios.
	public class ThreadConfPerfil extends Thread {
		String nombre = Login.nombreUsuario;
		String modalidadJugada;
		String numeroDCI = "";
		String pais = "";
		String provincia = "";
		String codigoPostal = "";
		String colorFav = "";
		String email = "";

		@Override
		public void run() {
			Connection conn = null;
			try {
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
				ResultSet rs = stat
						.executeQuery("SELECT modalidadJugada, numeroDCI, pais, provincia, codigoPostal, colorFav, email from Usuario where nombreU='"
								+ nombre + "';");
				// Si el usuario existe y la contraseña es correcta bandera =
				// true.
				while (rs.next()) {
					modalidadJugada = rs.getString("modalidadJugada");
					numeroDCI = rs.getString("numeroDCI");
					pais = rs.getString("pais");
					provincia = rs.getString("provincia");
					codigoPostal = rs.getString("codigoPostal");
					colorFav = rs.getString("colorFav");
					email = rs.getString("email");
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

		public String getModalidadJugada() {
			return modalidadJugada;
		}

		public void setModalidadJugada(String modalidadJugada) {
			this.modalidadJugada = modalidadJugada;
		}

		public String getNumeroDCI() {
			return numeroDCI;
		}

		public void setNumeroDCI(String numeroDCI) {
			this.numeroDCI = numeroDCI;
		}

		public String getPais() {
			return pais;
		}

		public void setPais(String pais) {
			this.pais = pais;
		}

		public String getProvincia() {
			return provincia;
		}

		public void setProvincia(String provincia) {
			this.provincia = provincia;
		}

		public String getCodigoPostal() {
			return codigoPostal;
		}

		public void setCodigoPostal(String codigoPostal) {
			this.codigoPostal = codigoPostal;
		}

		public String getColorFav() {
			return colorFav;
		}

		public void setColorFav(String colorFav) {
			this.colorFav = colorFav;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}

	public class ThreadGuardarDatos extends Thread {
		String formatosJugados;
		String pais;
		String provincia;
		String cP;
		String email;
		String dci;
		String colorFav;

		public ThreadGuardarDatos(String formatosJugados, String pais,
				String provincia, String cP, String email, String dci,
				String colorFav) {
			this.formatosJugados = formatosJugados;
			this.pais = pais;
			this.provincia = provincia;
			this.cP = cP;
			this.email = email;
			this.dci = dci;
			this.colorFav = colorFav;
		}

		// Almacena los valores en la bbdd
		public void run() {
			String consulta = "UPDATE Usuario SET modalidadJugada = '"
					+ formatosJugados + "', pais = '" + pais
					+ "', provincia = '" + provincia + "',codigoPostal = '"
					+ cP + "', email = '" + email + "', numeroDCI = '" + dci
					+ "', colorFav = " + colorFav + " WHERE nombreU = '"
					+ Login.nombreUsuario + "'";

			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(
						"jdbc:mysql://db4free.net:3306/magicplayers",
						"dcuellar", "QAZwsx123");
			} catch (SQLException se) {
				se.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Statement stat = conn.createStatement();
				stat.executeUpdate(consulta);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
