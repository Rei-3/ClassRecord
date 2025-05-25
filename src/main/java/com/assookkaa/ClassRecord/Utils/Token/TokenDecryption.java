package com.assookkaa.ClassRecord.Utils.Token;
import com.assookkaa.ClassRecord.Utils.Token.Interface.TokenDecryptionInterface;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class TokenDecryption implements TokenDecryptionInterface {

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.secret}")
    private String secretKey;

    @Override
    public String tokenDecryption(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
