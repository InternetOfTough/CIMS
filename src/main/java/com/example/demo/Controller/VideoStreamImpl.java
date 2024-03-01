package com.example.demo.Controller;

import com.example.demo.Service.ConvertService;
import com.example.demo.common.FileUtils;
import com.example.demo.repository.CCTVMapper;
import io.grpc.stub.StreamObserver;
import java.io.File;
import net.devh.boot.grpc.server.service.GrpcService;
import org.cctv.libs.Frame;
import org.cctv.libs.ServerMessage;
import org.cctv.libs.StreamingGrpc.StreamingImplBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


@GrpcService
public class VideoStreamImpl extends StreamingImplBase {

    @Autowired
    CCTVMapper cctvMapper;

    @Autowired
    WebSocketController webSocketController;

    private final ConvertService convertService;

    @Value("${hls.output.location}")
    private String HLS_OUTPUT_PATH;

    @Autowired
    public VideoStreamImpl(ConvertService convertService) {
        this.convertService = convertService;
    }

    @Override
    public StreamObserver<Frame> streamVideo(StreamObserver<ServerMessage> responseObserver) {

        return new StreamObserver<Frame>() {
            int index = 0;
            String piName;

            //변수 처리
            @Override
            public void onNext(Frame frame) {
                // 클라이언트로부터 받은 각각의 프레임 처리
                piName = frame.getName();
                String status = frame.getStatus();
                String vision = frame.getVision();
                System.out.println("piName =" + piName);
                System.out.println("status =" + status);
                System.out.println("vision =" + vision);

                convertService.convertToHls(index, piName, frame.getData().toByteArray());

                index++;

                webSocketController.sendVisionMsg(piName, status, vision);
            }

            @Override
            public void onError(Throwable t) {
                // 에러 발생 시 처리
                FileUtils.deleteDirectory(new File(HLS_OUTPUT_PATH + piName));
                System.out.println(t.getMessage());
                webSocketController.sendDiscconnectedMsg(piName);
            }

            @Override
            public void onCompleted() {
                // 클라이언트 요청 완료 시 처리
                responseObserver.onNext(ServerMessage.getDefaultInstance());
                responseObserver.onCompleted();
                System.out.println("client disconnectd!");
            }
        };

    }
}
