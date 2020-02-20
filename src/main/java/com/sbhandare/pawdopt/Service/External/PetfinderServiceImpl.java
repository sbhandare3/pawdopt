package com.sbhandare.pawdopt.Service.External;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbhandare.pawdopt.DTO.AddressDTO;
import com.sbhandare.pawdopt.DTO.OrganizationDTO;
import com.sbhandare.pawdopt.DTO.PetDTO;
import com.sbhandare.pawdopt.DTO.PetTypeDTO;
import com.sbhandare.pawdopt.Service.OrganizationService;
import com.sbhandare.pawdopt.Service.PetService;
import com.sbhandare.pawdopt.Util.PawdoptConstantUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Component
public class PetfinderServiceImpl implements PetfinderService {
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private PetService petService;
    @Autowired
    private ObjectMapper mapper;
    @Value("${petfinder.client_id}")
    private String client_id;
    @Value("${petfinder.client_secret}")
    private String client_secret;
    private Map<String, Long> petfinderOrgIdMap = new HashMap<>();
    private Map<String, String> petTypeMap;

    @Override
    public void getPetfinderData(){
        String token = getAccessToken();
        if(!StringUtils.isEmpty(token)){
            System.out.println(token);
            saveOrganizations(token);
            //setOrgIdMapFromDb();
            savePets(token);
        }
    }

