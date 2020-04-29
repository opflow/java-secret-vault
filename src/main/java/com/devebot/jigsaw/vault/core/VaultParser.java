package com.devebot.jigsaw.vault.core;

import com.devebot.jigsaw.vault.utils.HexadecimalUtil;
import com.devebot.jigsaw.vault.utils.StringUtil;

public class VaultParser {
    public static VaultHeader parseHeader(String vaultBlock) {
        return new VaultHeader(vaultBlock.substring(0, vaultBlock.indexOf(StringUtil.LINE_BREAK)));
    }

    public static VaultPayload parsePayload(String vaultBlock) {
        return new VaultPayload(parseBody(vaultBlock));
    }
    
    protected static byte[] parseBody(String vaultBlock) {
        return HexadecimalUtil.decode(StringUtil.join(extractBody(vaultBlock).split(StringUtil.LINE_BREAK)));
    }

    protected static String extractBody(String vaultBlock) {
        return vaultBlock.substring(vaultBlock.indexOf(StringUtil.LINE_BREAK) + 1);
    }
}
