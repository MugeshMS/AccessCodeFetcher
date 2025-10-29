package com.meteoros;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class AuthService {

    public String authenticate(String baseUrl, String username, String password) {

        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/api/auth/login";

        try {
            Map<?,?> response =
                    restTemplate.postForObject(url, Map.of("username", username, "password", password), Map.class);

            if (response != null && response.get("token") != null) {
                return response.get("token").toString();
            }

        } catch (Exception e) {
            System.out.println(" Login failed: " + e.getMessage());
        }
        return null;
    }
}
