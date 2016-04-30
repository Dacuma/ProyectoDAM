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
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;

public class CambiarImagenPerfil extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cambiarimagen);
		manejadorDeRadioButtons();
		ThreadImgPerfil tip = new ThreadImgPerfil();
		tip.start();
		try {
			tip.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int imgSelec = Integer.parseInt(tip.getImagenActual());
		RadioButton rb;
		switch (imgSelec) {
		case 0:
			rb = (RadioButton) findViewById(R.id.rbPerfilSelected0);
			rb.setChecked(true);
			break;
		case 1:
			rb = (RadioButton) findViewById(R.id.rbPerfilSelected1);
			rb.setChecked(true);
			break;
		case 2:
			rb = (RadioButton) findViewById(R.id.rbPerfilSelected2);
			rb.setChecked(true);
			break;
		case 3:
			rb = (RadioButton) findViewById(R.id.rbPerfilSelected3);
			rb.setChecked(true);
			break;
		case 4:
			rb = (RadioButton) findViewById(R.id.rbPerfilSelected4);
			rb.setChecked(true);
			break;
		case 5:
			rb = (RadioButton) findViewById(R.id.rbPerfilSelected5);
			rb.setChecked(true);
			break;
		case 6:
			rb = (RadioButton) findViewById(R.id.rbPerfilSelected6);
			rb.setChecked(true);
			break;
		case 7:
			rb = (RadioButton) findViewById(R.id.rbPerfilSelected7);
			rb.setChecked(true);
			break;
		case 8:
			rb = (RadioButton) findViewById(R.id.rbPerfilSelected8);
			rb.setChecked(true);
			break;
		case 9:
			rb = (RadioButton) findViewById(R.id.rbPerfilSelected9);
			rb.setChecked(true);
			break;
		default:
			rb = (RadioButton) findViewById(R.id.rbPerfilSelected0);
			rb.setChecked(true);
			break;
		}
	}

	// Método que controlará las acciones de los radio buttons
	// Controla que al seleccionar uno se deseleccionen los demás
	public void manejadorDeRadioButtons() {
		final RadioButton mrb0 = (RadioButton) findViewById(R.id.rbPerfilSelected0);
		final RadioButton mrb1 = (RadioButton) findViewById(R.id.rbPerfilSelected1);
		final RadioButton mrb2 = (RadioButton) findViewById(R.id.rbPerfilSelected2);
		final RadioButton mrb3 = (RadioButton) findViewById(R.id.rbPerfilSelected3);
		final RadioButton mrb4 = (RadioButton) findViewById(R.id.rbPerfilSelected4);
		final RadioButton mrb5 = (RadioButton) findViewById(R.id.rbPerfilSelected5);
		final RadioButton mrb6 = (RadioButton) findViewById(R.id.rbPerfilSelected6);
		final RadioButton mrb7 = (RadioButton) findViewById(R.id.rbPerfilSelected7);
		final RadioButton mrb8 = (RadioButton) findViewById(R.id.rbPerfilSelected8);
		final RadioButton mrb9 = (RadioButton) findViewById(R.id.rbPerfilSelected9);

		OnClickListener button0 = new OnClickListener() {
			public void onClick(View v) {
				mrb1.setChecked(false);
				mrb2.setChecked(false);
				mrb3.setChecked(false);
				mrb4.setChecked(false);
				mrb5.setChecked(false);
				mrb6.setChecked(false);
				mrb7.setChecked(false);
				mrb8.setChecked(false);
				mrb9.setChecked(false);
			}
		};
		mrb0.setOnClickListener(button0);

		OnClickListener button1 = new OnClickListener() {
			public void onClick(View v) {
				mrb0.setChecked(false);
				mrb2.setChecked(false);
				mrb3.setChecked(false);
				mrb4.setChecked(false);
				mrb5.setChecked(false);
				mrb6.setChecked(false);
				mrb7.setChecked(false);
				mrb8.setChecked(false);
				mrb9.setChecked(false);
			}
		};
		mrb1.setOnClickListener(button1);

		OnClickListener button2 = new OnClickListener() {
			public void onClick(View v) {
				mrb1.setChecked(false);
				mrb0.setChecked(false);
				mrb3.setChecked(false);
				mrb4.setChecked(false);
				mrb5.setChecked(false);
				mrb6.setChecked(false);
				mrb7.setChecked(false);
				mrb8.setChecked(false);
				mrb9.setChecked(false);
			}
		};
		mrb2.setOnClickListener(button2);

		OnClickListener button3 = new OnClickListener() {
			public void onClick(View v) {
				mrb1.setChecked(false);
				mrb2.setChecked(false);
				mrb0.setChecked(false);
				mrb4.setChecked(false);
				mrb5.setChecked(false);
				mrb6.setChecked(false);
				mrb7.setChecked(false);
				mrb8.setChecked(false);
				mrb9.setChecked(false);
			}
		};
		mrb3.setOnClickListener(button3);

		OnClickListener button4 = new OnClickListener() {
			public void onClick(View v) {
				mrb1.setChecked(false);
				mrb2.setChecked(false);
				mrb3.setChecked(false);
				mrb0.setChecked(false);
				mrb5.setChecked(false);
				mrb6.setChecked(false);
				mrb7.setChecked(false);
				mrb8.setChecked(false);
				mrb9.setChecked(false);
			}
		};
		mrb4.setOnClickListener(button4);

		OnClickListener button5 = new OnClickListener() {
			public void onClick(View v) {
				mrb1.setChecked(false);
				mrb2.setChecked(false);
				mrb3.setChecked(false);
				mrb4.setChecked(false);
				mrb0.setChecked(false);
				mrb6.setChecked(false);
				mrb7.setChecked(false);
				mrb8.setChecked(false);
				mrb9.setChecked(false);
			}
		};
		mrb5.setOnClickListener(button5);

		OnClickListener button6 = new OnClickListener() {
			public void onClick(View v) {
				mrb1.setChecked(false);
				mrb2.setChecked(false);
				mrb3.setChecked(false);
				mrb4.setChecked(false);
				mrb5.setChecked(false);
				mrb0.setChecked(false);
				mrb7.setChecked(false);
				mrb8.setChecked(false);
				mrb9.setChecked(false);
			}
		};
		mrb6.setOnClickListener(button6);

		OnClickListener button7 = new OnClickListener() {
			public void onClick(View v) {
				mrb1.setChecked(false);
				mrb2.setChecked(false);
				mrb3.setChecked(false);
				mrb4.setChecked(false);
				mrb5.setChecked(false);
				mrb6.setChecked(false);
				mrb0.setChecked(false);
				mrb8.setChecked(false);
				mrb9.setChecked(false);
			}
		};
		mrb7.setOnClickListener(button7);

		OnClickListener button8 = new OnClickListener() {
			public void onClick(View v) {
				mrb1.setChecked(false);
				mrb2.setChecked(false);
				mrb3.setChecked(false);
				mrb4.setChecked(false);
				mrb5.setChecked(false);
				mrb6.setChecked(false);
				mrb7.setChecked(false);
				mrb0.setChecked(false);
				mrb9.setChecked(false);
			}
		};
		mrb8.setOnClickListener(button8);

		OnClickListener button9 = new OnClickListener() {
			public void onClick(View v) {
				mrb1.setChecked(false);
				mrb2.setChecked(false);
				mrb3.setChecked(false);
				mrb4.setChecked(false);
				mrb5.setChecked(false);
				mrb6.setChecked(false);
				mrb7.setChecked(false);
				mrb8.setChecked(false);
				mrb0.setChecked(false);
			}
		};
		mrb9.setOnClickListener(button9);

	}
	
	public void volverConfPerfil(View v) {
		Intent i = new Intent(this, ConfigurarPerfil.class);
		startActivity(i);
	}
	
	public void guardarImagenBBDD(View v) throws InterruptedException{
		RadioButton rb;
		String img = "";
		rb = (RadioButton) findViewById(R.id.rbPerfilSelected0);
		if(rb.isChecked()){
			img = "0";
		}
		rb = (RadioButton) findViewById(R.id.rbPerfilSelected1);
		if(rb.isChecked()){
			img = "1";
		}
		rb = (RadioButton) findViewById(R.id.rbPerfilSelected2);
		if(rb.isChecked()){
			img = "2";
		}
		rb = (RadioButton) findViewById(R.id.rbPerfilSelected3);
		if(rb.isChecked()){
			img = "3";
		}
		rb = (RadioButton) findViewById(R.id.rbPerfilSelected4);
		if(rb.isChecked()){
			img = "4";
		}
		rb = (RadioButton) findViewById(R.id.rbPerfilSelected5);
		if(rb.isChecked()){
			img = "5";
		}
		rb = (RadioButton) findViewById(R.id.rbPerfilSelected6);
		if(rb.isChecked()){
			img = "6";
		}
		rb = (RadioButton) findViewById(R.id.rbPerfilSelected7);
		if(rb.isChecked()){
			img = "7";
		}
		rb = (RadioButton) findViewById(R.id.rbPerfilSelected8);
		if(rb.isChecked()){
			img = "8";
		}
		rb = (RadioButton) findViewById(R.id.rbPerfilSelected9);
		if(rb.isChecked()){
			img = "9";
		}
		
		ThreadGuardarImagen tgi = new ThreadGuardarImagen(img);
		tgi.start();
		tgi.join();
		
		Toast.makeText(this, "Imágen cambiada con éxito!", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, ConfigurarPerfil.class);
		startActivity(i);
		
	}

	// Hilo que recoge la imágen actual de la bbdd
	public class ThreadImgPerfil extends Thread {
		String nombre = Login.nombreUsuario;
		String imagenActual = "0";

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
						.executeQuery("SELECT imgPerfil from Usuario where nombreU='"
								+ nombre + "';");
				while (rs.next()) {
					imagenActual = rs.getString("imgPerfil");
				}
				rs.close();
			} catch (SQLException e) {

			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}

		public String getImagenActual() {
			return imagenActual;
		}

		public void setImagenActual(String imagenActual) {
			this.imagenActual = imagenActual;
		}

	}
	
	public class ThreadGuardarImagen extends Thread {
		String img;
	public ThreadGuardarImagen(String img) {
		this.img= img;
	}

	// Almacena los valores en la bbdd
	public void run() {
		String consulta = "UPDATE Usuario SET imgPerfil = "
				+ img + " WHERE nombreU = '"
				+ Login.nombreUsuario + "'";

		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					ConexionesDB.serverDB,
					ConexionesDB.usuarioDB, ConexionesDB.passDB);
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
