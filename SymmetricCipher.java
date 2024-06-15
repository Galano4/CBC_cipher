
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import java.security.InvalidKeyException;

public class SymmetricCipher {

    byte[] byteKey_;
    SymmetricEncryption s;
    SymmetricEncryption d;
    byte[] iv;
    // Initialization Vector (fixed)

    /**
     * **********************************************************************************
     */
    /* Constructor method */
    /**
     * **********************************************************************************
     */
    public void SymmetricCipher(byte[] byteKey) throws InvalidKeyException {
        byteKey_ = byteKey;
        s = new SymmetricEncryption(byteKey_);
        d = new SymmetricEncryption(byteKey_);
        iv = new byte[]{(byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54,
            (byte) 55, (byte) 56, (byte) 57, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52,
            (byte) 53, (byte) 54}; //9+7 bytes
    }

    /**
     * **********************************************************************************
     */
    /* Method to encrypt using AES/CBC/PKCS5 */
    /**
     * **********************************************************************************
     */
    public byte[] encryptCBC(byte[] input, byte[] byteKey) throws Exception {
        byte[] ciphertext = new byte[input.length];
        byte[] previousBlock = iv;

        // Generate the plaintext with padding
        for (int i = 0; i < input.length; i += 16) //cada 16 iteraciones, 16 bytes (128 bits), un bloque	
        {
            for (int j = 0; j < 16; j++) {
                input[i + j] ^= previousBlock[j]; //del 0 al 15 del input se suman con el 
            }
            // Si es el último bloque, aplica padding PKCS5
            if (i + 16 >= input.length) {
                int paddingLength = 16 - (input.length - i);
                for (int j = 0; j < paddingLength; j++) {
                    input[i + 16 + j] = (byte) paddingLength; //se castea la variable
                }
            }           
            // Generate the ciphertext
            // Cifrar el bloque resultante en modo ECB
            byte[] block = new byte[16];
            System.arraycopy(input, i, block, 0, 16);
            byte[] encryptedBlock = s.encryptBlock(block);

            // Copiar el bloque cifrado al resultado y actualizar el bloque anterior
            System.arraycopy(encryptedBlock, 0, ciphertext, i, 16);
            previousBlock = encryptedBlock; //en la primera iteración previousBlock era iv, ya se cambia al bloque encriptado
        }
        
        return ciphertext;
    }

    /**
     * **********************************************************************************
     */
    /* Method to decrypt using AES/CBC/PKCS5 */
    /**
     * **********************************************************************************
     */
    public byte[] decryptCBC(byte[] input, byte[] byteKey) throws Exception {
        byte[] plaintext = new byte[input.length];
        byte[] previousBlock = iv;

        for (int i = 0; i < input.length; i += 16) { //i=[0, 16, 32, ...]
            // Cifrar el bloque de entrada en modo ECB
            byte[] block = new byte[16];
            System.arraycopy(input, i, block, 0, 16);
            byte[] decryptedBlock = s.decryptBlock(block);

            // XOR el bloque descifrado con el bloque anterior (o IV en el primer bloque)
            for (int j = 0; j < 16; j++) { //j=[0,1,2,3,4, ... , 15]
                decryptedBlock[j] ^= previousBlock[j]; // opciones={[0 XOR 0,1 XOR 1,...,15 XOR 15],[16 XOR 0, 17 XOR 1,..., 31 XOR 15]}
            }

            // Copiar el bloque resultante al resultado y actualizar el bloque anterior
            System.arraycopy(decryptedBlock, 0, plaintext, i, 16);
            previousBlock = block;
        }

        // Eliminar el padding PKCS5
        int paddingLength = plaintext[plaintext.length - 1];
        byte[] unpaddedPlaintext = new byte[plaintext.length - paddingLength];
        System.arraycopy(plaintext, 0, unpaddedPlaintext, 0, unpaddedPlaintext.length);

        return unpaddedPlaintext;
    }

}
