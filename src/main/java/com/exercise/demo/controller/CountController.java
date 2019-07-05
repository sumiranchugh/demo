package com.exercise.demo.controller;

import com.exercise.demo.controller.request.SearchRequest;
import com.exercise.demo.controller.response.SearchResponse;
import com.exercise.demo.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/counter-api")
public class CountController {

    @Autowired
    CountService service;

    @PostMapping(value = "/search",  consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public SearchResponse searchTxtCount(@RequestBody SearchRequest request) {
        return new SearchResponse(service.searchText(request.getSearchText()));
    }


    @GetMapping(value = "/top/{top}", produces = "text/csv;charset=UTF-8")
    @ResponseBody
    public String searchTxtCount(@PathVariable("top") int top) {
       return   service.topX(top);
    }
}
