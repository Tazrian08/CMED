package com.prescription.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

@Service
@RequiredArgsConstructor
public class RxNavService {

    private static final String RXNAV_BASE_URL = "https://rxnav.nlm.nih.gov/REST";
    private final RestTemplate restTemplate = new RestTemplate();

    public String getDrugInteractions(String rxcui) {
        try {
            String url = RXNAV_BASE_URL + "/interaction/interaction.json?rxcui=" + rxcui;
            String response = restTemplate.getForObject(url, String.class);
            return response;
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch drug interactions: " + e.getMessage(), e);
        }
    }
}
