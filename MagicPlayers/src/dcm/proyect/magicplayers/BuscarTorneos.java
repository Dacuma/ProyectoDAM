package dcm.proyect.magicplayers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BuscarTorneos extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buscartorneos);
	}

	public void lanzarVolverMP(View v) {
		Intent i = new Intent(this, MenuPrincipal.class);
		startActivity(i);
	}

	public void lanzarBuscarTorneos(View v) {
		String consulta = "";
		boolean bandera = true;
		// Nombre Torneo
		EditText et1 = (EditText) findViewById(R.id.etNombreTorneoBT);
		if (et1.getText().toString().length() > 0) {
			consulta = consulta + "and nombreTorneo LIKE '%"
					+ et1.getText().toString() + "%' ";
		}
		// Pais
		CheckBox cb1 = (CheckBox) findViewById(R.id.cbBuscarProvinciaBT);
		if (cb1.isChecked()) {
			Spinner spnrProvincia = (Spinner) findViewById(R.id.spnProvinciaBT);
			consulta = consulta + "and provincia = '"
					+ spnrProvincia.getSelectedItem().toString() + "' ";
		}
		// Fecha
		CheckBox cb2 = (CheckBox) findViewById(R.id.cbBuscarFechaBT);
		if (cb2.isChecked()) {
			DatePicker dpFecha = (DatePicker) findViewById(R.id.dpATFechaBT);
			// Formateo la fecha a xx/xx/xxxx
			String fecha = Integer.toString(dpFecha.getDayOfMonth()) + "/"
					+ Integer.toString(dpFecha.getMonth()) + "/"
					+ Integer.toString(dpFecha.getYear());
			consulta = consulta + "and fecha = '" + fecha + "' ";
		}

		// Precio
		CheckBox cb3 = (CheckBox) findViewById(R.id.cbMayorQueBT);
		EditText mayorQue = (EditText) findViewById(R.id.etPrecioMayorBT);
		CheckBox cb4 = (CheckBox) findViewById(R.id.cbMenorQueBT);
		EditText menorQue = (EditText) findViewById(R.id.etPrecioMenorBT);
		if (cb3.isChecked() && mayorQue.getText().toString().length() > 0) {
			consulta = consulta + "and precio >"
					+ mayorQue.getText().toString() + " ";
			if (cb4.isChecked() && menorQue.getText().toString().length()>0) {
				consulta = consulta + "and precio <"
						+ menorQue.getText().toString() + " ";
			} 
		}else if (cb4.isChecked()
					/*&& menorQue.getText().toString().length() > 0*/) {

				Toast.makeText(this, menorQue.getText().toString(), Toast.LENGTH_LONG).show();
				consulta = consulta + "and precio <"
						+ menorQue.getText().toString() + " ";
		}

		// Modalidad Jugada
		// Where modalidadJugada like '%%';
		CheckBox cb5 = (CheckBox) findViewById(R.id.cbformatoJugadoBT);
		if (cb5.isChecked()) {
			Spinner spnFormato = (Spinner) findViewById(R.id.spnFormatoBT);
			String formato = spnFormato.getSelectedItem().toString();
			consulta = consulta + "and formato = '"+formato+"'";
		}
		
		if (bandera) {
			Intent i = new Intent(this, TorneosBuscados.class);
			i.putExtra("consulta", consulta);
			startActivity(i);
		}

	}
}
