package com.siaron.grpc.server;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.HelloRequest;
import com.example.grpc.HelloResponse;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

/**
 * @author xielongwang
 * @create 2019-03-302:03 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class GrpcDemoServer {

    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(8089).addService(new GreetingServiceImpl()).build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }


    public static class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
        @Override
        public void greeting(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
            System.out.println(request);
            String greeting = "Hello there, " + request.getName();

            HelloResponse handleRes = HelloResponse.newBuilder().setGreeting(greeting).build();

            responseObserver.onNext(handleRes);
            responseObserver.onCompleted();
        }
    }

}