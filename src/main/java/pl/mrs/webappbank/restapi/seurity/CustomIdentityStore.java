package pl.mrs.webappbank.restapi.seurity;

import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.model.users.Person;
import pl.mrs.webappbank.repositories.PersonRepository;

import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomIdentityStore implements IdentityStore {

    @Inject
    private PersonRepository personRepository;
    @Override
    public CredentialValidationResult validate(Credential credential) {
        if(credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            Person person = personRepository.findByLoginPasswordActive(usernamePasswordCredential.getCaller(),usernamePasswordCredential.getPasswordAsString());
            if(null != person) {
                return new CredentialValidationResult(person.getLogin(), new HashSet<>(Arrays.asList(person.getAccessLevel())));
            }
            else {
                return CredentialValidationResult.INVALID_RESULT;
            }
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return IdentityStore.super.getCallerGroups(validationResult);
    }
}
