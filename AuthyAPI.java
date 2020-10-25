package AuthyJava;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jsoup.Jsoup;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class AuthyAPI {
	public static String ApplicationSecret = "";
	public static String ApplicationName = "";

	static String urlbase = "http://194.127.179.195:8081/";
	public static void main(String[] args) throws IOException {
	}
	public static JsonObject auth() throws JsonSyntaxException, IOException, NoSuchAlgorithmException {
		JsonObject o = new JsonParser().parse(Connect("auth?hwid="+getHWID()+"&name="+ApplicationName)).getAsJsonObject();
		if(o.get("success").getAsBoolean())
		o.addProperty("payload", AES.decrypt(o.get("encryptedpayload").getAsString(), getKey()));
		return o;
	}
	public static JsonObject register(String key, String name) throws JsonSyntaxException, IOException, NoSuchAlgorithmException {
		return new JsonParser().parse(Connect("registercustomer?name="+ApplicationName+"&key="+key+"&customername="+name+"&hwid="+getHWID())).getAsJsonObject();
	}
	public static String getKey() throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
		return Connect("time")+getHWID()+ApplicationSecret;
	}
	public static String Connect(String arguments) throws IOException {
		return (Jsoup.connect(urlbase+arguments).ignoreContentType(true).ignoreHttpErrors(true).execute().body());
	}
	public static String getHWID() throws NoSuchAlgorithmException, UnsupportedEncodingException {

	    String s = "";
	    final String main = System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name").trim();
	    final byte[] bytes = main.getBytes("UTF-8");
	    final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	    final byte[] md5 = messageDigest.digest(bytes);
	    int i = 0;
	    for (final byte b : md5) {
	        s += Integer.toHexString((b & 0xFF) | 0x300).substring(0, 3);
	        if (i != md5.length - 1) {
	            s += "-";
	        }
	        i++;
	    }
	    return getSHA(s);
	}
	public static String getSHA(String input) { try { 
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        byte[] messageDigest = md.digest(input.getBytes()); 
        BigInteger no = new BigInteger(1, messageDigest); 

        // Convert message digest into hex value 
        String hashtext = no.toString(16); 

        while (hashtext.length() < 32) { 
            hashtext = "0" + hashtext; 
        } 

        return hashtext; 
    } catch (NoSuchAlgorithmException e) { 
        System.out.println("Exception thrown"
                           + " for incorrect algorithm: " + e); 

        return null; 
    } 
}
}
