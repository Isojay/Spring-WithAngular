package com.example.AngularSpring.Config;

import com.example.AngularSpring.Auth.MsgResponse;
import com.example.AngularSpring.Service.StudentService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import java.io.IOException;
import java.security.GeneralSecurityException;
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
            System.out.println("10");
            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj != null) {
                return idTokenObj.getPayload();
            }
        } catch (Exception e) {
            System.out.println("6");
            e.printStackTrace();
        }

        return null;
    }








//    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
////    @Override
////    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
////
////        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
////        String email =  token.getPrincipal().getAttributes().get("email").toString();
////        if (studentService.findbyEmail(email) != null) {
////            StudentDetails studentDetails = new StudentDetails();
////            studentDetails.setFName(token.getPrincipal().getAttributes().get("given_name").toString());
////            studentDetails.setLName(token.getPrincipal().getAttributes().get("family_name").toString());
////            studentDetails.setEmail(email);
////            studentDetails.setRole(Role.USER);
////            studentDetails.setIdstatus(0);
////            studentService.save(studentDetails);
////
////        }
////        redirectStrategy.sendRedirect(request, response, "/");
////
////    }
}
