/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica1;

import java.security.InvalidKeyException;
import java.util.Arrays;

/**
 *
 * @author Usuario
 */
public class Practica1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
                try {
            // Clave de ejemplo de 128 bits (16 bytes)
            byte[] key = new byte[] {
                (byte) 0x2b, (byte) 0x7e, (byte) 0x15, (byte) 0x16,
                (byte) 0x28, (byte) 0xae, (byte) 0xd2, (byte) 0xa6,
                (byte) 0xab, (byte) 0xf7, (byte) 0x97, (byte) 0x50,
                (byte) 0x3d, (byte) 0xc0, (byte) 0x77, (byte) 0xbe
            };

            // Mensaje de prueba (128 bits, un solo bloque)
            byte[] mensajeOriginal = new byte[] {
                (byte) 0x48, (byte) 0x65, (byte) 0x6c, (byte) 0x6c,
                (byte) 0x6f, (byte) 0x20, (byte) 0x43, (byte) 0x72,
                (byte) 0x79, (byte) 0x70, (byte) 0x74, (byte) 0x6f,
                (byte) 0x67, (byte) 0x72, (byte) 0x61, (byte) 0x70
            };

            // Crear instancia de SymmetricCipher con la clave
            SymmetricCipher cipher = new SymmetricCipher(key);

            // Cifrar el mensaje
            byte[] mensajeCifrado = cipher.encryptCBC(mensajeOriginal);

            // Descifrar el mensaje cifrado
            byte[] mensajeDescifrado = cipher.decryptCBC(mensajeCifrado);

            // Mostrar resultados
            System.out.println("Mensaje original: " + Arrays.toString(mensajeOriginal));
            System.out.println("Mensaje cifrado: " + Arrays.toString(mensajeCifrado));
            System.out.println("Mensaje descifrado: " + Arrays.toString(mensajeDescifrado));

            // Convertir los resultados a cadenas legibles
            String mensajeOriginalString = new String(mensajeOriginal);
            String mensajeCifradoString = new String(mensajeCifrado);
            String mensajeDescifradoString = new String(mensajeDescifrado);

            System.out.println("Mensaje original (String): " + mensajeOriginalString);
            System.out.println("Mensaje cifrado (String): " + mensajeCifradoString);
            System.out.println("Mensaje descifrado (String): " + mensajeDescifradoString);

        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
}
