//package chapter2;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
  import java.util.Scanner;

public class SimpleSymmetric{
	public static String digits = "aseemaryal123456";

	private static String toPadding(String text){
		int n = 16-text.length();
	 	while (n!=0){
			text = text.concat("3");
			n= n-1;		
		}	
		return text;
	}

	private static String toNoPadding(String text,int pin_length){
		String inputText= text;	 	
		text = text.substring(0,pin_length);
		String paddingText = inputText.substring(pin_length,inputText.length());
		int n = pin_length;
		for (int i=0;i<=paddingText.length()-1;i++){
			if (paddingText.charAt(i) != '3'){
				System.out.println("Cracked at position:"+ i + " with character: "+paddingText.charAt(i));
			}					
		}
		return text;
	}


	public static void main(String[] args) throws Exception{
		byte[] input = new byte[] {0x00,0x11,0x22,0x33,0x44,0x55,0x66,0x77,
					(byte)0x88,(byte)0x99,(byte)0xaa,(byte)0xbb,
					(byte)0xcc,(byte)0xdd,(byte)0xee,(byte)0xff};
		byte[] keyBytes =new byte[]{0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,
					0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f,
					0x10,0x11,0x12,0x13,0x04,0x15,0x16,0x17};
		
	while (true){		
		System.out.println("Enter 16 or less digit character to encrypt");
		Scanner scan= new Scanner(System.in);		
		//For string
	        String text= scan.nextLine();
		int pin_length = text.length();
		//System.out.println("Input Text:"+ text);
		if (text.length() >16){
			throw new Exception ("Must be less than 16 characters");
		}else{		
			text = toPadding(text);
		}		
		if(text.length() <= 16){
			byte[] bytes = text.getBytes();
	    		SecretKeySpec key= new SecretKeySpec(keyBytes,"AES");	 	
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding","BC");
			String textInput = new String(bytes);
			cipher.init(Cipher.ENCRYPT_MODE,key);
			byte[] cipherText=new byte[cipher.getOutputSize(bytes.length)];			
			int ctLength = cipher.update(bytes,0,bytes.length,cipherText,0);
			ctLength += cipher.doFinal(cipherText,ctLength);
			System.out.println("cipher text:"+toHex(cipherText)+"bytes:"+ctLength);
			cipher.init(Cipher.DECRYPT_MODE,key);
			byte[] plainText = new byte[cipher.getOutputSize(cipherText.length)];			
			int ptLength = cipher.update(cipherText,0,ctLength,plainText,0);
			ptLength += cipher.doFinal(plainText,ptLength);
			String textOutput= new String(plainText);
			textOutput = toNoPadding(textOutput,pin_length); 
		}else{
			System.out.println("No can do!!");		
		}
	}
	}
}
