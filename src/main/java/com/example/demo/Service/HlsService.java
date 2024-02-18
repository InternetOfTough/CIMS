package com.example.demo.Service;

import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HlsService {
    @Value("${hls.output.location}")
    private String HLS_OUTPUT_PATH; //final 하면 강제종료됨...
    @Value("${delimeter}")
    private String DELIMETER;

    public File getHlsFile(String piname, String filename) {
        return new File(HLS_OUTPUT_PATH + piname + DELIMETER + filename);
    }
}
