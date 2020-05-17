package com.sbhandare.pawdopt.Service.External;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import com.sbhandare.pawdopt.Model.GeoPoint;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class OpenStreetMapProcessor {

    private static OpenStreetMapProcessor instance = null;

    public static OpenStreetMapProcessor getInstance() {
        if (instance == null) {
            instance = new OpenStreetMapProcessor();
        }
        return instance;
    }

    private String getRequest(String url) throws Exception {

        final URL obj = new URL(url);
        final HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        if (con.getResponseCode() != 200) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public GeoPoint getCoordinates(String address) {
        GeoPoint point = null;
        StringBuffer query;
        String[] split = address.split(",");
        String queryResult = null;

        query = new StringBuffer();

        query.append("https://nominatim.openstreetmap.org/search?q=");

        if (split.length == 0) {
            return null;
        }

        for (int i = 0; i < split.length; i++) {
            String str = split[i].trim();
            if(!StringUtils.isEmpty(str))
                query.append(str);
            if (i < (split.length - 1)) {
                query.append("+");
            }
        }
        query.append("&format=json&addressdetails=1");

        try {
            queryResult = getRequest(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (queryResult == null) {
            return null;
        }

        Object obj = JSONValue.parse(queryResult);

        if (obj instanceof JSONArray) {
            JSONArray array = (JSONArray) obj;
            if (array.size() > 0) {
                JSONObject jsonObject = (JSONObject) array.get(0);

                String lon = (String) jsonObject.get("lon");
                String lat = (String) jsonObject.get("lat");
                point = new GeoPoint(new BigDecimal(lat), new BigDecimal(lon));
            }
        }
        return point;
    }
}
