package pl.mrs.webappbank.restapi.seurity;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;

import javax.security.enterprise.identitystore.CredentialValidationResult;
import java.text.ParseException;
import java.util.Date;
import java.util.jar.JarException;

public class JWTGeneratorVerifier {
    private final static String SECRET = "8PKfQo7ubbZLdiCjjMzF91ZMclsJPTKurizVicX-0A9HbXVXNFrTEu1_B4O3Pl-In1XVx_FRq-wqJvh-uvKfKKswyfUc93mqcH86YtFPM317EHdr8looHaenHbpBWZd3CpvNEZkScaZZAo70S-CsfzC9-1fXv8tkDjaX028IUQ0";
    private static final long JWT_TIMEOUT = 15 * 60 * 1000; //millis
    public static String generateJWTString(CredentialValidationResult credential) {
        try{
            JWSSigner signer = new MACSigner(SECRET);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(credential.getCallerPrincipal().getName())
                    .claim("auth",String.join(",",credential.getCallerGroups()))
                    .issuer("WebAppBank Rest Api")
                    .expirationTime(new Date(new Date().getTime() + JWT_TIMEOUT))
                    .build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256),claimsSet);
            signedJWT.sign(new MACSigner(SECRET));
            return signedJWT.serialize();
        } catch (JOSEException ex) {
            return "JWT failure";
        }
    }
    public static boolean validateJWTSignature(String jwtToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(jwtToken);
            JWSVerifier verifier = new MACVerifier(SECRET);
            return signedJWT.verify(verifier);
        }catch (JOSEException | ParseException ex) {
            return false;
        }
    }
}
