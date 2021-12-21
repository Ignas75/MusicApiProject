package com.spartaglobal.musicapiproject.services;

import com.spartaglobal.musicapiproject.entities.EndpointPermission;
import com.spartaglobal.musicapiproject.entities.Token;
import com.spartaglobal.musicapiproject.repositories.EndpointPermissionRepository;
import com.spartaglobal.musicapiproject.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private EndpointPermissionRepository endpointpermissionRepository;

    // Auth function, called with the token in a request and the endpoint hardcoded from inside the function tied to that endpoint
    /*
       This function should be called as the first part of your endpoint function if it requires authentication.
       The token should be supplied to your endpoint as part of the RequestHeader "Authorization" which should have value "Basic <token> (see above for examples)
       The endpoint URL should be hardcoded to match whichever mapping you are making the function for

       You are responsible for adding a line to the EndpointPermissions Table in the DB to make this work
       If you do not yet have this table, run this SQL then INSERT the necessary rows.
       CREATE TABLE EndpointPermissions(
            rowID INTEGER AUTO_INCREMENT PRIMARY KEY,
            url NVARCHAR(255) NOT NULL,
            isForCustomer BIT,
            isForStaff BIT,
            isForAdmins BIT
        );
     */
    
    public boolean isAuthorizedForAction(String authToken, String endpoint){
        if(tokenRepository.existsByAuthToken(authToken)){
            Token token = tokenRepository.getByAuthToken(authToken);
            EndpointPermission permissions = endpointpermissionRepository.getByUrl(endpoint);
            switch (token.getRoleID().getId()){
                case 1 -> {
                    if (permissions.getIsForAdmins()) return true;
                } case 2 -> {
                    if(permissions.getIsForStaff()) return true;
                } case 3 -> {
                    if (permissions.getIsForCustomer()) return true;
                }default -> {return false;}
            }
        }
        return false;
    }
}
