package dcm.proyect.magicplayers;

public class TorneoEntrada {
	private String textoNombreTorneo; 
	private String textoFormatoTorneo; 
	private String textoCiudadTorneo;
	private String textoPrecioTorneo;
	int id;

	public TorneoEntrada(String textoNombreTorneo, String textoFormatoTorneo,  String textoCiudadTorneo, String textoPrecioTorneo, int id) { 
	    this.textoNombreTorneo = textoNombreTorneo; 
	    this.textoFormatoTorneo = textoFormatoTorneo; 
	    this.textoCiudadTorneo = textoCiudadTorneo; 
	    this.textoPrecioTorneo =  textoPrecioTorneo;
	    this.id = id;
	}

	public String getTextoNombreTorneo() {
		return textoNombreTorneo;
	}

	public String getTextoFormatoTorneo() {
		return textoFormatoTorneo;
	}

	public String getTextoCiudadTorneo() {
		return textoCiudadTorneo;
	}

	public String getTextoPrecioTorneo() {
		return textoPrecioTorneo;
	}
	
	public int getID() {
		return id;
	}

	
	}