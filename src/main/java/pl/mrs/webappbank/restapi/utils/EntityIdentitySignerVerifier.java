package pl.mrs.webappbank.restapi.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import pl.mrs.webappbank.model.SignableEntity;

import java.text.ParseException;

public class EntityIdentitySignerVerifier {

    private final static String SECRET = "8PKfQo7ubbZLdiCjjMzF91ZMclsJPTKurizVicX-0A9HbXVXNFrTEu1_B4O3Pl-In1XVx_FRq-wqJvh-uvKfKKswyfUc93mqcH86YtFPM317EHdr8looHaenHbpBWZd3CpvNEZkScaZZAo70S-CsfzC9-1fXv8tkDjaX028IUQ0";
    public static String calculateSignature(SignableEntity entity) {
        JWSObject jwsObject;
        try {
            JWSSigner signer = new MACSigner(SECRET);
            jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(entity.getSignablePayload()));
            jwsObject.sign(signer);
        }
        catch (JOSEException ex) {
            return "ETag generation failure";
        }
        return jwsObject.serialize();
    }

    public static boolean verifySignature(String eTagValue) {
        try {
            JWSObject jwsObject = JWSObject.parse(eTagValue);
            JWSVerifier verifier = new MACVerifier(SECRET);
            return jwsObject.verify(verifier);
        }
        catch (JOSEException | ParseException ex) {
            return false;
        }
    }
    public static boolean verifyIntegration(String eTagValue, SignableEntity entity) {
        try {
            String payloadFromETag = JWSObject.parse(eTagValue).getPayload().toString();
            String payloadFromEntity = entity.getSignablePayload();
            return verifySignature(eTagValue) && payloadFromEntity.equals(payloadFromETag);
        }
        catch (ParseException ex) {
            return false;
        }
    }
}