    private String getAccessToken() {
        URL url = null;
        try {
            url = new URL("https://api.petfinder.com/v2/oauth2/token");
            String userCredentials = client_id+":"+client_secret;
            String urlParameters  = "grant_type=client_credentials";
            byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int postDataLength = postData.length;

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes())));
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
            conn.setUseCaches(false);
            try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postData);
            }

            InputStream is = conn.getInputStream();

            Map tokenMap = mapper.readValue(is, Map.class);
            if(tokenMap.containsKey("access_token"))
                return tokenMap.get("access_token").toString();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return StringUtils.EMPTY;
    }

    private void saveOrganizations(String token){
        int page = 1;
        while(true) {
            try {
                URL url = new URL("https://api.petfinder.com/v2/organizations?page="+page);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestProperty("Authorization", "Bearer " + token);
                conn.setRequestMethod("GET");

                InputStream is = conn.getInputStream();
                JsonNode orgsMap = mapper.readTree(is);

                if(petfinderOrgIdMap.size()>=100 || page>orgsMap.get("pagination").get("total_pages").intValue())
                    break;

                List<OrganizationDTO> orgList = parseOrgsFromResponse(orgsMap.get("organizations"));

                if (!orgList.isEmpty()) {
                    for (OrganizationDTO organizationDTO : orgList) {
                        long orgId = organizationService.saveOrganization(organizationDTO);
                        if (orgId != PawdoptConstantUtil.NO_SUCCESS)
                            petfinderOrgIdMap.put(organizationDTO.getPetfinderCode(), orgId);
                    }
                }
                page++;
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<OrganizationDTO> parseOrgsFromResponse(JsonNode orgsMap){
        List<OrganizationDTO> organizationDTOList = new ArrayList<>();
        Iterator it  = orgsMap.elements();
        while(it.hasNext()){
            OrganizationDTO organizationDTO = new OrganizationDTO();
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
                            List photoArray = (ArrayList) propPair.getValue();
                            if(!photoArray.isEmpty()){
                                LinkedHashMap photo = (LinkedHashMap) photoArray.get(0);
                                if(photo.containsKey("full"))
                                    organizationDTO.setImage((String) photo.get("full"));
                            }
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
            petTypeMap = petService.getAllPetTypes();
            while(it.hasNext()) {
                int page = 1;
                while (true && it.hasNext()) {
                    Map.Entry<String, Long> orgIdPair = (Map.Entry) it.next();

                    URL url = new URL("https://api.petfinder.com/v2/animals?organization=" + orgIdPair.getKey() + "&page=" +page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestProperty("Authorization", "Bearer " + token);
                    conn.setRequestMethod("GET");

                    InputStream is = conn.getInputStream();
                    JsonNode petMap = mapper.readTree(is);

                    if(page>petMap.get("pagination").get("total_pages").intValue())
                        break;

                    List<PetDTO> petDTOList = parsePetsFromResponse(petMap.get("animals"));

                    for (PetDTO petDTO : petDTOList) {
                        petService.savePet(petDTO, orgIdPair.getValue());
                    }
                    page++;
                    is.close();
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
            Map<String, Object> petPropMap = mapper.convertValue((JsonNode)it.next(),new TypeReference<Map<String, Object>>(){});
            Iterator propIt = petPropMap.entrySet().iterator();
            while(propIt.hasNext()){
                Map.Entry<String,Object> propPair = (Map.Entry) propIt.next();
                if(propPair.getValue()!=null) {
                    switch (propPair.getKey()) {
                        case "id":
                            int petId = (int) propPair.getValue();
                            petDTO.setPetfinderid((long) petId);
                            break;
                        case "name":
                            petDTO.setName(propPair.getValue().toString());
                            break;
                        case "breeds":
                            petDTO.setBreed(parseBreed(propPair.getValue()));
                            break;
                        case "type":
                            petDTO.setTypeCode(parsePetType(propPair.getValue().toString()));
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
                        case "colors":
                            petDTO.setColor(parseColor(propPair.getValue()));
                            break;
                        case "coat":
                            petDTO.setCoat(propPair.getValue().toString());
                            break;
                        case "attributes":
                            Map<String, Boolean> attMap = mapper.convertValue(propPair.getValue(), Map.class);
                            Iterator attIt = attMap.entrySet().iterator();
                            while(attIt.hasNext()){
                                Map.Entry<String,Boolean> attPair = (Map.Entry) attIt.next();
                                    switch (attPair.getKey()) {
                                        case "spayed_neutered":
                                            petDTO.setIsSpayedNeutered(convertBooleanToString(attPair.getValue()));
                                            break;
                                        case "house_trained":
                                            petDTO.setIsHouseTrained(convertBooleanToString(attPair.getValue()));
                                            break;
                                        case "declawed":
                                            petDTO.setIsDeclawed(convertBooleanToString(attPair.getValue()));
                                            break;
                                        case "special_needs":
                                            petDTO.setIsSpecialNeeds(convertBooleanToString(attPair.getValue()));
                                            break;
                                        case "shots_current":
                                            petDTO.setIsVaccinated(convertBooleanToString(attPair.getValue()));
                                            break;
                                        default:
                                            break;

                                    }
                            }
                            break;
                        case "environment":
                            Map<String, Boolean> envMap = mapper.convertValue(propPair.getValue(), Map.class);
                            Iterator envIt = envMap.entrySet().iterator();
                            while (envIt.hasNext()){
                                Map.Entry<String, Boolean> envPair = (Map.Entry<String, Boolean>) envIt.next();
                                switch (envPair.getKey()){
                                    case "children":
                                        petDTO.setIsGoodWithChildren(convertBooleanToString(envPair.getValue()));
                                        break;
                                    case "dogs":
                                        petDTO.setIsGoodWithDogs(convertBooleanToString(envPair.getValue()));
                                        break;
                                    case "cats":
                                        petDTO.setIsGoodWithCats(convertBooleanToString(envPair.getValue()));
                                        break;
                                    default:
                                        break;
                                }
                            }
                            break;
                        case "description":
                            petDTO.setBio(propPair.getValue().toString());
                            break;
                        case "photos":
                            List photoArray = (ArrayList) propPair.getValue();
                            if(!photoArray.isEmpty()){
                                LinkedHashMap photo = (LinkedHashMap) photoArray.get(0);
                                if(photo.containsKey("full"))
                                    petDTO.setImage((String) photo.get("full"));
                            }
                            break;
                        case "status":
                            if(StringUtils.equals(propPair.getValue().toString(), PawdoptConstantUtil.ADOPTABLE))
                                petDTO.setIsAdoptable("Y");
                            break;
                        case "tags":
                            List tagList = (ArrayList) propPair.getValue();
                            if(!tagList.isEmpty()){
                                StringBuilder tagSb = new StringBuilder();
                                for(Object tag : tagList){
                                    tagSb.append(tag.toString()).append(",");
                                }
                                tagSb.setLength(tagSb.length()-1);
                                petDTO.setTags(tagSb.toString());
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            petDTOList.add(petDTO);
        }

        return petDTOList;
    }

    private String parseBreed(Object breedObj){
        Map<String, Object> breedProps = mapper.convertValue(breedObj, Map.class);
        if((boolean) breedProps.get(PawdoptConstantUtil.PET_BREED_UNKNOWN))
            return PawdoptConstantUtil.PET_BREED_UNKNOWN;
        StringBuilder breedSb = new StringBuilder();
        Iterator breedIt = breedProps.entrySet().iterator();
        while (breedIt.hasNext()) {
            Map.Entry<String, Object> breedPair = (Map.Entry) breedIt.next();
            if(breedPair.getValue()!=null) {
                if(StringUtils.equals(breedPair.getKey(),PawdoptConstantUtil.PET_BREED_PRIMARY))
                    breedSb.append(breedPair.getValue().toString()).append(",");
                else  if(StringUtils.equals(breedPair.getKey(),PawdoptConstantUtil.PET_BREED_SECONDARY)){
                    breedSb.append(breedPair.getValue().toString()).append(",");
                }
                else if(StringUtils.equals(breedPair.getKey(),PawdoptConstantUtil.PET_BREED_MIXED) && (boolean) breedPair.getValue()){
                    breedSb.append(PawdoptConstantUtil.PET_BREED_MIXED).append(",");
                }
            }
        }
        if(breedSb.length()>1) {
            breedSb.setLength(breedSb.length() - 1);
            return breedSb.toString();
        }
        return null;
    }

    private String parseColor(Object colorObj){
        Map<String, String> colorProps = mapper.convertValue(colorObj, Map.class);
        StringBuilder colorSb = new StringBuilder();
        Iterator colorIt = colorProps.entrySet().iterator();
        while (colorIt.hasNext()) {
            Map.Entry<String, String> colorPair = (Map.Entry) colorIt.next();
            if(colorPair.getValue()!=null)
                colorSb.append(colorPair.getValue()).append(",");
        }
        if(colorSb.length()>1) {
            colorSb.setLength(colorSb.length() - 1);
            return colorSb.toString();
        }
        return null;
    }

    private String parsePetType(String type){
        if(petTypeMap.containsKey(type.toLowerCase()))
            return petTypeMap.get(type.toLowerCase());
        return PawdoptConstantUtil.PET_TYPE_OTHER;
    }

    private String convertBooleanToString(Boolean att){
        if(att == null)
            return PawdoptConstantUtil.BOOLEAN_TO_STRING_NULL;
        else if(att)
            return PawdoptConstantUtil.BOOLEAN_TO_STRING_TRUE;
        else
            return PawdoptConstantUtil.BOOLEAN_TO_STRING_FALSE;
    }

    private void setOrgIdMapFromDb(){
        List<OrganizationDTO> organizationDTOList = organizationService.getAllOrganizations();
        for(OrganizationDTO organizationDTO : organizationDTOList){
            petfinderOrgIdMap.put(organizationDTO.getPetfinderCode(), organizationDTO.getOrganizationid());
        }
    }
}
