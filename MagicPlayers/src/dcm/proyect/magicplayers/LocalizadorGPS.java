package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

//Clase que obtiene las coordenadas de ubicación del dispositivo.
//NOTA: HAY QUE AGREGAR PERMISOS EN EL ANDROID MANIFEST.
public class LocalizadorGPS implements LocationListener {
	static final double metrosXGrado = 111.225;
	private Context context;
	// Manejador de la localización
	LocationManager locationManager;
	// Proveedor de la localización
	String proveedor;
	// Boolean que determina si el proveedor esta activo
	private boolean redOn;
	String latitud = "";
	String longitud = "";

	public LocalizadorGPS(Context contex) {
		this.context = contex;
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		proveedor = LocationManager.NETWORK_PROVIDER;
		redOn = locationManager.isProviderEnabled(proveedor);
		locationManager.requestLocationUpdates(proveedor, 1000, 1, this);
		getLocalizacion();
	}

	// Método con el que obtengo la localización.
	private void getLocalizacion() {
		if (redOn) {
			Location lc = locationManager.getLastKnownLocation(proveedor);
			if (lc != null) {
				StringBuilder builder = new StringBuilder();
				builder.append(lc.getLatitude());
				latitud = builder.toString();
				builder.setLength(0);
				builder.append(lc.getLongitude());
				longitud = builder.toString();
				ThreadLocalizacion tl = new ThreadLocalizacion(latitud,
						longitud);
				tl.start();
			}
		}
	}

	// Este método se activa cada vez que cambia la localización.
	@Override
	public void onLocationChanged(Location loc) {
	}

	// Si el proveedor se desactiva...
	@Override
	public void onProviderDisabled(String provider) {

	}

	// Si el proveedor se activa...
	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	// Método que calcula distancia entre dos coordenadas GPS, devuelve un
	// double. Para calcularla se usa el teorema de pitágoras, que aunque no es
	// muy preciso debido a que la tierra es redonda, nos sirve para lo que nos
	// proponemos.
	public double calcularDistancia(String lat1, String long1, String lat2,
			String long2) {
		double distancia = 0;
		double latitud1 = 0;
		double longitud1 = 0;
		double latitud2 = 0;
		double longitud2 = 0;
		try {
			latitud1 = Double.parseDouble(lat1);
			longitud1 = Double.parseDouble(long1);
			latitud2 = Double.parseDouble(lat2);
			longitud2 = Double.parseDouble(long2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		distancia = Math.sqrt((Math.pow((latitud1 - latitud2), 2) + Math.pow(
				(longitud1 - longitud2), 2)));
		// Distancia en grados de latitud y longitud.
		distancia = Math.abs(distancia);
		// 1 grado es aprox 111,225km, por lo que obtenemos los kilómetros con:
		distancia = Math.round(distancia * metrosXGrado);
		return distancia;
	}

	// Hilo que actualizará las coordenadas en la bbdd.
	public class ThreadLocalizacion extends Thread {
		String latitud = "";
		String longitud = "";
		boolean bandera = false;

		public ThreadLocalizacion(String latitud, String longitud) {
			this.latitud = latitud;
			this.longitud = longitud;
		}

		@Override
		public void run() {
			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(ConexionesDB.serverDB,
						ConexionesDB.usuarioDB, ConexionesDB.passDB);
			} catch (SQLException se) {

			} catch (ClassNotFoundException e) {

			} catch (Exception e) {

			}

			try {
				Statement stat = conn.createStatement();
				stat.executeUpdate("UPDATE Usuario SET latitud = '" + latitud
						+ "', longitud = '" + longitud + "' WHERE nombreU = '"
						+ Login.nombreUsuario + "';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}
	}
}