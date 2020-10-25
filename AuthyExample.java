package AuthyJava;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.UnsupportedLookAndFeelException;

import com.google.gson.JsonSyntaxException;

public class AuthyExample {

	public static void main(String[] args) throws JsonSyntaxException, NoSuchAlgorithmException, IOException, UnsupportedLookAndFeelException, InterruptedException {
		Authy Example = new Authy("TestApp","7D860F33S25G");
		Example.Initialize();
		System.out.println("Customer name == "+Example.getname());
		System.out.println("Payload == "+Example.getpayload());

	}

}
