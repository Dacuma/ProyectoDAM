package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CentroDeMensajes extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.centrodemensajes);

		TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
		tabs.setup();

		TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("No Leidos");
		tabs.addTab(spec);

		spec = tabs.newTabSpec("mitab2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Leidos");
		tabs.addTab(spec);

		tabs.setCurrentTab(0);
		cargarMensajes();
	}

	@Override
	protected void onResume() {
		super.onResume();
		cargarMensajes();
	}
	
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(this, MenuPrincipal.class);
		startActivity(i);
	}

	public void cargarMensajes() {
		ArrayList<Mensaje> mensajesLeidosArray;
		ArrayList<Mensaje> mensajesNoLeidosArray;

		ThreadObtenerMensajes tom = new ThreadObtenerMensajes();
		tom.start();
		try {
			tom.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mensajesLeidosArray = tom.getMensajesLeidos();
		mensajesNoLeidosArray = tom.getMensajesNoLeidos();

		// TRABAJANDO EN ESTO-----

		ListView listaLeidos = (ListView) findViewById(R.id.lvMensajesLeidos);
		listaLeidos.setAdapter(new AdaptadorLista(this,
				R.layout.mostarmensajesentrada, mensajesLeidosArray) {

			@Override
			public void onEntrada(Object entrada, View view) {
				TextView texto_derecha_entrada = (TextView) view
						.findViewById(R.id.tvNombreEmisor);
				texto_derecha_entrada.setText("De: "
						+ ((Mensaje) entrada).getEmisor());

				TextView texto_superior_entrada = (TextView) view
						.findViewById(R.id.tvAsunto);
				texto_superior_entrada.setText("Asunto: "
						+ ((Mensaje) entrada).getAsunto());

			}
		});

		ListView listaNoLeidos = (ListView) findViewById(R.id.lvMensajesNoLeidos);
		listaNoLeidos.setAdapter(new AdaptadorLista(this,
				R.layout.mostarmensajesentrada, mensajesNoLeidosArray) {

			@Override
			public void onEntrada(Object entrada, View view) {
				TextView texto_derecha_entrada = (TextView) view
						.findViewById(R.id.tvNombreEmisor);
				texto_derecha_entrada.setText("De: "
						+ ((Mensaje) entrada).getEmisor());

				TextView texto_superior_entrada = (TextView) view
						.findViewById(R.id.tvAsunto);
				texto_superior_entrada.setText("Asunto: "
						+ ((Mensaje) entrada).getAsunto());

			}
		});

		// Métodos que se ejecuta al clickar sobre un mensaje
		listaLeidos.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> pariente, View view,
					int posicion, long id) {
				Intent i = new Intent(CentroDeMensajes.this, VistaMensaje.class);
				Mensaje elegido = (Mensaje) pariente
						.getItemAtPosition(posicion);
				String emisor = elegido.getEmisor();
				String asunto = elegido.getAsunto();
				String mensaje = elegido.getMensaje();
				i.putExtra("emisor", emisor);
				i.putExtra("asunto", asunto);
				i.putExtra("mensaje", mensaje);
				startActivity(i);

			}
		});

		listaNoLeidos.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> pariente, View view,
					int posicion, long id) {

				Intent i = new Intent(CentroDeMensajes.this, VistaMensaje.class);
				Mensaje elegido = (Mensaje) pariente
						.getItemAtPosition(posicion);
				// Actualizamos el mensaje a leido
				ThreadActualizarLeidos tal = new ThreadActualizarLeidos(elegido
						.getIdMensaje());
				tal.start();
				try {
					tal.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Fin actualizar a leido

				String emisor = elegido.getEmisor();
				String asunto = elegido.getAsunto();
				String mensaje = elegido.getMensaje();
				i.putExtra("emisor", emisor);
				i.putExtra("asunto", asunto);
				i.putExtra("mensaje", mensaje);
				startActivity(i);

			}
		});
	}

	// Hilo que obtiene los mensajes leidos y no leidos
	public class ThreadObtenerMensajes extends Thread {

		ArrayList<Mensaje> mensajesLeidos = new ArrayList<Mensaje>();
		ArrayList<Mensaje> mensajesNoLeidos = new ArrayList<Mensaje>();
		int idMensaje;
		String emisor;
		String asunto;
		String mensaje;
		String receptor;

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
				// Se obtienen los mensajes NO leidos "leido = 0"
				Statement stat = conn.createStatement();
				ResultSet rs = stat
						.executeQuery("Select * from Mensaje where leido = 0 and nombreReceptor='"
								+ Login.nombreUsuario + "';");
				while (rs.next()) {
					idMensaje = rs.getInt("idMensaje");
					emisor = rs.getString("nombreEmisor");
					asunto = rs.getString("asunto");
					mensaje = rs.getString("mensaje");
					receptor = rs.getString("nombreReceptor");
					Mensaje mens = new Mensaje(idMensaje, emisor, asunto,
							mensaje, receptor);
					mensajesNoLeidos.add(mens);
				}

				// Se obtienen los mensajes NO leidos "leido = 1"
				rs = stat
						.executeQuery("Select * from Mensaje where leido = 1 and nombreReceptor='"
								+ Login.nombreUsuario + "';");
				while (rs.next()) {
					idMensaje = rs.getInt("idMensaje");
					emisor = rs.getString("nombreEmisor");
					asunto = rs.getString("asunto");
					mensaje = rs.getString("mensaje");
					receptor = rs.getString("nombreReceptor");
					Mensaje mens = new Mensaje(idMensaje, emisor, asunto,
							mensaje, receptor);
					mensajesLeidos.add(mens);
				}

				rs.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}

		public ArrayList<Mensaje> getMensajesLeidos() {
			return mensajesLeidos;
		}

		public ArrayList<Mensaje> getMensajesNoLeidos() {
			return mensajesNoLeidos;
		}

	}

	// Hilo que actualiza los mensajes no leidos a leidos
	public class ThreadActualizarLeidos extends Thread {

		int id;

		public ThreadActualizarLeidos(int id) {
			super();
			this.id = id;
		}

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
				// Se actualiza el mensaje leido
				Statement stat = conn.createStatement();
				stat.executeUpdate("Update Mensaje set leido = 1 where idMensaje = "
						+ id);

			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}

	}

}
