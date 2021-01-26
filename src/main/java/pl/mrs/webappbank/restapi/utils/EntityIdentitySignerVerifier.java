package pl.mrs.webappbank.restapi.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import pl.mrs.webappbank.model.SignableEntity;

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
}
