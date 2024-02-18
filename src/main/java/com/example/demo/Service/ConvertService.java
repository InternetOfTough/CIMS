package com.example.demo.Service;

import static com.example.demo.Constants.constants.HLS;
import static com.example.demo.Constants.constants.HLS_COPY;
import static com.example.demo.Constants.constants.HLS_COPY_VALUE;
import static com.example.demo.Constants.constants.HLS_DELETE_THRESHOLD;
import static com.example.demo.Constants.constants.HLS_DELETE_THRESHOLD_VALUE;
import static com.example.demo.Constants.constants.HLS_FLAGS;
import static com.example.demo.Constants.constants.HLS_FLAGS_FIRST;
import static com.example.demo.Constants.constants.HLS_FLAGS_SECOND;
import static com.example.demo.Constants.constants.HLS_LIST_SIZE;
import static com.example.demo.Constants.constants.HLS_LIST_SIZE_VALUE;
import static com.example.demo.Constants.constants.HLS_TIME;
import static com.example.demo.Constants.constants.HLS_TIME_VALUE;
import static com.example.demo.Constants.constants.OUTPUT_M3U8;

import com.example.demo.common.FileUtils;
import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.progress.Progress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvertService {

    @Value("${hls.output.location}")
    private String HLS_OUTPUT_PATH;
    private final FFmpeg fFmpeg;
    private final FFprobe fFprobe;


    public void convertToHls(int index, String piName, byte[] buffer) {
        File output = FileUtils.createDirectoryIfNotExists(piName, HLS_OUTPUT_PATH);
        File tempMp4 = FileUtils.writeBufferToTempFile(buffer);

        if (output != null) {

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(tempMp4.getAbsolutePath()) // 입력 소스
                    .overrideOutputFiles(true)
                    .addOutput(output.getAbsolutePath() + OUTPUT_M3U8) // 출력 위치
                    .setFormat(HLS)
                    .addExtraArgs(HLS_COPY, HLS_COPY_VALUE)
                    .addExtraArgs(HLS_TIME, HLS_TIME_VALUE)
                    .addExtraArgs(HLS_LIST_SIZE, HLS_LIST_SIZE_VALUE)
                    .addExtraArgs(HLS_DELETE_THRESHOLD, HLS_DELETE_THRESHOLD_VALUE)
                    .addExtraArgs(HLS_FLAGS, index == 0 ? HLS_FLAGS_FIRST : HLS_FLAGS_SECOND)
                    .done();

            run(builder, tempMp4);
        }
    }

    private void run(FFmpegBuilder builder, File tempMp4) {
        FFmpegExecutor executor = new FFmpegExecutor(fFmpeg, fFprobe);

        executor
                .createJob(builder, progress -> {
                    log.info("progress ==> {}", progress);
                    if (progress.status.equals(Progress.Status.END)) {
                        log.info("================================= JOB FINISHED =================================");
                    }
                })
                .run();

        tempMp4.delete();// 임시파일 삭제
    }
}