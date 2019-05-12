package com.siaron.grpc.client;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.HelloRequest;
import com.example.grpc.HelloResponse;
import com.example.grpc.Sentiment;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author xielongwang
 * @create 2019-03-302:25 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class GrpcDemoClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8089)
                .usePlaintext()
                .build();

        GreetingServiceGrpc.GreetingServiceBlockingStub stub =
                GreetingServiceGrpc.newBlockingStub(channel);

        HelloResponse helloResponse = stub.greeting(HelloRequest.newBuilder().setAge(1).setName("11").setSentiment(Sentiment.HAPPY).build());

        System.out.println(helloResponse);

        channel.shutdown();
    }
}