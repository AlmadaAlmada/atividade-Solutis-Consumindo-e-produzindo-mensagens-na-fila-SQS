package tech.buildrun.sqs;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.net.URI;

public class SqsProducer {

    private static final String QUEUE_URL = "https://sqs.sa-east-1.amazonaws.com/000000000000/minha-fila";

    public static void main(String[] args) {

        // Cria o cliente SQS
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                        "fakeAccessKeyId",
                        "fakeSecretAccessKey")))
                .endpointOverride(URI.create("http://localhost:4566")) // URL do LocalStack
                .build();

        // Envia uma mensagem
        sendMessage(sqsClient, "Olá, esta é uma mensagem de teste!");

        // Fecha o cliente
        sqsClient.close();
    }

    public static void sendMessage(SqsClient sqsClient, String message) {
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(QUEUE_URL)
                .messageBody(message)
                .delaySeconds(0)
                .build();

        sqsClient.sendMessage(sendMsgRequest);
        System.out.println("Mensagem enviada: " + message);
    }
}
