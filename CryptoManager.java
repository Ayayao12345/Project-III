
public class CryptoManager {
	

	    private static final char LOWER_RANGE = ' ';
	    private static final char UPPER_RANGE = '_';
	    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
	    // Use 64-character matrix (8X8) for Playfair cipher  
	    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_";

	    public static boolean isStringInBounds(String plainText) {
	        for (int i = 0; i < plainText.length(); i++) {
	            if (!(plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE)) {
	                return false;
	            }
	        }
	        return true;
	    }

		/**
		 * Vigenere Cipher is a method of encrypting alphabetic text 
		 * based on the letters of a keyword. It works as below:
		 * 		Choose a keyword (e.g., KEY).
		 * 		Repeat the keyword to match the length of the plaintext.
		 * 		Each letter in the plaintext is shifted by the position of the 
		 * 		corresponding letter in the keyword (A = 0, B = 1, ..., Z = 25).
		 */   

	    public static String vigenereEncryption(String plainText, String key) {
	         //to be implemented by students
	    	if (  !isStringInBounds(plainText)) {
	    		return "The selected string is not in bounds, Try again.";
	    	}
	    	StringBuilder encryptedText =  new StringBuilder();
	    	
	    	key = key.toUpperCase();  // ensure key is upperCase 
	    	plainText = plainText.toUpperCase(); // ensure plainText is upperCase
	    	
	    for (int i = 0; i < plainText.length(); i++) {
	    	char plainChar = plainText.charAt(i) ;
	    	char keyChar  = key.charAt(i % key.length());
	    	
	    	// convert characters to position in alphabet64 range 
	    	
	   int plainPos = plainChar- LOWER_RANGE ;
	   int keyPos = keyChar- LOWER_RANGE ;

	   	// Encrypt by shifting 
	   
	   int encryptedPos = (plainPos + keyPos) % RANGE ;
	   char encryptedChar = (char) (encryptedPos + LOWER_RANGE); 
	   
	   encryptedText.append(encryptedChar);
	  
	    }
	    return encryptedText.toString(); 
	    }

	    // Vigenere Decryption
	    public static String vigenereDecryption(String encryptedText, String key) {
	         //to be implemented by students
	    	if(!isStringInBounds(encryptedText)) {
	    		return "The selected string is not in bounds, Try again.";
	    	}
	    	StringBuilder decryptedText = new StringBuilder();
	    	key = key.toUpperCase() ; // ensure that key is uppercase
	    	encryptedText = encryptedText.toUpperCase();// ensure that encryptedText is uppercase
	    	
	    	for (int i = 0; i < encryptedText.length(); i++) {
	    		char encryptedChar = encryptedText.charAt(i);
	    		char keyChar = key.charAt( i % key.length());
	    		
	    	// Convert characters to position in the Alphabet64 range 
	    	
	    			int encryptedPos = encryptedChar-LOWER_RANGE ;
	    			int keyPos = keyChar-LOWER_RANGE ;
	    	// Decrypt using modular subtraction 
	    			int decryptedPos = (encryptedPos - keyPos + RANGE) % RANGE ;
	    			char decryptedChar = (char) (decryptedPos + LOWER_RANGE) ;
	    			
	    			decryptedText.append(decryptedChar);
	    	}
	    	
	    	return decryptedText.toString();
	    }


		/**
		 * Playfair Cipher encrypts two letters at a time instead of just one.
		 * It works as follows:
		 * A matrix (8X8 in our case) is built using a keyword
		 * Plaintext is split into letter pairs (e.g., ME ET YO UR).
		 * Encryption rules depend on the positions of the letters in the matrix:
		 *     Same row: replace each letter with the one to its right.
		 *     Same column: replace each with the one below.
		 *     Rectangle: replace each letter with the one in its own row but in the column of the other letter in the pair.
		 */    

