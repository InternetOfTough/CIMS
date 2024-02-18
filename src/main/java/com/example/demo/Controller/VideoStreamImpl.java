package com.example.demo.Controller;

import com.example.demo.Service.ConvertService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.cctv.libs.Frame;
import org.cctv.libs.ServerMessage;
import org.cctv.libs.StreamingGrpc.StreamingImplBase;
import org.springframework.beans.factory.annotation.Autowired;


@GrpcService
public class VideoStreamImpl extends StreamingImplBase {

    private final ConvertService convertService;

    @Autowired
    public VideoStreamImpl(ConvertService convertService) {
        this.convertService = convertService;
    }

    @Override
    public StreamObserver<Frame> streamVideo(StreamObserver<ServerMessage> responseObserver) {

        return new StreamObserver<Frame>() {
            int index = 0;

            //변수 처리
            @Override
            public void onNext(Frame frame) {
                // 클라이언트로부터 받은 각각의 프레임 처리
                String piName = frame.getName();
                String status = frame.getStatus();

                System.out.println("piName =" + piName);
                System.out.println("status =" + status);

                convertService.convertToHls(index, piName, frame.getData().toByteArray());

                index++;
            }

            @Override
            public void onError(Throwable t) {
                // 에러 발생 시 처리
                System.out.println(t.getMessage());
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
