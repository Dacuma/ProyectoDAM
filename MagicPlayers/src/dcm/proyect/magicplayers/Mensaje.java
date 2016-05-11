package dcm.proyect.magicplayers;

public class Mensaje {
	int idMensaje;
	String emisor;
	String asunto;
	String mensaje;
	String receptor;
	public Mensaje(int idMensaje, String emisor, String asunto, String mensaje,
			String receptor) {
		this.idMensaje = idMensaje;
		this.emisor = emisor;
		this.asunto = asunto;
		this.mensaje = mensaje;
		this.receptor = receptor;
	}
	public int getIdMensaje() {
		return idMensaje;
	}
	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getReceptor() {
		return receptor;
	}
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}
	
	

}
