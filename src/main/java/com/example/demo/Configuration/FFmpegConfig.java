package com.example.demo.Configuration;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


@Slf4j
@Configuration
public class FFmpegConfig {

    @Value("${ffmpeg.main.location}")
    private String ffmpegPath;

    @Value("${ffmpeg.probe.location}")
    private String ffprobePath;
    @Value("${os}")
    private String os;


    @Bean
    public FFmpeg ffMpeg() throws IOException {
        System.out.println("ffmpegPath :" + ffmpegPath);
        ClassPathResource classPathResource = new ClassPathResource(ffmpegPath);
        return new FFmpeg(os.equals("linux") ? ffmpegPath : classPathResource.getURL().getPath());
    }

    @Bean
    public FFprobe ffProbe() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(ffprobePath);
        return new FFprobe(os.equals("linux") ? ffprobePath : classPathResource.getURL().getPath());
    }
}