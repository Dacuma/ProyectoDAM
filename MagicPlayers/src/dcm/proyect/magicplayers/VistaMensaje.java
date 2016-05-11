package dcm.proyect.magicplayers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

//Clase para ver los mensajes de los usuarios.
public class VistaMensaje extends Activity {
	String receptorAlResponder = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vistamensaje);
		Bundle bundle = getIntent().getExtras();
		String emisor = bundle.getString("emisor");
		//Si respondemos, lo haremos al que emitió el mensaje
		receptorAlResponder = emisor;
		String asunto = bundle.getString("asunto");
		String mensaje = bundle.getString("mensaje");
		TextView tvEmisor = (TextView) findViewById(R.id.tvEmisorVM);
		TextView tvAsunto = (TextView) findViewById(R.id.tvAsuntoVM);
		TextView tvMensaje = (TextView) findViewById(R.id.tvMensajeVM);
		tvEmisor.setText("De: " +emisor);
		tvAsunto.setText("Asunto: " +asunto);
		tvMensaje.setText(mensaje);
	}
	
	public void volverAMensajes(View v){
		Intent i = new Intent(this, CentroDeMensajes.class);
		startActivity(i);
	}
	
	public void responder(View v){
		Intent i = new Intent(this, EnviarMensaje.class);
		i.putExtra("receptor", receptorAlResponder);
		startActivity(i);
	}
}
