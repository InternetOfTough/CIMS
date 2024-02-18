package com.example.demo.Controller;

import com.example.demo.Service.HlsService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class HlsController {

    private final HlsService hlsService;

    @GetMapping("/cctv")
    public String getCCTV(Model model) { // cctv 비디오 페이지
        model.addAttribute("data", "hello!!");
        return "hlsjs";
    }

    @ResponseBody
    @RequestMapping("/cctv/{piname}/{filename}")
    public ResponseEntity<InputStreamResource> getHlsFile( // .m3u8 .ts 불러오기
            @PathVariable String piname,
            @PathVariable String filename
    ) throws FileNotFoundException {
        File file = hlsService.getHlsFile(piname, filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/x-mpegURL"))
                .body(resource);
    }

}
