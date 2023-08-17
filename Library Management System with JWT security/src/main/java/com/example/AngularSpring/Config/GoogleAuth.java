package com.example.AngularSpring.Config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;

@RequiredArgsConstructor
@Configuration
@EnableAutoConfiguration
public class GoogleAuth{

    private static final String CLIENT_ID = "880713808646-db7qbait6rm10c1t5a8oeo0o067j75j3.apps.googleusercontent.com";



    private final JsonFactory jsonFactory;
    private final HttpTransport httpTransport;

    public GoogleIdToken.Payload verifyAndExtractPayload(String idToken) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();
            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj != null) {
                System.out.println("30");
                return idTokenObj.getPayload();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
