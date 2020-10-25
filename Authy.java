package AuthyJava;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class Authy {
	private String ApplicationName = "";
	private String ApplicationSecret = "";
	public String name = "";
	public String payload = "";

	public Authy(String Appname,String AppSecret) {this.ApplicationName=Appname;this.ApplicationSecret=AppSecret;AuthyAPI.ApplicationName=this.ApplicationName;AuthyAPI.ApplicationSecret=this.ApplicationSecret;}
	public void Initialize() throws JsonSyntaxException, NoSuchAlgorithmException, IOException, UnsupportedLookAndFeelException, InterruptedException {
        javax.swing.UIManager.setLookAndFeel(new FlatDarkLaf());
		JsonObject o =AuthyAPI.auth();
		while(!o.get("success").getAsBoolean()) {
			JOptionPane.showMessageDialog(null, o.get("message").getAsString(), "Failed!", JOptionPane.INFORMATION_MESSAGE);
			AuthyGUI a = new AuthyGUI(ApplicationName,ApplicationSecret);
			a.setVisible(true);
			while(a.isDisplayable()) TimeUnit.SECONDS.sleep(1);
			o =AuthyAPI.auth();
		}
		this.payload = o.get("payload").getAsString();
		this.name = o.get("name").getAsString();
	}
	
	public String getname() {return this.name;}
	public String getpayload() {return this.payload;}

}
