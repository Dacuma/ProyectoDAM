package dcm.proyect.magicplayers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;

//La actividad principal muestra una im�gen y al tocarla pasa a la 
//ventana de login
public class MainActivity extends Activity{
	
	 // Duraci�n en milisegundos que se mostrar� el splash
    private final int DURACION_SPLASH = 2000; // 2 segundos


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	  super.onCreate(savedInstanceState);
    	  
          // Tenemos una plantilla llamada splash.xml donde mostraremos la informaci�n que queramos (logotipo, etc.)
          setContentView(R.layout.activity_main);
   
          new Handler().postDelayed(new Runnable(){
              public void run(){
  		// Cuando pasen los 2 segundos, pasamos a la actividad principal de la aplicaci�n
          	Intent intent = new Intent(MainActivity.this, Login.class);
          	startActivity(intent);
          	finish();
              }; 
          }, DURACION_SPLASH);
      }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
  }


    