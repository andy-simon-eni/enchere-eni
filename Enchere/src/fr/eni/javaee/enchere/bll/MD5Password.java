package fr.eni.javaee.enchere.bll;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import fr.eni.javaee.enchere.BusinessException;

public final class MD5Password {
	
	private MD5Password() {};
	
	public static String getEncodedPassword(String key) throws BusinessException {
		StringBuilder sb = new StringBuilder();
		StringBuilder hashString = null;
		byte[] uniqueKey = key.getBytes();
		byte[] hash = null;
		for(int i = 1; i<=2; i++) {
			try {
				hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();				
				throw new BusinessException();
			}
			hashString = new StringBuilder();
			for (int j = 0; j < hash.length; j++) {
				String hex = Integer.toHexString(hash[j]);
				if (hex.length() == 1) {
					hashString.append('0');
					hashString.append(hex.charAt(hex.length() - 1));
				} else {
					hashString.append(hex.substring(hex.length() - 2));
				}
			}
			sb.append("az");
			sb.append(hashString.toString());
			sb.append("op");
			uniqueKey = sb.toString().getBytes();
		}
		
		return hashString.toString();
	}

	public static boolean testPassword(String clearTextTestPassword, String encodedActualPassword)
			throws BusinessException {
		String encodedTestPassword = getEncodedPassword(clearTextTestPassword);

		return (encodedTestPassword.equals(encodedActualPassword));
	}
}
