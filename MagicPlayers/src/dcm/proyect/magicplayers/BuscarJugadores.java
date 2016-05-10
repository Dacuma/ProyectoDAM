package dcm.proyect.magicplayers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BuscarJugadores extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buscarjugadores);
	}

	public void lanzarVolverMP(View v) {
		Intent i = new Intent(this, MenuPrincipal.class);
		startActivity(i);
	}

	public void lanzarBuscarUsuarios(View v) {
		String consulta = "where nombreU != '"+Login.nombreUsuario+"' ";
		boolean bandera = true;
		EditText et1 = (EditText) findViewById(R.id.etNombreBJ);
		if (et1.getText().toString().length() > 0) {
			consulta = consulta + "and nombreU LIKE '%" + et1.getText().toString()
					+ "%' ";
		}
		// Pais
		CheckBox cb1 = (CheckBox) findViewById(R.id.cbBuscarPais);
		if (cb1.isChecked()) {
			Spinner spnrPais = (Spinner) findViewById(R.id.spnPaisBJ);
			consulta = consulta + "and pais = '"
					+ spnrPais.getSelectedItem().toString() + "' ";
		}
		// Provincia
		CheckBox cb2 = (CheckBox) findViewById(R.id.cbBuscarProvincia);
		if (cb2.isChecked()) {
			Spinner spnrPais = (Spinner) findViewById(R.id.spnProvinciaBJ);
			consulta = consulta + "and provincia = '"
					+ spnrPais.getSelectedItem().toString() + "' ";
		}
		// Código Postal
		EditText et2 = (EditText) findViewById(R.id.etCPBJ);
		if (et2.getText().toString().length() > 0) {
			if (!Validaciones.validarCP(et2.getText().toString())) {
				Toast.makeText(this,
						"El código postal introducido es incorrecto",
						Toast.LENGTH_SHORT).show();
				bandera = false;
			} else {
				consulta = consulta + "and codigoPostal = '"
						+ et2.getText().toString() + "' ";
			}

		}
		// Modalidad Jugada
		// Where modalidadJugada like '%%';
		CheckBox cb5 = (CheckBox) findViewById(R.id.cbVintageBJ);
		if (cb5.isChecked()) {

			consulta = consulta + "and modalidadJugada LIKE '%0%'";

		}

		CheckBox cb6 = (CheckBox) findViewById(R.id.cbLegacyBJ);
		if (cb6.isChecked()) {
			consulta = consulta + "and modalidadJugada LIKE '%1%'";

		}

		CheckBox cb7 = (CheckBox) findViewById(R.id.cbModernBJ);
		if (cb7.isChecked()) {
			consulta = consulta + "and modalidadJugada LIKE '%2%'";
		}

		CheckBox cb8 = (CheckBox) findViewById(R.id.cbStandardBJ);
		if (cb8.isChecked()) {
			consulta = consulta + "and modalidadJugada LIKE '%3%'";
		}

		CheckBox cb9 = (CheckBox) findViewById(R.id.cbPauperBJ);
		if (cb9.isChecked()) {
			consulta = consulta + "and modalidadJugada LIKE '%4%'";
		}

		CheckBox cb10 = (CheckBox) findViewById(R.id.cbCommanderBJ);
		if (cb10.isChecked()) {
			consulta = consulta + "and modalidadJugada LIKE '%5%'";
		}

		CheckBox cb11 = (CheckBox) findViewById(R.id.cbCasualBJ);
		if (cb11.isChecked()) {
			consulta = consulta + "and modalidadJugada LIKE '%6%'";
		}

		CheckBox cb12 = (CheckBox) findViewById(R.id.cbOtrosBJ);
		if (cb12.isChecked()) {
			consulta = consulta + "and modalidadJugada LIKE '%7%'";
		}

		// Distancia
		CheckBox cb3 = (CheckBox) findViewById(R.id.cbMayorQueBJ);
		EditText mayorQue = (EditText) findViewById(R.id.etDistanciaMayorBJ);
		CheckBox cb4 = (CheckBox) findViewById(R.id.cbMenorQueBJ);
		EditText menorQue = (EditText) findViewById(R.id.etDistanciaMenorBJ);
		if (cb3.isChecked() && mayorQue.getText().toString().length() > 0) {
			consulta = consulta + "having distancia*100 >"
					+ mayorQue.getText().toString() + " ";
			if (cb4.isChecked() && menorQue.getText().toString().length() > 0) {
				consulta = consulta + "and distancia*100 <"
						+ menorQue.getText().toString() + " ";
			} else if (cb4.isChecked()
					&& menorQue.getText().toString().length() > 0) {
				consulta = consulta + "having distancia*100 <"
						+ menorQue.getText().toString() + " ";
			}
		}
		if (bandera) {
			Intent i = new Intent(this, JugadoresBuscados.class);
			i.putExtra("consulta", consulta);
			startActivity(i);
		}

	}
}