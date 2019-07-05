package com.exercise.demo.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
public class SearchResponse {

    public Map<String,Integer> counts;
}
