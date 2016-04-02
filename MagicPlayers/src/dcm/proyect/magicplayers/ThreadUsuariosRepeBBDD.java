package dcm.proyect.magicplayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ThreadUsuariosRepeBBDD extends Thread {
	String nombre = "";
	String passwd = "";
	boolean bandera = false;

	public ThreadUsuariosRepeBBDD(String nombre, String passwd) {
		this.nombre = nombre;
		this.passwd = passwd;
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
			ResultSet rs = stat.executeQuery("SELECT nombreU from usuarios");
			while(rs.next()){
				if(nombre.equals(rs.getString("nombreU"))){
					bandera = true;
				}
			}
			if(!bandera){
				stat.executeUpdate("INSERT INTO `usuarios` VALUES ('" +nombre+ "', '" +passwd+ "', NULL, NULL, NULL)");
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
