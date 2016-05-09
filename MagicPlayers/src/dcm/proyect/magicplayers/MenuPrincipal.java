package dcm.proyect.magicplayers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

//To-Do
public class MenuPrincipal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuprincipal);
		// Cada vez que se accede por primera vez a la aplicación se guardan las
		// coordenadas en la
		// base de datos.
		LocalizadorGPS loc = new LocalizadorGPS(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void lanzarJugadoresCercanos(View v){
		Intent i = new Intent(this, JugadoresCercanos.class);
		startActivity(i);
	}
	
	public void lanzarConfigurarPefil(View v){
		Intent i = new Intent(this, ConfigurarPerfil.class);
		startActivity(i);
	}
	
	public void lanzarTorneosBuscados(View v){
		Intent i = new Intent(this, TorneosBuscados.class);
		startActivity(i);
	}
	
	public void lanzarBuscarJugadores(View v){
		Intent i = new Intent(this, BuscarJugadores.class);
		startActivity(i);
	}
	
	public void lanzarAltaTorneo(View v){
		Intent i = new Intent(this, AltaTorneo.class);
		startActivity(i);
	}
	
	public void salirAplicación(View v){
		Intent i = new Intent(this, Login.class);
		startActivity(i);
	}
	
	@Override
	public void onBackPressed() {
		alertaCerrarSesion();		
	}
	
	public void alertaCerrarSesion(){
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
		dlgAlert.setMessage("¿Desea Salir?");
		dlgAlert.setTitle("Salir");
		dlgAlert.setPositiveButton("Si",new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	Login.contrasenaUsuario="";
	    		Login.nombreUsuario="";
	        	salirAplicación(null); 	        	
	          }
	      });
		dlgAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            //dismiss the dialog  
	          }
	      });
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}
}
