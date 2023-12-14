package com.rockstone.service.impl;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v2.AutoDetectDecodingConfig;
import com.google.cloud.speech.v2.CreateRecognizerRequest;
import com.google.cloud.speech.v2.OperationMetadata;
import com.google.cloud.speech.v2.RecognitionConfig;
import com.google.cloud.speech.v2.RecognizeRequest;
import com.google.cloud.speech.v2.RecognizeResponse;
import com.google.cloud.speech.v2.Recognizer;
import com.google.cloud.speech.v2.SpeechClient;
import com.google.protobuf.ByteString;
import com.rockstone.service.ParseVoiceNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
@Slf4j
public class ParseVoiceNoteServiceImpl implements ParseVoiceNoteService {


    String projectId = "sustained-vial-384519";
    String filePath = "src/main/resources/testWav.wav";
    String recognizerId = "495376362694";
    String parent = String.format("projects/%s/locations/global", projectId);

    private SpeechClient createSpeechClient() {
        log.trace("Enter Method createSpeechClient");

        try (SpeechClient speechClient = SpeechClient.create()) {
            return  speechClient;
        }
        catch (Exception e) {
            log.error("Exit Method createRecognizer: an error occurred when trying to create recognizer", e);
            throw new RuntimeException("Failed to create recognizer");
        }
    }
    private Recognizer createRecognizer()  {
        log.trace("Enter Method createRecognizer");

        SpeechClient speechClient = createSpeechClient();

        try{
            Recognizer recognizer = Recognizer.newBuilder()
                    .setModel("latest_long")
                    .addLanguageCodes("en-US")
                    .build();

            CreateRecognizerRequest createRecognizerRequest = CreateRecognizerRequest.newBuilder()
                    .setParent(parent)
                    .setRecognizerId(recognizerId)
                    .setRecognizer(recognizer)
                    .build();

            OperationFuture<Recognizer, OperationMetadata> operationFuture =
                    speechClient.createRecognizerAsync(createRecognizerRequest);

            log.info("Exit Method createRecognizer: recognizer created successfully:{}", recognizer);
            return recognizer = operationFuture.get();
        }
        catch (Exception e) {
            log.error("Exit Method createRecognizer: an error occurred when trying to create recognizer", e);
            throw new RuntimeException("Failed to create recognizer");
        }
    }

    private RecognizeRequest createTranscriptionRequest() throws IOException {
        log.trace("Enter Method createTranscriptionRequest");

        Recognizer recognizer = createRecognizer();
        ByteString audioBytes = convertAudioToByteString();

        try{
            RecognitionConfig recognitionConfig = RecognitionConfig.newBuilder()
                    .setAutoDecodingConfig(AutoDetectDecodingConfig.newBuilder().build())
                    .build();
            System.out.println("Recognition configuration: " + recognitionConfig);

            RecognizeRequest request = RecognizeRequest.newBuilder()
                    .setConfig(recognitionConfig)
                    .setRecognizer(recognizer.getName())
                    .setContent(audioBytes)
                    .build();

            log.info("Exit Method createTranscriptionRequest: transcription request created successfully {} ", request);
            return  request;
        }
        catch (Exception e) {
            log.error("Exit Method createTranscriptionRequest: an error occurred when trying to create transcription", e);
            throw new RuntimeException("Failed to create transcription");
        }
    }

    private RecognizeResponse createRecognizeResponse(SpeechClient speechClient) throws IOException {
        log.trace("Enter Method createRecognizeResponse :{}", speechClient);

        RecognizeRequest request = createTranscriptionRequest();
        try{
            RecognizeResponse response = speechClient.recognize(request);
            log.info("Exit Method createRecognizeResponse: Recognize response created successfully: {}", response);
            return  response;
        }
        catch (Exception e) {
            log.error("Exit Method createRecognizeResponse: an error occurred when trying to create recognizer response", e);
            throw new RuntimeException("Failed to create recognize response");
        }
    }

    private ByteString convertAudioToByteString() throws IOException {
        Path path = Paths.get(filePath);
        byte[] data = Files.readAllBytes(path);
        ByteString audioBytes = ByteString.copyFrom(data);
        return  audioBytes;
    }
}
