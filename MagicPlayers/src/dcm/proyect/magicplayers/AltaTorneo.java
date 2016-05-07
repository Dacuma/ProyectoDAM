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
		// PROVINCIA
		Spinner sp = (Spinner) findViewById(R.id.spnProvinciasAT);
		provincia = sp.getSelectedItem().toString();
		// CIUDAD
		EditText et = (EditText) findViewById(R.id.etATCiudad);
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
		et = (EditText) findViewById(R.id.etATMinutos);
		hora = hora + et.getText().toString();
		// PRECIO
		et = (EditText) findViewById(R.id.etATPrecio);
		precio = Double.parseDouble(et.getText().toString());
		// FORMATO
		sp = (Spinner) findViewById(R.id.spnFormatoAT);
		formato = sp.getSelectedItem().toString();
		// DESCRIPCION
		et = (EditText) findViewById(R.id.etATDescripcion);
		descripcion = et.getText().toString();
		// AFORO
		et = (EditText) findViewById(R.id.etATAforo);
		aforoMax = Integer.parseInt(et.getText().toString());

		ThreadAltaTorneo tat = new ThreadAltaTorneo();
		tat.start();
		try {
			tat.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		idTorneo++;
		Toast.makeText(
				this,
				"INSERT INTO Torneo VALUES (" + idTorneo + ", '"
						+ Login.nombreUsuario + "', '" + provincia + "', '"
						+ ciudad + "', '" + fecha + "', '" + hora + "', '"
						+ precio + "', '" + formato + "', '" + descripcion
						+ "', " + aforoMax + ");", Toast.LENGTH_SHORT).show();
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
						+ ", '" + Login.nombreUsuario + "', '" + provincia
						+ "', '" + ciudad + "', '" + fecha + "', '" + hora
						+ "', '" + precio + "', '" + formato + "', '"
						+ descripcion + "', " + aforoMax + ");");

			} catch (SQLException e) {

			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}
	}
}
