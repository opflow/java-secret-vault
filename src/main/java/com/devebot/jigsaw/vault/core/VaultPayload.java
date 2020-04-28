package com.devebot.jigsaw.vault.core;

import com.devebot.jigsaw.vault.exceptions.ParsingException;
import com.devebot.jigsaw.vault.utils.HexadecimalUtil;
import com.devebot.jigsaw.vault.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see https://docs.ansible.com/ansible/latest/user_guide/vault.html#vault-payload-format-1-1-1-2
 */
public class VaultPayload {
    private final static Logger LOG = LoggerFactory.getLogger(VaultPayload.class);
    private final static char NEWLINE_CHAR = '\n';
    
    private final byte[] salt;
    private final byte[] hmac;
    private final byte[] data;
    
    public VaultPayload(byte[] salt, byte[] hmac, byte[] data) {
        this.salt = salt;
        this.hmac = hmac;
        this.data = data;
    }
    
    public VaultPayload(byte[] encryptedVault) {
        byte [][] vaultParts = unwrap(encryptedVault);
        salt = HexadecimalUtil.decode(StringUtil.newString(vaultParts[0]));
        hmac = HexadecimalUtil.decode(StringUtil.newString(vaultParts[1]));
        data = HexadecimalUtil.decode(StringUtil.newString(vaultParts[2]));
    }
    
    public byte[] getSalt() {
        return salt;
    }

    public byte[] getHmac() {
        return hmac;
    }

    public byte[] getData() {
        return data;
    }
    
    public byte [] toByteArray() {
        return toString().getBytes();
    }

    @Override
    public String toString() {
        String saltString = HexadecimalUtil.encode(salt);
        if (LOG.isTraceEnabled()) {
            LOG.trace("Salt String Length: {} - {}", saltString.length(), salt.length);
        }
        
        String hmacString = HexadecimalUtil.encode(hmac);
        if (LOG.isTraceEnabled()) {
            LOG.trace("HMAC String Length: {} - {}", hmacString.length(), hmac.length);
        }
        
        String dataString = HexadecimalUtil.encode(data, -1);
        if (LOG.isTraceEnabled()) {
            LOG.trace("Data String Length: {} - {}", dataString.length(), data.length);
        }
        
        String complete =  saltString + "\n" + hmacString + "\n" + dataString;
        if (LOG.isTraceEnabled()) {
            LOG.trace("Complete: {} \n{}", complete.length(), complete);
        }
        
        String result = HexadecimalUtil.encode(complete.getBytes(), 80);
        if (LOG.isTraceEnabled()) {
            LOG.trace("Result: [{}] {}\n{}", complete.length() * 2, result.length(), result);
        }
        return result;
    }

    private int[] getDataLengths(byte[] encodedData) {
        int[] result = new int[3];
        int idx = 0;
        
        int saltLen = 0;
        while (encodedData[idx] != NEWLINE_CHAR && idx < encodedData.length) {
            saltLen++;
            idx++;
        }
        idx++; // skip the newline
        if (idx == encodedData.length) {
            throw new ParsingException("Malformed vault block - salt incomplete");
        }
        result[0] = saltLen;
        
        int hmacLen = 0;
        while (encodedData[idx] != NEWLINE_CHAR && idx < encodedData.length) {
            hmacLen++;
            idx++;
        }
        idx++; // skip the newline
        if (idx == encodedData.length) {
            throw new ParsingException("Malformed vault block - hmac incomplete");
        }
        result[1] = hmacLen;
        
        int dataLen = 0;
        while (idx < encodedData.length) {
            dataLen++;
            idx++;
        }
        result[2] = dataLen;
        
        return result;
    }

    private byte[][] unwrap(byte[] encodedData) {
        int[] partsLength = getDataLengths(encodedData);

        byte[][] result = new byte[3][];
        int idx = 0;
        
        int saltIdx = 0;
        result[0] = new byte[partsLength[0]];
        while (encodedData[idx] != NEWLINE_CHAR && idx < encodedData.length) {
            result[0][saltIdx++] = encodedData[idx++];
        }
        idx++; // skip the newline
        if (idx == encodedData.length) {
            throw new ParsingException("Malformed vault block - salt incomplete");
        }
        
        int hmacIdx = 0;
        result[1] = new byte[partsLength[1]];
        while (encodedData[idx] != NEWLINE_CHAR && idx < encodedData.length) {
            result[1][hmacIdx++] = encodedData[idx++];
        }
        idx++; // skip the newline
        if (idx == encodedData.length) {
            throw new ParsingException("Malformed vault block - hmac incomplete");
        }
        
        int dataIdx = 0;
        result[2] = new byte[partsLength[2]];
        while (idx < encodedData.length) {
            result[2][dataIdx++] = encodedData[idx++];
        }
        return result;
    }
}
