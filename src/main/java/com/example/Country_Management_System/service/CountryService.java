package com.example.Country_Management_System.service;

import com.example.Country_Management_System.models.CountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class CountryService {

    @Autowired
    RestTemplate restTemplate;

    public Object getInfo(String country_name) throws Exception {
        String url = "https://restcountries.com/v3.1/name/" + country_name;

        Object response = restTemplate.getForObject(url, Object.class);
        if (response == null) {
            throw new Exception("Invalid Credential");
        }
        return response;
    }
    public List<String> filterCountries(String language, Long population, Double area, String order, int pageNo, int pageSize) throws Exception {
        String langUrl = "https://restcountries.com/v3.1/lang/"+language;
        ResponseEntity<List<CountryResponse>> responseEntity = restTemplate.exchange(
                langUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CountryResponse>>() {});

        List<CountryResponse>countryResponses = responseEntity.getBody();

        List<String>responseList = new ArrayList<>();

        for (CountryResponse countryResponse : countryResponses) {
            if(countryResponse.getPopulation()>=population && countryResponse.getArea()>=area){
                responseList.add(countryResponse.getName().getCommon());
            }
        }

        if(responseList.isEmpty()){
            throw new Exception("No countries found based on given information!!!!");
        }

        if(order.equals("asc")){
            Collections.sort(responseList);
        }
        else if(order.equals("desc")){
            Collections.sort(responseList, Collections.reverseOrder());
        }
        else{
            throw new Exception("Add proper order (asc/desc)!!!!");
        }

        int start = (pageNo - 1) * pageSize;
        int end = Math.min(start + pageSize, responseList.size());
        List<String> list;

        if (responseList.size() < start) {
            list = Collections.emptyList();
        } else {
            list = responseList.subList(start, end);
        }

        return list;
    }
}
