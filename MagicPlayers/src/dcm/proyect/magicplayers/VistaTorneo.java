package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dcm.proyect.magicplayers.R.drawable;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VistaTorneo extends Activity {
	int idTorneo;
	int inscritosIniciales;
	int inscritosActuales;
	boolean estaInscrito = false;
	ThreadVistaTorneo tvt = new ThreadVistaTorneo();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vistatorneo);
		Bundle bundle = getIntent().getExtras();
		int id = bundle.getInt("idTorneo");
		idTorneo = id;
		tvt.start();
		try {
			tvt.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		inscritosIniciales = Integer.parseInt(tvt.getDatos().get(9));
		inscritosActuales = inscritosIniciales;

		if (estaInscrito) {
			Button btnInscribirse = (Button) findViewById(R.id.btnInscribirse);
			btnInscribirse.setText("Inscrito");
			btnInscribirse.setTextColor(Color.RED);
		}

		TextView tv = (TextView) findViewById(R.id.tvNombreTorneoVT);
		tv.setText(tvt.getDatos().get(0));
		tv = (TextView) findViewById(R.id.tvOrganizadorTVT);
		tv.setText("Organizador: " + tvt.getDatos().get(1));
		tv = (TextView) findViewById(R.id.tvProvinciaTVT);
		tv.setText(tvt.getDatos().get(2));
		tv = (TextView) findViewById(R.id.tvLocalidadVT);
		tv.setText(tvt.getDatos().get(3));
		tv = (TextView) findViewById(R.id.tvFechaVT);
		tv.setText(tvt.getDatos().get(4));
		tv = (TextView) findViewById(R.id.tvHoraVT);
		tv.setText(tvt.getDatos().get(5));
		tv = (TextView) findViewById(R.id.tvPrecioVT);
		tv.setText(tvt.getDatos().get(6));
		tv = (TextView) findViewById(R.id.tvFormatoVT);
		tv.setText(tvt.getDatos().get(7));
		tv = (TextView) findViewById(R.id.tvDescripcionVT);
		tv.setText(tvt.getDatos().get(8));
		tv = (TextView) findViewById(R.id.tvInscritosVT);
		tv.setText("Jugadores inscritos: " + tvt.getDatos().get(9));
	}

	public void inscribirTorneo(View v) {
		if (!estaInscrito) {
			ThreadInscribirTorneo tit = new ThreadInscribirTorneo();
			tit.start();
			try {
				tit.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
			dlgAlert.setMessage("Enhorabuena. Te han inscrito en el torneo.");
			dlgAlert.setTitle("Inscrito!");
			dlgAlert.setPositiveButton("Aceptar", null);
			dlgAlert.setCancelable(true);
			dlgAlert.create().show();

			Button btnInscribirse = (Button) findViewById(R.id.btnInscribirse);
			btnInscribirse.setText("Inscrito");
			btnInscribirse.setTextColor(Color.RED);
			inscritosActuales++;
			TextView tv = (TextView) findViewById(R.id.tvInscritosVT);
			tv.setText("Jugadores inscritos: " + inscritosActuales);
			estaInscrito = true;
		} else {
			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
			dlgAlert.setMessage("¿Desea desinscribirse del torneo?");
			dlgAlert.setTitle("Desincribirse");
			dlgAlert.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			dlgAlert.setPositiveButton("Si",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							ThreadInscribirTorneo tit = new ThreadInscribirTorneo();
							tit.start();
							try {
								tit.join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Button btnInscribirse = (Button) findViewById(R.id.btnInscribirse);
							btnInscribirse.setText("Inscribirse");
							inscritosActuales--;
							TextView tv = (TextView) findViewById(R.id.tvInscritosVT);
							tv.setText("Jugadores inscritos: " + inscritosActuales);
							estaInscrito = false;
							btnInscribirse.setTextColor(Color.WHITE);
						}
					});
			dlgAlert.setCancelable(true);
			dlgAlert.create().show();

		}
	}
	
	public void verInsrcritos(View v) {
		String consulta;
		consulta = " join Participacion where Usuario.nombreU = Participacion.nombreU and Participacion.idTorneo = '"+idTorneo+"' ";
		Intent i = new Intent(this, JugadoresBuscados.class);
		i.putExtra("consulta", consulta);
		startActivity(i);		
	}

	// Hilo que busca los torneos
	public class ThreadVistaTorneo extends Thread {

		String dato;
		ArrayList<String> datos = new ArrayList<String>();

		@Override
		public void run() {
			String descripcion = "";
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
				// Se obtienen las coordenadas del usuario
				Statement stat = conn.createStatement();
				ResultSet rs = stat
						.executeQuery("SELECT * From Torneo where idTorneo = "
								+ idTorneo);
				while (rs.next()) {
					datos.add(rs.getString("nombreTorneo"));
					datos.add(rs.getString("organizador"));
					datos.add(rs.getString("provincia"));
					datos.add(rs.getString("localidad"));
					datos.add("Fecha: " + rs.getString("fecha"));
					datos.add("Hora: " + rs.getString("hora"));
					datos.add("Precio: " + rs.getString("precio") + "€");
					datos.add("Formato: " + rs.getString("formato"));
					if (rs.getString("descripcion").length() == 0) {
						descripcion = "No hay descripción.";
					} else {
						descripcion = rs.getString("descripcion");
					}
					datos.add(descripcion);
				}

				rs = stat
						.executeQuery("SELECT count(*) as cuenta From Participacion where idTorneo = "
								+ idTorneo);
				while (rs.next()) {
					datos.add(rs.getString("cuenta"));
				}

				rs = stat
						.executeQuery("SELECT count(*) as cuenta From Participacion where idTorneo = "
								+ idTorneo
								+ " and nombreU = '"
								+ Login.nombreUsuario + "';");
				while (rs.next()) {
					if (Integer.parseInt(rs.getString("cuenta")) > 0) {
						estaInscrito = true;
					}
				}

			} catch (SQLException e) {

			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}

		public ArrayList<String> getDatos() {
			return datos;
		}

	}

	// Hilo que inscribe usuarios en los torneos
	public class ThreadInscribirTorneo extends Thread {

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
				if (!estaInscrito) {
					// Se obtienen las coordenadas del usuario
					Statement stat = conn.createStatement();
					stat.executeUpdate("Insert into Participacion values ('"
							+ Login.nombreUsuario + "', " + idTorneo + ");");
				} else {
					Statement stat = conn.createStatement();
					stat.executeUpdate("Delete from Participacion where nombreU = '"
							+ Login.nombreUsuario
							+ "' and idTorneo="
							+ idTorneo);
				}
			} catch (SQLException e) {

			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}

	}

}
