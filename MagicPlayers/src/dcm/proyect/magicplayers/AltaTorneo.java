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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AltaTorneo extends Activity {
	int idTorneo = 0;
	String nombreTorneo = "";
	String provincia = "";
	String ciudad = "";
	String fecha = "";
	String hora = "";
	double precio = 0;
	String formato = "";
	String descripcion = "";
	int aforoMax = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.altatorneo);
		ThreadGetIdTorneo tgit = new ThreadGetIdTorneo();
		tgit.start();
		try {
			tgit.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		idTorneo++;
	}

	public void volverAT(View v) {
		Intent i = new Intent(this, MenuPrincipal.class);
		startActivity(i);
	}

	public void altaTorneo(View v) {
		boolean bandera = true;
		// NOMBRE DEL TORNEO
		EditText et = (EditText) findViewById(R.id.etATnTorneo);
		nombreTorneo = et.getText().toString();
		if (nombreTorneo.equals("")) {
			Toast.makeText(this, "El nombre del torneo no puede estar vacío",
					Toast.LENGTH_SHORT).show();
			bandera = false;
		}
		// PROVINCIA
		Spinner sp = (Spinner) findViewById(R.id.spnProvinciasAT);
		provincia = sp.getSelectedItem().toString();
		// CIUDAD
		et = (EditText) findViewById(R.id.etATCiudad);
		ciudad = et.getText().toString();
		// FECHA
		DatePicker cv = (DatePicker) findViewById(R.id.dpATFecha);
		// Formateo la fecha a xx/xx/xxxx
		fecha = Integer.toString(cv.getDayOfMonth()) + "/"
				+ Integer.toString(cv.getMonth()) + "/"
				+ Integer.toString(cv.getYear());
		// HORA
		et = (EditText) findViewById(R.id.etATHora);
		hora = et.getText().toString() + ":";
		if (bandera) {
			try {
				int horaInsetada = Integer.parseInt(et.getText().toString());
				if (horaInsetada < 0 || horaInsetada > 23) {
					Toast.makeText(this, "La hora es incorrecta",
							Toast.LENGTH_SHORT).show();
					bandera = false;
				}
			} catch (Exception e) {
				Toast.makeText(this, "La hora es incorrecta",
						Toast.LENGTH_SHORT).show();
				bandera = false;
			}
		}
		et = (EditText) findViewById(R.id.etATMinutos);
		hora = hora + et.getText().toString();
		try {
			int minutosInsetados = Integer.parseInt(et.getText().toString());
			if (minutosInsetados < 0 || minutosInsetados > 59) {
				Toast.makeText(this, "La hora es incorrecta",
						Toast.LENGTH_SHORT).show();
				bandera = false;
			}
		} catch (Exception e) {
			Toast.makeText(this, "La hora es incorrecta", Toast.LENGTH_SHORT)
					.show();
			bandera = false;
		}
		// PRECIO
		et = (EditText) findViewById(R.id.etATPrecio);
		if (bandera) {
			try {
				precio = Double.parseDouble(et.getText().toString());
			} catch (Exception e) {
				Toast.makeText(this, "El precio no puede estar vacío",
						Toast.LENGTH_SHORT).show();
				bandera = false;
			}
		}
		// FORMATO
		sp = (Spinner) findViewById(R.id.spnFormatoAT);
		formato = sp.getSelectedItem().toString();
		// DESCRIPCION
		et = (EditText) findViewById(R.id.etATDescripcion);
		descripcion = et.getText().toString();

		if (ciudad.equals("")) {
			ciudad = provincia;
		}

		if (bandera) {
			ThreadAltaTorneo tat = new ThreadAltaTorneo();
			tat.start();
			try {
				tat.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idTorneo++;
			Toast.makeText(this, "El Torneo se ha añadido con éxito!",
					Toast.LENGTH_LONG).show();
			Intent i = new Intent(this, MenuPrincipal.class);
			startActivity(i);
		}
	}

	// Hilo que devuelve el id del último torneo
	public class ThreadGetIdTorneo extends Thread {

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
						.executeQuery("SELECT idTorneo from Torneo order by idTorneo desc limit 1;");
				// Se recoge el id del último torneo y se almacena en la
				// variable correspondiente.
				while (rs.next()) {
					idTorneo = rs.getInt("idTorneo");
				}
				rs.close();
			} catch (SQLException e) {

			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}
	}

	// Hilo que devuelve el id del último torneo y guarda los torneos nuevos
	public class ThreadAltaTorneo extends Thread {

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
				stat.executeUpdate("INSERT INTO Torneo VALUES (" + idTorneo
						+ ", '" + Login.nombreUsuario + "', '" + nombreTorneo
						+ "', '" + provincia + "', '" + ciudad + "', '" + fecha
						+ "', '" + hora + "', '" + precio + "', '" + formato
						+ "', '" + descripcion + "', 0);");

			} catch (SQLException e) {

			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}
	}
}
