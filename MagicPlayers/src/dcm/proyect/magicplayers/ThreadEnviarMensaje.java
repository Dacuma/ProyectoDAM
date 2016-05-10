package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Thread que envía el mensaje
public class ThreadEnviarMensaje extends Thread {

	String emisor = "";
	String asunto = "";
	String mensaje = "";
	String receptor = "";
	int ultimoId = 0;

	public ThreadEnviarMensaje(String emisor, String asunto, String mensaje, String receptor) {
		this.emisor = emisor;
		this.asunto = asunto;
		this.mensaje = mensaje;
		this.receptor = receptor;
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
			//Obtenemos el último id
			ResultSet rs = stat
					.executeQuery("SELECT idMensaje from Mensaje order by idMensaje desc limit 1;");
			while (rs.next()) {
				ultimoId = rs.getInt("idMensaje");
			}
			rs.close();
			//Actualizamos el ID e insertamos el mensaje en la bbdd
			ultimoId ++;
			stat.executeUpdate("Insert into Mensaje values ("+ultimoId+",'"+emisor+"','"+asunto+"','"+mensaje+"','"+receptor+"',0);");

		} catch (SQLException e) {

		}
		try {
			conn.close();
		} catch (SQLException e) {

		}

	}

}
