package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

//Clase que muestra los datos de un usuario concreto.
public class VistaUsuario extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vistausuario);
		Bundle bundle = getIntent().getExtras();
		String nombreU = bundle.getString("nombreU");
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(nombreU);
		ThreadVistaUsuario tvu = new ThreadVistaUsuario(nombreU);
		tvu.start();
		try {
			tvu.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ImageView iv = (ImageView) findViewById(R.id.imageView_imagen);
		iv.setImageResource(Integer.parseInt(tvu.getDatos().get(0)));
		tv = (TextView) findViewById(R.id.textView2);
		tv.setText(tvu.getDatos().get(1));
		tv = (TextView) findViewById(R.id.textView3);
		tv.setText(tvu.getDatos().get(2));
		//To-Do
		tv = (TextView) findViewById(R.id.textView5);
		tv.setText("No definido");
	}
	
	
	// Hilo que busca los jugadores mas cercanos del usuario
		public class ThreadVistaUsuario extends Thread {

			String nombre = "";
			String dato;
			ArrayList<String> datos = new ArrayList<String>();

			// Se le pasa el nombre del usuario logeado
			public ThreadVistaUsuario(String nombre) {
				this.nombre = nombre;
			}

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
					//Se obtienen las coordenadas del usuario
					Statement stat = conn.createStatement();
					ResultSet rs = stat
							.executeQuery("SELECT * From Usuario where nombreU = '"+nombre+"'");
					while (rs.next()) {
						datos.add(getImage(rs.getInt("imgPerfil")));
						dato = rs.getString("pais");
						datos.add(dato);
						dato = rs.getString("provincia");
						datos.add(dato);
						dato = String.valueOf(rs.getInt("modalidadJugada"));
					
						datos.add(dato);

					}

				} catch (SQLException e) {
					
				}
				try {
					conn.close();
				} catch (SQLException e) {

				}

			}
			
			public ArrayList<String> getDatos(){
				return datos;
			}
			
			private String getImage(int dato){
				int imgPerfil;
				switch(dato){
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
				return String.valueOf(imgPerfil);
				
			}

		}

}
