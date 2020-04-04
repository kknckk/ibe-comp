package io.reks.ibe;

import cryptid.CryptID;
import cryptid.ibe.IdentityBasedEncryption;
import cryptid.ibe.domain.CipherTextTuple;
import cryptid.ibe.domain.SecurityLevel;
import cryptid.ibe.exception.SetupException;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Encryptor {
    private static final Logger logger = Logger.getLogger(Encryptor.class);

    public static void main(String[] args) {
        //just another mock...
        Class clazz = Encryptor.class;
        InputStream inputStream = clazz.getResourceAsStream("/paragon.par");

        try {
            String data = readFromInputStream(inputStream);
            StringBuilder id = new StringBuilder("id::");
            id.append("5.00::");//kwota brutto
            id.append("5519031212::");//nip sprzedawcy
            id.append("20200403::");//data
            id.append("1406");//godzina
            String ids = id.toString();
            CipherTextTuple ct = encrypt(data, id.toString());
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(ids.getBytes(StandardCharsets.UTF_8));

            logger.debug("szyfrogram czyli wartość: " + ct.toString());
            logger.debug("id czyli nigdzie dalej nie idzie bo gdpr: " + ids);
            logger.debug("sha(id) czyli key w naszym key-value storze: " + new String(encodedhash, StandardCharsets.UTF_8));
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    private static final CipherTextTuple encrypt(String message, String identity) {
        // Setup a Boneh-Franklin IBE system of the specified security level.
        // Here we use LOWEST to make things fast.
        try {
            // Encrypt the message
            IdentityBasedEncryption ibe = CryptID.setupBonehFranklin(SecurityLevel.LOWEST);
            CipherTextTuple cipherText = ibe.encrypt(message, identity);
            return cipherText;
        } catch (SetupException e) {
            e.printStackTrace();
        }
        return null;
    }
}
