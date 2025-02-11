package com.assookkaa.ClassRecord.Utils.Token;

import com.assookkaa.ClassRecord.Utils.Token.Data.TokenData;
import com.assookkaa.ClassRecord.Utils.Token.Interface.TokenDecryptionInterface;

public class TokenDecryption implements TokenDecryptionInterface {

    TokenData tokenData = new TokenData();
    @Override
    public void tokenDecryption(String token) {
        if (token.startsWith("Bearer ")){
            token = token.substring(7);
            tokenData.setToken(token);
        }
    }

    public String getToken() {
        return tokenData.getToken();
    }
}
