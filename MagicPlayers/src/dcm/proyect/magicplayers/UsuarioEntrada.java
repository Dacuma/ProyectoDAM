package dcm.proyect.magicplayers;

//Clase que adaptara los datos para el ListView de de mostrarusuarios.xml
public class UsuarioEntrada {
	private int idImagen; 
	private String textoEncima; 
	private String textoDebajo; 
	private String textoDerecha;
	String modalidadJugada;

	public UsuarioEntrada(int idImagen, String textoEncima, String textoDebajo,  String textoDerecha, String modalidadJugada) { 
	    this.idImagen = idImagen; 
	    this.textoEncima = textoEncima; 
	    this.textoDebajo = textoDebajo; 
	    this.textoDerecha =  textoDerecha;
	    this.modalidadJugada = modalidadJugada;
	}

	public String get_textoEncima() { 
	    return textoEncima; 
	}
	
	public String get_textoDerecha() { 
	    return textoDerecha; 
	}

	public String get_textoDebajo() { 
	    return textoDebajo; 
	}

	public int get_idImagen() {
	    return idImagen; 
	} 
	}