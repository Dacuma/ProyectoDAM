package dcm.proyect.magicplayers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EnviarMensaje extends Activity{
	String usuarioReceptor="";
	String usuarioEmisor="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mensajesusuarios);
		Bundle extras = getIntent().getExtras(); 
		usuarioReceptor = extras.getString("receptor");
		usuarioEmisor = Login.nombreUsuario;
		TextView tvEmisor = (TextView) findViewById(R.id.tvUsuarioEmisor);
		TextView tvReceptor = (TextView) findViewById(R.id.tvUsuarioReceptor);
		tvEmisor.setText("De: " +usuarioEmisor);
		tvReceptor.setText("Para: " +usuarioReceptor);

	}
	
	public void volver(View v){
		onBackPressed();
	}
	
	public void enviarMensaje(View v){
		String asunto = "";
		String mensaje = "";
		EditText etAsunto = (EditText) findViewById(R.id.etAsunto);
		asunto = etAsunto.getText().toString();
		EditText etMensaje = (EditText) findViewById(R.id.etMensaje);
		mensaje = etMensaje.getText().toString();
		ThreadEnviarMensaje tem = new ThreadEnviarMensaje(usuarioEmisor,asunto,mensaje,usuarioReceptor);
		tem.start();
		try {
			tem.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
		dlgAlert.setMessage("El mensaje se ha enviado correctamente!");
		dlgAlert.setTitle("Enviado");
		dlgAlert.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {     
	    		onBackPressed();
	          }
	      });
		dlgAlert.setCancelable(false);
		dlgAlert.create().show();
	}
}
