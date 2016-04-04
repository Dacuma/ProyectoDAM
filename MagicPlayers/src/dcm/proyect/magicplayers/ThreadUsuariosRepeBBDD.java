package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ThreadUsuariosRepeBBDD extends Thread {
	String nombre = "";
	String passwd = "";
	String email = "";
	boolean bandera = false;

	public ThreadUsuariosRepeBBDD(String nombre, String passwd, String email) {
		this.nombre = nombre;
		this.passwd = passwd;
		this.email = email;
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
			ResultSet rs = stat.executeQuery("SELECT nombreU from usuarios where nombreU='" +nombre+ "';");
			while(rs.next()){
					bandera = true;
			}
			if(!bandera){
				//Encriptamos la contrase�a para aumentar la seguridad.
				passwd = EncriptarPasswd.encriptar(passwd);
				stat.executeUpdate("INSERT INTO `usuarios` VALUES ('" +nombre+ "', '" +passwd+ "', NULL, NULL, NULL,'" +email+ "')");
			}
			rs.close();
		} catch (SQLException e) {
			
		}
		try {
			conn.close();
		} catch (SQLException e) {
			
		}
	
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}

}