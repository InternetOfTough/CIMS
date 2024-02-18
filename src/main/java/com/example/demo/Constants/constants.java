package com.example.demo.Constants;

public class constants {
    public static final String OUTPUT_M3U8 = "/output.m3u8"; // 출력 파일명
    public static final String HLS = "hls";
    public static final String HLS_TIME = "-hls_time";
    public static final String HLS_TIME_VALUE = "10"; // hls_time
    public static final String HLS_LIST_SIZE = "-hls_list_size"; // hls_list_size 옵션
    public static final String HLS_LIST_SIZE_VALUE = "6"; // hls_list_size 옵션 값
    public static final String HLS_DELETE_THRESHOLD = "-hls_delete_threshold"; // hls_delete_threshold 옵션
    public static final String HLS_DELETE_THRESHOLD_VALUE = "1"; // hls_delete_threshold 옵션 값
    public static final String HLS_FLAGS = "-hls_flags";
    public static final String HLS_FLAGS_FIRST = "delete_segments+omit_endlist"; // hls_flags
    public static final String HLS_FLAGS_SECOND = "delete_segments+append_list+omit_endlist"; // hls_flags
    public static final String HLS_COPY = "-c:v";
    public static final String HLS_COPY_VALUE = "copy";
}
