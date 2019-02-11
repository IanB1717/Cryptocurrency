import java.util.Scanner;
import java.util.*;
import java.math.BigInteger;
import java.math.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
public class PrivateKeyGenerator
{
	public static void main(String args[])
	{
        Scanner sc=new Scanner(System.in);
        int length=sc.nextInt();
        String Original=RandomHex(length);
        String Eighty=AddEighty(Original);
        String sha;
        String sha2;
        SHA256 mySha = new SHA256();
        String secret1 = Eighty;
        try{
        sha=mySha.sha256(secret1);
        sha2=mySha.sha256(sha);
		System.out.println(sha);
		System.out.println(sha2);
		String EightyFour= Eighty4(sha2,Eighty);
		Base58CheckEncoding base = new Base58CheckEncoding();
        System.out.println(base.convertToBase58(EightyFour,16));
		}catch (NoSuchAlgorithmException e){}
		
		
		
	
		
	}
	public static String RandomHex(int length)
	{
		Random randomService = new Random();
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length)
	    {
		   sb.append(Integer.toHexString(randomService.nextInt()));
		}
		sb.setLength(length);
		System.out.println(sb.toString());
		return sb.toString();
		
	}
	public static String AddEighty(String Original)
	{
		String Eighty=80+Original;
		System.out.println(Eighty);
		return Eighty;
	}
	public static String Eighty4(String sha2,String Eighty)
	{
		String EightyFour=Eighty+sha2.substring(0,9);
		System.out.println(EightyFour);
		return EightyFour;
	}
	
}
	class SHA256 {
	static String sha256(String input) throws NoSuchAlgorithmException {
	byte[] in = hexStringToByteArray(input);
	MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
	byte[] result = mDigest.digest(in);
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < result.length; i++) {
	sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
	}
	return sb.toString();
	}
	public static byte[] hexStringToByteArray(String s) {
	int len = s.length();
	byte[] data = new byte[len / 2];
	for (int i = 0; i < len; i += 2) {
	data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	+ Character.digit(s.charAt(i+1), 16));
	}
	return data;
	}
	}

	class Base58CheckEncoding {
    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final BigInteger BIG0 = BigInteger.ZERO;
    private static final BigInteger BIG58 = BigInteger.valueOf(58);
 
    public static String convertToBase58(String hash) {
        return convertToBase58(hash, 16);
    }
 
    public static String convertToBase58(String hash, int base) {
        BigInteger x;
        if (base == 16 && hash.substring(0, 2).equals("0x")) {
            x = new BigInteger(hash.substring(2), 16);
        } else {
            x = new BigInteger(hash, base);
        }
 
        StringBuilder sb = new StringBuilder();
        while (x.compareTo(BIG0) > 0) {
            int r = x.mod(BIG58).intValue();
            sb.append(ALPHABET.charAt(r));
            x = x.divide(BIG58);
        }
 
        return sb.reverse().toString();
    }
}