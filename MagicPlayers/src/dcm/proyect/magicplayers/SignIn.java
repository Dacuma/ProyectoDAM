package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void startLogin(View v) {
		Intent i = new Intent(this, Login.class);
		startActivity(i);
	}

	public void registrarUsuario(View v) throws InterruptedException {
		EditText text = (EditText) findViewById(R.id.etNombreUsuarioSI);
		String usuario = text.getText().toString();
		text = (EditText) findViewById(R.id.etPasswordSI);
		String passwd = text.getText().toString();
		text = (EditText) findViewById(R.id.etPassword2SI);
		String passwd2 = text.getText().toString();

		if (usuario.length() == 0 | passwd.length() == 0
				| passwd2.length() == 0) {
			Toast toast = Toast
					.makeText(this, "Por favor, complete todos los campos.",
							Toast.LENGTH_SHORT);
			toast.show();
		} else if (usuario.length() < 4 | usuario.length() > 13) {
			Toast toast = Toast
					.makeText(
							this,
							"El nombre de usuario ha de tener entre 4 y 13 caracteres.",
							Toast.LENGTH_SHORT);
			toast.show();
		} else if (passwd.length() < 6 | passwd.length() > 13) {
			Toast toast = Toast.makeText(this,
					"La contraseña ha de tener entre 6 y 13 caracteres.",
					Toast.LENGTH_SHORT);
			toast.show();
		} else if (!passwd.equals(passwd2)) {
			Toast toast = Toast.makeText(this, "Las contraseñas no coinciden.",
					Toast.LENGTH_SHORT);
			toast.show();
		} else {
			ThreadUsuariosRepeBBDD cDB = new ThreadUsuariosRepeBBDD(usuario, passwd);
			cDB.start();
			cDB.join();
			if(cDB.isBandera()){
				Toast toast = Toast.makeText(this, "El nombre de Usuario ya existe.",
						Toast.LENGTH_SHORT);
				toast.show();
			}else{
				Toast toast = Toast.makeText(this, "Ya estás registrado, logea con tu nombre de usuario y contraseña.",
						Toast.LENGTH_SHORT);
				toast.show();
			}
		}

	}

}
