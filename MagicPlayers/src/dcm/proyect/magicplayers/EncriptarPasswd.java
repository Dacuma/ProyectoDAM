package dcm.proyect.magicplayers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncriptarPasswd {
	private static final char[] CONSTS_HEX = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	//Método que encripta texto con un algoritmo irreversible
	public static String encriptar(String passwd) {

		try {
			MessageDigest msgd = MessageDigest.getInstance("MD5");
			byte[] bytes = msgd.digest(passwd.getBytes());
			StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
			for (int i = 0; i < bytes.length; i++) {
				int bajo = (int) (bytes[i] & 0x0f);
				int alto = (int) ((bytes[i] & 0xf0) >> 4);
				strbCadenaMD5.append(CONSTS_HEX[alto]);
				strbCadenaMD5.append(CONSTS_HEX[bajo]);
			}
			return strbCadenaMD5.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}
