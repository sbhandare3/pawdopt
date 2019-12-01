package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.Config.AES;
import com.sbhandare.pawdopt.Util.PawdoptConstantUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class SecurityServiceImpl implements SecurityService {

    @Value("${oauth2.auth_header.base64Creds}")
    private String base64Creds;

    @Override
    public String loginAfterRegister(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", AES.decrypt(password));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(PawdoptConstantUtil.OAUTH_TOKEN_URL,request,String.class);
        //System.out.println(response.getBody());
        if(response!=null && response.getBody()!=null)
            return response.getBody();
        return PawdoptConstantUtil.AUTO_LOGIN_FAILED;
    }
}
