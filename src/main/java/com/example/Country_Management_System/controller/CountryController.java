package com.example.Country_Management_System.controller;

import com.example.Country_Management_System.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryService countryService;

    //Fetch detailed information about a specific country by providing its name as a parameter.
    /**
     * @param country_name the name of the country.
     * @return
     */
    @GetMapping("/getInfo/country_name/{name}")
    public ResponseEntity getInfo(@PathVariable("name") String country_name){

        try{
            Object response = countryService.getInfo(country_name);
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //Retrieve a list of all countries' names based on filters (population/area/language) and
    //sorting(asc/desc). It should have support for pagination.
    /**
     * @param language   The language spoken in the countries.
     * @param population Minimum population required.
     * @param area       Minimum area required.
     * @param order      The order in which the results should be sorted (asc/desc).
     * @param pageNo     The page number for paginated results.
     * @param pageSize   The size of each page in the paginated results.
     * @return
     */
    @GetMapping("/getCountries")
    public ResponseEntity filterCountries(@RequestParam("language") String language, @RequestParam("population")Long population, @RequestParam("area")Double area, @RequestParam("sort") String order,  @RequestParam("page") int pageNo, @RequestParam("pageSize") int pageSize){
        try {
            List<String> response = countryService.filterCountries(language, population, area, order, pageNo, pageSize);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
