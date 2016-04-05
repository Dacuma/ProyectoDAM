package dcm.proyect.magicplayers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //Evito que se pueda volver a atrás.
    @Override
    public void onBackPressed() {
        super.finish();
    return;
    }
    
    public void startSignUp(View v){
    	Intent i = new Intent(this, SignUp.class);
    	startActivity(i);
    }
    
    public void startMenuPrincipal(View V) throws InterruptedException{
    	//Obtiene el usuario y la contraseña de la ventana de login
    	EditText text = (EditText) findViewById(R.id.etUsername);
		String usuario = text.getText().toString();
		text = (EditText) findViewById(R.id.etPassword);
		String passwd = text.getText().toString();
		//Hilo que comprueba si el usuario y la contraseña son correctos
    	ThreadLogin tl = new ThreadLogin(usuario,passwd);
    	//Elimina el texto del campo contraseña, deja el nombre de usuario
    	text.setText("");
    	
    	//Se ejecuta el hilo ThreadLogin
    	tl.start();
    	tl.join();
    	//isBandera devuelve true si el login es correcto, false si no.
    	if(tl.isBandera()){
    		Toast toast = Toast
					.makeText(this, "Conectado!",
							Toast.LENGTH_SHORT);
			toast.show();
    		Intent i = new Intent(this, MenuPrincipal.class);
        	startActivity(i);
    	}else{
    		Toast toast = Toast
					.makeText(this, "Error en nombre de usuario o contraseña.",
							Toast.LENGTH_SHORT);
			toast.show(); 
    	}
    }

}
