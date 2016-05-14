package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class VerPerfil extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vistaperfil);
		ThreadVerMisDatos tvmd = new ThreadVerMisDatos();
		tvmd.start();
		try {
			tvmd.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ImageView iv = (ImageView)findViewById(R.id.ivMiImagen);
		iv.setImageResource(tvmd.getImgPerfil());
		TextView tvNombre = (TextView) findViewById(R.id.tvMiNombre);
		tvNombre.setText("Nombre: " +tvmd.getNombre());
		TextView tvPais = (TextView) findViewById(R.id.tvMiPais);
		tvPais.setText("Pais: " +tvmd.getPais());
		TextView tvProvincia = (TextView) findViewById(R.id.tvMiProvincia);
		tvProvincia.setText("Provincia: " +tvmd.getProvincia());
		TextView tvDCI = (TextView) findViewById(R.id.tvMiDCI);
		tvDCI.setText("DCI: " +tvmd.getDCI());
		
	}

	// Hilo que obtiene algunos datos del usuario conectado
	public class ThreadVerMisDatos extends Thread {

		String nombre = "";
		String pais = "";
		String provincia = "";
		String DCI = "";
		int imgPerfil = 0;

		@Override
		public void run() {
			Connection conn = null;
			String latitud = "";
			String longitud = "";
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(ConexionesDB.serverDB,
						ConexionesDB.usuarioDB, ConexionesDB.passDB);
			} catch (SQLException se) {

			} catch (ClassNotFoundException e) {

			} catch (Exception e) {

			}

			try {
				// Se obtienen las coordenadas del usuario
				Statement stat = conn.createStatement();
				ResultSet rs = stat
						.executeQuery("SELECT * from Usuario where nombreU='"
								+ Login.nombreUsuario + "';");
				while (rs.next()) {
					nombre = rs.getString("nombreU");
					pais = rs.getString("pais");
					provincia = rs.getString("provincia");
					DCI = rs.getString("numeroDCI");
					imgPerfil = rs.getInt("imgPerfil");
				}
				
				if(DCI.length()<5){
					DCI = "No Definido";
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

		public String getPais() {
			return pais;
		}

		public String getProvincia() {
			return provincia;
		}

		public String getDCI() {
			return DCI;
		}

		public int getImgPerfil() {
			return imgPerfil;
		}

		

	}
}
