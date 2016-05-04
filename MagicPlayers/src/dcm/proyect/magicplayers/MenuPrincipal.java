package dcm.proyect.magicplayers;

import android.app.Activity;
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
	
	public void lanzarBuscarJugadores(View v){
		Intent i = new Intent(this, BuscarJugadores.class);
		startActivity(i);
	}
}
