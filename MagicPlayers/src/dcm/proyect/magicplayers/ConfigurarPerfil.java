package dcm.proyect.magicplayers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ConfigurarPerfil extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configurarperfil);
	}
	
	public void volverMenuPrincipal(View v){
		Intent i = new Intent(this, MenuPrincipal.class);
		startActivity(i);
	}
}
