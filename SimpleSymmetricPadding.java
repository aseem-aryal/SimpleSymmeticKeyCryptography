import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
  import java.util.Scanner;

public class SimpleSymmetricPadding{

	public static String digits = "aseemaryal123456";

	public static void main(String args[]) throws Exception{

		byte[] input = new byte[]{
					0x00,0x11,0x22,0x33,0x44,0x55,0x66,0x77,
					0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f,
					0x12,0x15,0x16,0x15,0x23,0x51,0x56,0x71			
					};
		byte[] keyBytes = new byte[]{
					0x00,0x11,0x22,0x33,0x44,0x55,0x66,0x07,
					0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x2e,0x2f,
					0x12,0x15,0x16,0x15,0x23,0x51,0x56,0x71			
					};
		while (true){
			System.out.println("Enter word to encrypt");
			Scanner scan= new Scanner(System.in);		
			//For string
			String text= scan.nextLine();
			byte[] input_text = text.getBytes(); 
			SecretKeySpec key = new SecretKeySpec(keyBytes,"AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding","BC");
			//System.out.println("Input: " + new String(input_text));
			cipher.init(Cipher.ENCRYPT_MODE,key);
			byte[] cipherText= new byte[cipher.getOutputSize(input_text.length)];
			int ctLength = cipher.update(input_text,0,input_text.length,cipherText,5); 
			ctLength += cipher.doFinal(cipherText,ctLength);
			System.out.println("Cipher:"+new String(cipherText) +" bytes:"+ctLength);
			cipher.init(Cipher.DECRYPT_MODE,key);
			byte[] plainText= new byte[cipher.getOutputSize(ctLength)];
			int ptLength = cipher.update(cipherText,0,ctLength,plainText,0); 
			ptLength += cipher.doFinal(plainText,ptLength);
			//System.out.println("Plain:"+new String(plainText) +" bytes:"+ptLength);
		}	
		
	}





	
}
