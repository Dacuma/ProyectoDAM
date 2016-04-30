package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//Clase que corresponde con la actividad que muestra a los jugadores 
//mas cercanos en un ListView.
public class JugadoresCercanos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostrarusuarios);
		ArrayList<UsuarioEntrada> usuariosCercanos = new ArrayList<UsuarioEntrada>();
		ThreadUsuariosCercanos tuc = new ThreadUsuariosCercanos(
				Login.nombreUsuario);
		try {
			tuc.start();
			tuc.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		usuariosCercanos = tuc.get_usuarios();

		ListView lista = (ListView) findViewById(R.id.lvUsers);
		lista.setAdapter(new AdaptadorLista(this,
				R.layout.mostrarusuariosentrada, usuariosCercanos) {

			@Override
			public void onEntrada(Object entrada, View view) {
				TextView texto_derecha_entrada = (TextView) view
						.findViewById(R.id.textView_derecha);
				texto_derecha_entrada.setText(((UsuarioEntrada) entrada)
						.get_textoDerecha());

				TextView texto_superior_entrada = (TextView) view
						.findViewById(R.id.textView_superior);
				texto_superior_entrada.setText(((UsuarioEntrada) entrada)
						.get_textoEncima());

				TextView texto_inferior_entrada = (TextView) view
						.findViewById(R.id.textView_inferior);
				texto_inferior_entrada.setText(((UsuarioEntrada) entrada)
						.get_textoDebajo());

				ImageView imagen_entrada = (ImageView) view
						.findViewById(R.id.imageView_imagen);
				imagen_entrada.setImageResource(((UsuarioEntrada) entrada)
						.get_idImagen());
			}
		});

		// Método que se ejecuta al clickar sobre un usuario
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> pariente, View view,
					int posicion, long id) {
				Intent i = new Intent(JugadoresCercanos.this,
						VistaUsuario.class);
				UsuarioEntrada elegido = (UsuarioEntrada) pariente
						.getItemAtPosition(posicion);
				String nombreU = elegido.get_textoEncima();
				String distanciaU = elegido.get_textoDerecha();
				i.putExtra("nombreU", nombreU);
				i.putExtra("distanciaU",distanciaU);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Hilo que busca los jugadores mas cercanos del usuario
	public class ThreadUsuariosCercanos extends Thread {

		String nombre = "";
		ArrayList<UsuarioEntrada> usuarios = new ArrayList<UsuarioEntrada>();
		// Variable que controla el número de usuarios mostrados
		int numeroResultados = 20;

		// Se le pasa el nombre del usuario logeado
		public ThreadUsuariosCercanos(String nombre) {
			this.nombre = nombre;
		}

		@Override
		public void run() {
			Connection conn = null;
			String latitud = "";
			String longitud = "";
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
						.executeQuery("SELECT latitud,longitud from Usuario where nombreU='"
								+ nombre + "';");
				while (rs.next()) {
					latitud = rs.getString("latitud");
					longitud = rs.getString("longitud");
				}

				// Se buscan los jugadores ordenados por proximidad
				rs = stat
						.executeQuery("Select nombreU, provincia, SQRT(POW((Usuario.latitud-"
								+ latitud
								+ "), 2 ) + POW((Usuario.longitud-"
								+ longitud
								+ "), 2 )) as distancia, imgPerfil from Usuario where nombreU!='"
								+ nombre
								+ "' and latitud is not null and longitud is not null order by SQRT(POW((Usuario.latitud-"
								+ latitud
								+ "), 2 ) + POW((Usuario.longitud-"
								+ longitud
								+ "), 2 )) limit "
								+ numeroResultados + ";");

				// Se obtienen los datos
				while (rs.next()) {
					String nombre = rs.getString("nombreU");
					String provincia = rs.getString("provincia");
					String distancia = rs.getString("distancia");
					int imgPerfil = rs.getInt("imgPerfil");
					Double dist = Double.parseDouble(distancia)
							* LocalizadorGPS.metrosXGrado;
					// Si la distancia es menor que 1 o mayor de 500km se trata.
					if (dist < 1) {
						distancia = "<1 Km";
					} else if (dist > 500) {
						distancia = ">500Km";
					} else {
						distancia = dist.intValue() + " Km";
					}
					// Elegimos imagen de perfil
					switch (imgPerfil) {
					case 0:
						imgPerfil = R.drawable.perfilajani;
						break;
					case 1:
						imgPerfil = R.drawable.perfilchandra;
						break;
					case 2:
						imgPerfil = R.drawable.perfilelspeth;
						break;
					case 3:
						imgPerfil = R.drawable.perfilgarruk;
						break;
					case 4:
						imgPerfil = R.drawable.perfiljace;
						break;
					case 5:
						imgPerfil = R.drawable.perfilkiora;
						break;
					case 6:
						imgPerfil = R.drawable.perfilliliana;
						break;
					case 7:
						imgPerfil = R.drawable.perfilnissa;
						break;
					case 8:
						imgPerfil = R.drawable.perfilsarkhan;
						break;
					case 9:
						imgPerfil = R.drawable.perfiltezzeret;
						break;
					default:
						imgPerfil = R.drawable.perfiljace;
					}
					UsuarioEntrada du = new UsuarioEntrada(imgPerfil, nombre,
							provincia, distancia);
					usuarios.add(du);
				}
				rs.close();
			} catch (SQLException e) {
				Toast.makeText(JugadoresCercanos.this, "Error",
						Toast.LENGTH_LONG).show();
			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}

		public ArrayList<UsuarioEntrada> get_usuarios() {
			return usuarios;
		}

	}

}