package com.exercise.demo.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {

    List<String> searchText;
}
