package com.sbhandare.pawdopt.Service.External;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbhandare.pawdopt.DTO.AddressDTO;
import com.sbhandare.pawdopt.DTO.OrganizationDTO;
import com.sbhandare.pawdopt.DTO.PetDTO;
import com.sbhandare.pawdopt.Service.OrganizationService;
import com.sbhandare.pawdopt.Service.PetService;
import com.sbhandare.pawdopt.Util.PawdoptConstantUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private PetService petService;
    @Value("${petfinder.client_id}")
    private String client_id;
    @Value("${petfinder.client_secret}")
    private String client_secret;
    private Map<String, Integer> petfinderOrgIdMap;

    @Override
    public void getPetfinderData(){
        String token = getAccessToken();
        if(!StringUtils.isEmpty(token)){
            System.out.println(token);
            saveOrganizations(token);
            savePets(token);
        }
    }

    private String getAccessToken() {
        String command = "curl -d grant_type=client_credentials&" +
                "client_id=" + client_id +
                "&client_secret=" + client_secret+
                " https://api.petfinder.com/v2/oauth2/token";
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

            if(orgList!=null & !orgList.isEmpty()){
                petfinderOrgIdMap = new HashMap<>();
                for (OrganizationDTO organizationDTO : orgList) {
                    int orgId = organizationService.saveOrganization(organizationDTO);
                    if(orgId!=-1)
                        petfinderOrgIdMap.put(organizationDTO.getPetfinderCode(),orgId);
                }
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

    private void savePets(String token){
        try {
            Iterator it = petfinderOrgIdMap.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<String,Integer> orgIdPair = (Map.Entry) it.next();

                URL url = new URL("https://api.petfinder.com/v2/animals?organization=" +orgIdPair.getKey());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestProperty("Authorization", "Bearer " + token);
                conn.setRequestMethod("GET");

                InputStream is = conn.getInputStream();

                ObjectMapper mapper = new ObjectMapper();
                JsonNode petMap = mapper.readTree(is);

                List<PetDTO> petDTOList = parsePetsFromResponse(petMap.get("animals"));

                for(PetDTO petDTO : petDTOList){
                    petService.savePet(petDTO,orgIdPair.getValue());
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private List<PetDTO> parsePetsFromResponse(JsonNode petMap) {
        List<PetDTO> petDTOList = new ArrayList<>();
        Iterator it  = petMap.elements();
        while(it.hasNext()){
            PetDTO petDTO = new PetDTO();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> petPropMap = objectMapper.convertValue((JsonNode)it.next(),new TypeReference<Map<String, Object>>(){});
            Iterator propIt = petPropMap.entrySet().iterator();
            while(propIt.hasNext()){
                Map.Entry<String,Object> propPair = (Map.Entry) propIt.next();
                if(propPair.getValue()!=null) {
                    switch (propPair.getKey()) {
                        case "name":
                            petDTO.setName(propPair.getValue().toString());
                            break;
                        case "breeds":
                            //object contains primary, secondary, mix etc breeds
                            break;
                        case "type":
                            //map pet type to petCode
                            break;
                        case "gender":
                            petDTO.setGender(propPair.getValue().toString());
                            break;
                        case "age":
                            petDTO.setAge(propPair.getValue().toString());
                            break;
                        case "size":
                            petDTO.setSize(propPair.getValue().toString());
                            break;
                        case "color":
                            //object contains primary,sec,tertiary colors
                            break;
                        case "coat":
                            //check for more data
                            break;
                        case "attributes":
                            break;
                        case "environment":
                            break;
                        case "description":
                            petDTO.setBio(propPair.getValue().toString());
                            break;
                        case "photos":
                            //object of photos of diff zies
                            break;
                        case "status":
                            if(StringUtils.equals(propPair.getValue().toString(), PawdoptConstantUtil.ADOPTABLE))
                                petDTO.setIsAdoptable("Y");
                            //check for more data
                            break;
                        case "tags":

                            break;
                        default:
                            break;
                    }
                }
            }
        }

        return petDTOList;
    }
}
