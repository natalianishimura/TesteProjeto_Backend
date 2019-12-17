package com.projeto.funancial.service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Service;

import com.projeto.funancial.exception.EncriptadorServiceException;
/**
 * A classe <code>EncriptadorService</code> para a realização de criptografia e 
 * validação de informações criptografadas.
 *
 * @author Lokesh Gupta - howtodoinjava.com 
 * @version 1.0
 * @since JDK1.8 * 
 */
@Service
public class EncriptadorService {
	/**
     * O método getSenhaEncriptada é utilizado para criptografar a senha informada no padrão
     * PBKDF2WithHmacSHA1 para que a mesma seja inserida com segurança no repositório de dados.
     *
     * @param String senha - A senha que será criptografada
     * @return String - A senha após ser criptografada
     *
     * @throws EncriptadorServiceException 
     */
	public String geraSenhaEncriptada(String senha)  throws EncriptadorServiceException {
		int iterations = 1000;
	    char[] chars = senha.toCharArray();
	    
	    try {
	    	byte[] salt = getSalt();
	    	PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		    byte[] hash = skf.generateSecret(spec).getEncoded();
		    
		    return iterations + ":" + toHex(salt) + ":" + toHex(hash);		    
		} catch (NoSuchAlgorithmException e) {
			throw new EncriptadorServiceException("Erro durante a execução do mecanismo de criptografia.", e.getCause());
		} catch (InvalidKeySpecException  e) {
			throw new EncriptadorServiceException("Uma especifição de chave inválida foi encontrada.", e.getCause());
		}
	}
	
	 /**
     * O método validaSenha é responsável por verificar se a senha informada é igual à senha
     * armazenada no repositório.
     *
     * @param String senhaOriginal - A senha informada pelo usuário
     * @param String senhaArmazenada - A senha armazenada no repositório
     *
     * @return boolean - A resposta da validação (true = válida, false = inválida)
     *
     * @throws EncriptadorServiceException
     */
	public boolean validaSenha(String senhaOriginal, String senhaArmazenada) throws EncriptadorServiceException {
		String[] parts = senhaArmazenada.split(":");
        int iterations = Integer.parseInt(parts[0]);
        try {
        	byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);
             
            PBEKeySpec spec = new PBEKeySpec(senhaOriginal.toCharArray(), salt, iterations, hash.length * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] testHash = skf.generateSecret(spec).getEncoded();
             
            int diff = hash.length ^ testHash.length;
            for(int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }
            
            return diff == 0;            
		} catch (NoSuchAlgorithmException e) {
			throw new EncriptadorServiceException("Erro durante a execução do mecanismo de criptografia.", e.getCause());
		} catch (InvalidKeySpecException e) {
			throw new EncriptadorServiceException("Uma especifição de chave inválida foi encontrada.", e.getCause());
        }
    }
    	
	/**
     * O método getSalt é responsável por gerar o salt utilizado durante o processo de 
     * encriptação da chave
     *
     * @return byte[] - O salt gerado
     * @throws NoSuchAlgorithmException
     */
	protected byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	    byte[] salt = new byte[16];
	    sr.nextBytes(salt);
	    return salt;
	}
	
	/**
     * O método toHex é utilizado para converter byte[] para objetos String
     *
     * @param array   - O byte[] que será convertido
     * @return String - O byte[] convertido para String
     */
	protected String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
	    String hex = bi.toString(16);
	    int paddingLength = (array.length * 2) - hex.length();
	    if(paddingLength > 0) {
	    	return String.format("%0"  +paddingLength + "d", 0) + hex;
	    } else {
	    	return hex;
	    }
	}
	
	/**
    * O método fromHex é utilizado para converter objetos String para byte[]
    *
    * @param hex     - A String que será convertida
    * @return byte[] - A String convertida para byte[]
    */
	private byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++) {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
