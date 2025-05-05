package com.assookkaa.ClassRecord.Utils.Token;
import com.assookkaa.ClassRecord.Utils.Token.Interface.TokenDecryptionInterface;
import org.springframework.stereotype.Component;

@Component
public class TokenDecryption implements TokenDecryptionInterface {

    @Override
    public String tokenDecryption(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
