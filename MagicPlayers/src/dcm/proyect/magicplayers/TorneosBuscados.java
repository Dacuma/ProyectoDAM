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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class TorneosBuscados  extends Activity {
	String consulta;
	int idTorneo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostrarusuariostorneos);
		Bundle extras = getIntent().getExtras(); 
		String consultaString = extras.getString("consulta");
		consulta = consultaString;
		ArrayList<TorneoEntrada> torneosBuscados = new ArrayList<TorneoEntrada>();
		ThreadTorneosBuscados ttb = new ThreadTorneosBuscados();
		try {
			ttb.start();
			ttb.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		torneosBuscados = ttb.get_torneos();

		ListView lista = (ListView) findViewById(R.id.lvUsers);
		lista.setAdapter(new AdaptadorLista(this,
				R.layout.mostrartorneosentrada, torneosBuscados) {

			@Override
			public void onEntrada(Object entrada, View view) {
				TextView textoNT = (TextView) view
						.findViewById(R.id.textoNombreTorneo);
				textoNT.setText(((TorneoEntrada) entrada)
						.getTextoNombreTorneo());

				TextView textoFT = (TextView) view
						.findViewById(R.id.textoFormatoTorneo);
				textoFT.setText("Formato: " +((TorneoEntrada) entrada)
						.getTextoFormatoTorneo());

				TextView textoCT = (TextView) view
						.findViewById(R.id.textoCiudadTorneo);
				textoCT.setText("Provincia: " +((TorneoEntrada) entrada)
						.getTextoCiudadTorneo());

				TextView textoPT = (TextView) view
						.findViewById(R.id.textoPrecioTorneo);
				textoPT.setText(((TorneoEntrada) entrada)
						.getTextoPrecioTorneo()+"€");
			}
		});

		// Método que se ejecuta al clickar sobre un torneo
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> pariente, View view,
					int posicion, long id) {
				Intent i = new Intent(TorneosBuscados.this,
						VistaTorneo.class);
				TorneoEntrada elegido = (TorneoEntrada) pariente
						.getItemAtPosition(posicion);
				int idTorneo = elegido.getID();
				i.putExtra("idTorneo", idTorneo);
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
	public class ThreadTorneosBuscados extends Thread {

		ArrayList<TorneoEntrada> torneos = new ArrayList<TorneoEntrada>();
		// Variable que controla el número de torneos mostrados
		int numeroResultados = 20;


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
						.executeQuery("SELECT * from Torneo where nombreTorneo is not null " +consulta);

				// Se obtienen los datos
				while (rs.next()) {
					String nombreTorneo = rs.getString("nombreTorneo");
					String modalidadTorneo = rs.getString("formato");
					String provinciaTorneo = rs.getString("provincia");
					String precioTorneo = Double.toString(rs.getDouble("precio"));
					int id = rs.getInt("idTorneo");
					
					TorneoEntrada te = new TorneoEntrada(nombreTorneo, modalidadTorneo,
							provinciaTorneo, precioTorneo, id);
					torneos.add(te);
				}
				rs.close();
			} catch (SQLException e) {
				Toast.makeText(TorneosBuscados.this, "Error",
						Toast.LENGTH_LONG).show();
			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}

		public ArrayList<TorneoEntrada> get_torneos() {
			return torneos;
		}
		

	}

}