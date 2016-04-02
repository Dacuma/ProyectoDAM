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
    
    public void startSignIn(View v){
    	Intent i = new Intent(this, SignIn.class);
    	startActivity(i);
    }
    
    public void startMenuPrincipal(View V) throws InterruptedException{
    	EditText text = (EditText) findViewById(R.id.etUsername);
		String usuario = text.getText().toString();
		text = (EditText) findViewById(R.id.etPassword);
		String passwd = text.getText().toString();
    	ThreadLogin tl = new ThreadLogin(usuario,passwd);
    	tl.start();
    	tl.join();
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
