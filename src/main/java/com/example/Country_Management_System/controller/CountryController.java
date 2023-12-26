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
