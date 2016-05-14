package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//To-Do
public class MenuPrincipal extends Activity {
	int mensajesNoLeidos = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuprincipal);
		// Cada vez que se accede por primera vez a la aplicación se guardan las
		// coordenadas en la base de datos.
		LocalizadorGPS loc = new LocalizadorGPS(this);
		try {
			contarMensajesNuevos();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		TextView tvMensajes = (TextView) findViewById(R.id.tvMensajesMenu);
		if (mensajesNoLeidos > 0) {
			tvMensajes
					.setText("Centro de\nMensajes (" + mensajesNoLeidos + ")");
		} else {
			tvMensajes.setText("Centro de\nMensajes");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.ver_perfil) {
			lanzarVerPerfil(null);
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			contarMensajesNuevos();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		TextView tvMensajes = (TextView) findViewById(R.id.tvMensajesMenu);
		if (mensajesNoLeidos > 0) {
			tvMensajes
					.setText("Centro de\nMensajes (" + mensajesNoLeidos + ")");
		} else {
			tvMensajes.setText("Centro de\nMensajes");
		}
	}

	public void lanzarJugadoresCercanos(View v) {
		Intent i = new Intent(this, JugadoresCercanos.class);
		startActivity(i);
	}

	public void lanzarConfigurarPefil(View v) {
		Intent i = new Intent(this, ConfigurarPerfil.class);
		startActivity(i);
	}

	public void lanzarTorneosBuscados(View v) {
		Intent i = new Intent(this, BuscarTorneos.class);
		startActivity(i);
	}

	public void lanzarBuscarJugadores(View v) {
		Intent i = new Intent(this, BuscarJugadores.class);
		startActivity(i);
	}

	public void lanzarAltaTorneo(View v) {
		Intent i = new Intent(this, AltaTorneo.class);
		startActivity(i);
	}

	public void lanzarCentroDeMensajes(View v) {
		Intent i = new Intent(this, CentroDeMensajes.class);
		startActivity(i);
	}

	public void lanzarVerPerfil(View v) {
		Intent i = new Intent(this, VerPerfil.class);
		startActivity(i);
	}
	
	public void salirAplicación(View v) {
		Intent i = new Intent(this, Login.class);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
		alertaCerrarSesion();
	}

	public void alertaCerrarSesion() {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
		dlgAlert.setMessage("¿Desea Salir?");
		dlgAlert.setTitle("Salir");
		dlgAlert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Login.contrasenaUsuario = "";
				Login.nombreUsuario = "";
				salirAplicación(null);
			}
		});
		dlgAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// dismiss the dialog
			}
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	public void contarMensajesNuevos() throws InterruptedException {
		ThreadMensajesNuevos tmn = new ThreadMensajesNuevos();
		tmn.start();
		tmn.join();
	}

	public class ThreadMensajesNuevos extends Thread {

		@Override
		public void run() {
			Connection conn = null;
			try {
				// Conexion con la base de datos.
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(ConexionesDB.serverDB,
						ConexionesDB.usuarioDB, ConexionesDB.passDB);
			} catch (SQLException se) {

			} catch (ClassNotFoundException e) {

			} catch (Exception e) {

			}

			try {
				Statement stat = conn.createStatement();
				// Cuenta los mensajes no leidos.
				ResultSet rs = stat
						.executeQuery("SELECT count(*) as noLeidos from Mensaje where nombreReceptor = '"
								+ Login.nombreUsuario + "' and leido = 0;");
				while (rs.next()) {
					mensajesNoLeidos = rs.getInt("noLeidos");
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

	}
}
