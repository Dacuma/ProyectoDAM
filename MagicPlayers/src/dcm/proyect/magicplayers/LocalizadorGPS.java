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
	private Context context;
	//Manejador de la localización
	LocationManager locationManager;
	//Proveedor de la localización
	String proveedor;
	//Boolean que determina si el proveedor esta activo
	private boolean redOn;
	String latitud = "";
	String longitud = "";

	public LocalizadorGPS(Context contex) {
		this.context = contex;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		proveedor = LocationManager.NETWORK_PROVIDER;
		redOn = locationManager.isProviderEnabled(proveedor);
		locationManager.requestLocationUpdates(proveedor,1000,1,this);
		getLocalizacion();
	}
	
	//Método con el que obtengo la localización.
	private void getLocalizacion(){
		if(redOn){
			Location lc = locationManager.getLastKnownLocation(proveedor);
			if(lc != null){
				StringBuilder builder = new StringBuilder();
				builder.append(lc.getLatitude());
				latitud = builder.toString();
				builder.setLength(0);
				builder.append(lc.getLongitude());
				longitud = builder.toString();
				ThreadLocalizacion tl = new ThreadLocalizacion(latitud,longitud);
				tl.start();
			}
		}
	}

	//Este método se activa cada vez que cambia la localización.
	@Override
	public void onLocationChanged(Location loc) {
	}

	//Si el proveedor se desactiva...
	@Override
	public void onProviderDisabled(String provider) {

	}

	//Si el proveedor se activa...
	@Override
	public void onProviderEnabled(String provider) {

	}

	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}
	
	//Hilo que actualizará las coordenadas en la bbdd.
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
				conn = DriverManager.getConnection(
						"jdbc:mysql://db4free.net:3306/magicplayers",
						"dcuellar", "QAZwsx123");
			} catch (SQLException se) {
				
			} catch (ClassNotFoundException e) {
				
			} catch (Exception e) {

			}

			try {
				Statement stat = conn
						.createStatement();
				stat.executeUpdate("UPDATE Usuario SET latitud = '"+latitud+"', longitud = '"+longitud+"' WHERE nombreU = '"+Login.nombreUsuario+"';");
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