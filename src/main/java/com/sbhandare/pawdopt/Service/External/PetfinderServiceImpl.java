package com.sbhandare.pawdopt.Service.External;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbhandare.pawdopt.DTO.AddressDTO;
import com.sbhandare.pawdopt.DTO.OrganizationDTO;
import com.sbhandare.pawdopt.Service.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


@Component
public class PetfinderServiceImpl implements PetfinderService {
    @Autowired
    private OrganizationService organizationService;

    @Override
    public void getPetfinderData(){
        String token = getAccessToken();
        if(!StringUtils.isEmpty(token)){
            System.out.println(token);
            saveOrganizations(token);
        }
    }

    private String getAccessToken() {
        String command = "curl -d grant_type=client_credentials&" +
                "client_id=OEwrZmNNl6UXV9HOkpeAkWjfbf2sGzUl2wqOGyvILi67zEJ330&" +
                "client_secret=B0PwjWpuPm3uZFPbqFhvW3D1Nr3S9DuCUkL93SbJ " +
                "https://api.petfinder.com/v2/oauth2/token";
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            ObjectMapper mapper = new ObjectMapper();
            if (process != null) {
                Map tokenMap = mapper.readValue(process.getInputStream(), Map.class);
                if(tokenMap.containsKey("access_token"))
                    return tokenMap.get("access_token").toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    private void saveOrganizations(String token){
        try {
            URL url = new URL("https://api.petfinder.com/v2/organizations");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestMethod("GET");

            InputStream is = conn.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode orgsMap = mapper.readTree(is);

            List<OrganizationDTO> orgList = parseOrgsFromResponse(orgsMap.get("organizations"));

            for(OrganizationDTO organizationDTO : orgList){
                organizationService.saveOrganization(organizationDTO);
            }

            is.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private List<OrganizationDTO> parseOrgsFromResponse(JsonNode orgsMap){
        List<OrganizationDTO> organizationDTOList = new ArrayList<>();
        Iterator it  = orgsMap.elements();
        while(it.hasNext()){
            OrganizationDTO organizationDTO = new OrganizationDTO();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> orgProps = mapper.convertValue((JsonNode) it.next(), new TypeReference<Map<String, Object>>(){});
            Iterator orgPropsIt = orgProps.entrySet().iterator();
            while(orgPropsIt.hasNext()){
                Map.Entry<String,Object> propPair = (Map.Entry) orgPropsIt.next();
                if(propPair.getValue()!=null) {
                    switch (propPair.getKey()) {
                        case "id":
                            organizationDTO.setPetfinderCode(propPair.getValue().toString());
                            break;
                        case "name":
                            organizationDTO.setName(propPair.getValue().toString());
                            break;
                        case "email":
                            organizationDTO.setEmail(propPair.getValue().toString());
                            break;
                        case "phone":
                            organizationDTO.setPhone(propPair.getValue().toString().replaceAll("\\D+", ""));
                            break;
                        case "address":
                            AddressDTO addressDTO = new AddressDTO();
                            Map<String, String> addProps = mapper.convertValue(propPair.getValue(), Map.class);
                            Iterator addIt = addProps.entrySet().iterator();
                            while (addIt.hasNext()) {
                                Map.Entry<String, String> addPair = (Map.Entry) addIt.next();
                                if(addPair.getValue()!=null) {
                                    switch (addPair.getKey()) {
                                        case "address1":
                                            addressDTO.setStreet1(addPair.getValue());
                                            break;
                                        case "address2":
                                            addressDTO.setStreet2(addPair.getValue());
                                            break;
                                        case "city":
                                            addressDTO.setCity(addPair.getValue());
                                            break;
                                        case "state":
                                            addressDTO.setState(addPair.getValue());
                                            break;
                                        case "postcode":
                                            addressDTO.setZipCode(addPair.getValue());
                                            break;
                                        case "country":
                                            addressDTO.setCountry(addPair.getValue());
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                            organizationDTO.setAddressDTO(addressDTO);
                            break;
                        case "url":
                            organizationDTO.setWebLink(propPair.getValue().toString());
                            break;
                        case "mission_statement":
                            organizationDTO.setBio(propPair.getValue().toString());
                            break;
                        case "social_media":
                            Map<String, String> socialProps = mapper.convertValue(propPair.getValue(), Map.class);
                            Iterator socialIt = socialProps.entrySet().iterator();
                            while(socialIt.hasNext()){
                                Map.Entry<String,String> socialPair = (Map.Entry) socialIt.next();
                                if(socialPair.getValue()!=null){
                                    switch (socialPair.getKey()){
                                        case "facebook":
                                            organizationDTO.setFbLink(socialPair.getValue());
                                            break;
                                        case "twitter":
                                            organizationDTO.setTwitterLink(socialPair.getValue());
                                            break;
                                        case "youtube":
                                            organizationDTO.setYoutubeLink(socialPair.getValue());
                                            break;
                                        case "instagram":
                                            organizationDTO.setInstaLink(socialPair.getValue());
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                            break;
                        case "photos":
                            break;
                        default:
                            break;
                    }
                }
            }
            organizationDTOList.add(organizationDTO);
        }

        return organizationDTOList;
    }
}
