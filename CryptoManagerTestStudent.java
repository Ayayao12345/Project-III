import static org.junit.Assert.*;
import org.junit.Test;

public class CryptoManagerTestStudent {
	
	
	@Test 
	public void testIsStringInBounds() {
		 String plain = "HELLO WORLD";
		    String key = "KEY";
		    String encrypted = CryptoManager.vigenereEncryption(plain, key);
		    String decrypted = CryptoManager.vigenereDecryption(encrypted, key);  // ✅ correct method now
		    assertEquals(plain.toUpperCase(), decrypted); // Make sure to match case!
		
		
	}
	
@Test
public void testPlayfairBasicEvenLength() {
	
    String plain = "CRYPTO";
    String key = "SECRET";
    String encrypted = CryptoManager.playfairEncryption(plain, key);
    String decrypted = CryptoManager.playfairDecryption(encrypted, key);
    assertEquals(plain, decrypted);
}

@Test 
public void testPlayfairOddLengthPadded() {
	
	String plain = "ABC" ;
	String key  = "KEY" ;
	String encrypted = CryptoManager.playfairEncryption(plain, key);
	String decrypted = CryptoManager.playfairDecryption(encrypted, key);
	assertTrue(decrypted.startsWith("ABC")); 
}

@Test
public void testOutOfBoundsInput() {
    String plain = "hello";  // lowercase not allowed
    String key = "KEY";
    String result = CryptoManager.vigenereEncryption(plain, key);
    assertEquals("The selected string is not in bounds, Try again.", result);
}
@Test
public void testVigenereDecryptionMatchesOriginal() {
    String plain = "THIS IS A TEST!";
    String key = "VIGENERE";
    String encrypted = CryptoManager.vigenereEncryption(plain, key);
    String decrypted = CryptoManager.vigenereDecryption(encrypted, key);
    assertEquals(plain.toUpperCase(), decrypted);
}

@Test
public void testPlayfairWithSpecialCharacters() {
    String plain = "HELLO#";
    String key = "SECRET";
    String encrypted = CryptoManager.playfairEncryption(plain, key);
    String decrypted = CryptoManager.playfairDecryption(encrypted, key);
    assertTrue(decrypted.startsWith("HELLO#"));
}
@Test
public void testVigenereWithLowercaseKey() {
    String plain = "UPPERCASE TEXT!";
    String key = "lowerkey";
    String encrypted = CryptoManager.vigenereEncryption(plain, key);
    String decrypted = CryptoManager.vigenereDecryption(encrypted, key);
    assertEquals(plain.toUpperCase(), decrypted);
}
@Test
public void testEmptyStringVigenere() {
    String plain = "";
    String key = "KEY";
    String encrypted = CryptoManager.vigenereEncryption(plain, key);
    String decrypted = CryptoManager.vigenereDecryption(encrypted, key);
    assertEquals("", encrypted);
    assertEquals("", decrypted);
}


}
