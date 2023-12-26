package com.example.Country_Management_System.service;

import com.example.Country_Management_System.Exceptions.CountryNotFoundException;
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

    /**
     * Retrieves country information based on the given country name.
     *
     * @param country_name The name of the country.
     * @return Object representing country information.
     * @throws CountryNotFoundException if the country information is not found.
     */
    public Object getInfo(String country_name) throws CountryNotFoundException {
        // Get REST Countries API URL to get country information by name
        String url = "https://restcountries.com/v3.1/name/" + country_name;

        // Perform the REST API call using RestTemplate
        Object response = restTemplate.getForObject(url, Object.class);

        // Check if the response is null, indicating invalid credentials or no data found
        if (response == null) {
            throw new CountryNotFoundException("Invalid Credential");
        }
        return response;
    }

    /**
     * Filters and retrieves a list of countries based on specified criteria.
     *
     * @param language   The language spoken in the countries.
     * @param population Minimum population required.
     * @param area       Minimum area required.
     * @param order      The order in which the results should be sorted (asc/desc).
     * @param pageNo     The page number for paginated results.
     * @param pageSize   The size of each page in the paginated results.
     * @return List of country names that meet the criteria, sorted as specified.
     * @throws CountryNotFoundException if no countries are found based on the given information.
     */
    public List<String> filterCountries(String language, Long population, Double area, String order, int pageNo, int pageSize) throws CountryNotFoundException {
        // Get REST Countries API URL to get countries by language
        String langUrl = "https://restcountries.com/v3.1/lang/" + language;

        // Perform the REST API call using RestTemplate and retrieve a list of CountryResponse objects
        ResponseEntity<List<CountryResponse>> responseEntity = restTemplate.exchange(
                langUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CountryResponse>>() {});

        // Extract the list of CountryResponse objects from the ResponseEntity
        List<CountryResponse> countryResponses = responseEntity.getBody();

        // Initialize a list to store the names of countries that meet the criteria
        List<String> responseList = new ArrayList<>();

        // Iterate through the CountryResponse objects and filter based on population and area criteria
        for (CountryResponse countryResponse : countryResponses) {
            if (countryResponse.getPopulation() >= population && countryResponse.getArea() >= area) {
                responseList.add(countryResponse.getName().getCommon());
            }
        }

        // If no countries meet the criteria, throw an exception
        if (responseList.isEmpty()) {
            throw new CountryNotFoundException("No countries found based on given information!!!!");
        }

        // Sort the responseList based on the specified order (asc/desc)
        if (order.equals("asc")) {
            Collections.sort(responseList);
        } else if (order.equals("desc")) {
            Collections.sort(responseList, Collections.reverseOrder());
        } else {
            throw new CountryNotFoundException("Add proper order (asc/desc)!!!!");
        }

        // Implement pagination by selecting the sublist based on pageNo and pageSize
        int start = (pageNo - 1) * pageSize;
        int end = Math.min(start + pageSize, responseList.size());
        List<String> list;

        // Check if the start index is beyond the size of the responseList
        if (responseList.size() < start) {
            list = new ArrayList<>();
        } else {
            list = responseList.subList(start, end);
        }

        return list;
    }
}
