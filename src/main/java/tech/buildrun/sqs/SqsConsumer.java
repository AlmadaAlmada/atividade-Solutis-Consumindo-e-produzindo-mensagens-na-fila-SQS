package tech.buildrun.sqs;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.net.URI;
import java.util.List;

public class SqsConsumer {

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

        // Recebe e processa mensagens
        receiveMessages(sqsClient);

        // Fecha o cliente
        sqsClient.close();
    }

    public static void receiveMessages(SqsClient sqsClient) {
        ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                .queueUrl(QUEUE_URL)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(10) // Long polling
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

        for (Message message : messages) {
            System.out.println("Mensagem recebida: " + message.body());

            // Exclui a mensagem da fila após o processamento
            DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                    .queueUrl(QUEUE_URL)
                    .receiptHandle(message.receiptHandle())
                    .build();
            sqsClient.deleteMessage(deleteRequest);
        }
    }
}