	    public static String playfairEncryption(String plainText, String key) {
	         //to be implemented by students
	    	if (!isStringInBounds(plainText)) {
	    		return "The selected string is not in bounds, Try again.";

	    	}
	    // Build 8*8 matrix using key and Alphabet64
	    	char [][] matrix = buildMatrix(key);
	    	key = key.toUpperCase();  // ensure key is upperCase 
	    	plainText = plainText.toUpperCase(); // ensure plainText is upperCase
	    	
	    // prepare the plaintext into digraphs 
	    	StringBuilder filteredText = new StringBuilder(plainText);
	    	if (filteredText.length() % 2 != 0) {
	    			filteredText.append('X'); 
	    	}
	    	StringBuilder encryptedText = new StringBuilder();
	    	
	    // Encrypt pairs
	    for (int i = 0; i < filteredText.length(); i +=2) {
	    	char a = filteredText.charAt(i) ;
	    	char b = filteredText.charAt(i+1);
	    	
	    	int[] posA = findPosition(matrix, a);
	    	int[] posB = findPosition(matrix, b);
	    if (posA[0] == posB[0]) {
	    	// same row => shift right 
	    	encryptedText.append(matrix[posA[0]][(posA[1]+ 1) % 8]); 
	    	encryptedText.append(matrix[posB[0]][(posB[1]+ 1) % 8]); 
	    } else if (posA[1] == posB[1]) {
	    	// same column => shift down
	    	encryptedText.append(matrix[(posA[0]+1) % 8] [posA[1]]); 
	    	encryptedText.append(matrix[(posB[0]+1) % 8] [posB[1]]); 
	    } else {
	    	// Rectangle => swap columns 
	    	encryptedText.append(matrix[posA[0]] [posB[1]]) ;
	    	encryptedText.append(matrix[posB[0]] [posA[1]]) ;
	    }
	    }
	     return encryptedText.toString();
	    }
	    
	   
	 private static char[][] buildMatrix(String key) {
	        key = key.toUpperCase();  // Normalize key to uppercase
	        String combined = key + ALPHABET64;
	        StringBuilder unique = new StringBuilder();

	        for (int i = 0; i < combined.length(); i++) {
	            char c = combined.charAt(i);
	            if (unique.indexOf(String.valueOf(c)) == -1) {
	                unique.append(c);
	                if (unique.length() == 64) break; // stop when we have enough
	            }
	        }

	        // Ensure we have exactly 64 characters
	        while (unique.length() < 64) {
	            for (int i = 0; i < ALPHABET64.length(); i++) {
	                char c = ALPHABET64.charAt(i);
	                if (unique.indexOf(String.valueOf(c)) == -1) {
	                    unique.append(c);
	                    if (unique.length() == 64) break;
	                }
	            }
	        }

	        char[][] matrix = new char[8][8];
	        int index = 0;
	        for (int row = 0; row < 8; row++) {
	            for (int col = 0; col < 8; col++) {
	                matrix[row][col] = unique.charAt(index++);
	            }
	        }

	        return matrix;
	    }

	 private static int [] findPosition (char[][] matrix, char c) {
		 
		 for (int row = 0; row < 8; row++) {
			 for (int col = 0; col < 8; col++ ) {
				if(matrix[row][col] == c) {
					return new int[] {row, col};
				}
			 }
		 }
		 return new int [] {-1,-1};
	 }

	    // Vigenere Decryption
	    public static String playfairDecryption(String encryptedText, String key) {
	         //to be implemented by students 
	    	if (!isStringInBounds(encryptedText)) {
	    	    return "The selected string is not in bounds, Try again.";
	    	}

	    	
	    	char [][] matrix = buildMatrix(key);
	    	StringBuilder decrypted = new StringBuilder();
	    	key = key.toUpperCase() ; // ensure that key is uppercase
	    	encryptedText = encryptedText.toUpperCase();// ensure that encryptedText is uppercase
	    	
	    	for(int i = 0; i< encryptedText.length(); i+=2) {
	    		char a = encryptedText.charAt(i);
	    		char b = encryptedText.charAt(i+1);
	    		int[] posA = findPosition(matrix, a);
	    		int[] posB = findPosition(matrix, b);
	    		
	    	if (posA[0] == posB[0]) {
	    		decrypted.append(matrix[posA[0]] [(posA[1] + 7) % 8] );
	    		decrypted.append(matrix[posB[0]] [(posB[1]+ 7) % 8]);
	    		
	    	} else if(posA[1] == posB [1]) {
	    		 decrypted.append(matrix[(posA[0] + 7) % 8][posA[1]]);
	                decrypted.append(matrix[(posB[0] + 7) % 8][posB[1]]);
	    		
	    	}else {
	    		decrypted.append(matrix[posA[0]][posB[1]]);
	    		decrypted.append(matrix[posB[0]][posA[1]]);
	    	}
	    	}
	    

	return decrypted.toString();

	}
	    
	 
}
