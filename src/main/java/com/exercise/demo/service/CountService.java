package com.exercise.demo.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Slf4j
public class CountService {

    @Value("${file.path}")
    String filePath;

    Map<String, Integer> countMap = new HashMap<>();
    Map<Integer, String> invCountMap = new TreeMap<>(Comparator.reverseOrder());

    @Autowired
    ResourceLoader resourceLoader;

    @PostConstruct
    public void loadText() {
        try (Stream<String> stream = Files.lines(Paths.get(filePath), UTF_8)) {
            stream.flatMap(s -> Stream.of(s.split("\\W+")))
                    .forEach(s -> countMap.put(s, countMap.getOrDefault(s, 0) + 1));
            countMap.entrySet().parallelStream().forEach(entry -> invCountMap.put(entry.getValue(), entry.getKey()));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.debug("loaded text file");
    }

    public Map<String, Integer> searchText(List<String> texts) {
        Map<String, Integer> counts = new HashMap<>();
        if (null != texts) {
            texts.forEach(t -> counts.put(t, countMap.get(t)));
        }
        return counts;
    }

    public String topX(int top) {
        StringBuilder builder = invCountMap.keySet()
                .stream()
                .limit(top)
                .collect(StringBuilder::new,
                        (b, k) -> b.append(invCountMap.get(k)).append("|").append(k).append("\n"),
                        (b1, b2) -> b1.append("\n").append(b2));
        return builder.toString();
    }
}
