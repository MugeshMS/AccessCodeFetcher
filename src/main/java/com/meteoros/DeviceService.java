package com.meteoros;

import java.util.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeviceService {

    public List<DeviceBasic> getDeviceBasics(String baseUrl, String token, String type) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        List<DeviceBasic> result = new ArrayList<>();

        int page = 0;
        int pageSize = 30;
        boolean hasNext = true;

        while (hasNext) {

            String url = baseUrl +
                    "/api/tenant/devices?pageSize=" + pageSize +
                    "&page=" + page +
                    "&type=" + type;

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<Map> response =
                        restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

                Map body = response.getBody();
                if (body == null) break;

                List<Map> data = (List<Map>) body.get("data");
                hasNext = Boolean.TRUE.equals(body.get("hasNext"));

                if (data != null) {
                    for (Map device : data) {

                        String name = Objects.toString(device.get("name"), null);
                        String devType = Objects.toString(device.get("type"), null);

                        if (devType != null && devType.equalsIgnoreCase(type)) {

                            Map idObj = (Map) device.get("id");
                            String id = idObj != null ? Objects.toString(idObj.get("id"), null) : null;

                            DeviceBasic d = new DeviceBasic(name, id);

                            // fetch credentials
                            d.setAccessToken(fetchAccessToken(baseUrl, token, id));

                            result.add(d);
                        }
                    }
                }

                page++;

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                break;
            }
        }

        return result;
    }

    private String fetchAccessToken(String baseUrl, String token, String deviceId) {
        if (deviceId == null) return null;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        String url = baseUrl + "/api/device/" + deviceId + "/credentials";

        try {
            ResponseEntity<Map> resp =
                    restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Map.class);

            Map body = resp.getBody();
            if (body != null && body.get("credentialsId") != null) {
                return body.get("credentialsId").toString();
            }
        } catch (Exception ignored) {}

        return null;
    }
}
