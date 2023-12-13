package com.rockstone;


import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v2.AutoDetectDecodingConfig;
import com.google.cloud.speech.v2.CreateRecognizerRequest;
import com.google.cloud.speech.v2.OperationMetadata;
import com.google.cloud.speech.v2.RecognitionConfig;
import com.google.cloud.speech.v2.RecognizeRequest;
import com.google.cloud.speech.v2.RecognizeResponse;
import com.google.cloud.speech.v2.Recognizer;
import com.google.cloud.speech.v2.SpeechClient;
import com.google.cloud.speech.v2.SpeechRecognitionAlternative;
import com.google.cloud.speech.v2.SpeechRecognitionResult;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException, ExecutionException,
            InterruptedException {
        Logger.getLogger("io.grpc").setLevel(Level.FINEST);
        String projectId = "sustained-vial-384519";
        String filePath = "src/main/resources/testWav.wav";
        String name = "my name is Luigi";
        String recognizerId = "495376362694";
        log.debug("Transcript : %s {}", name);
        quickstartSampleV2(projectId, filePath, recognizerId);


    }

    public static void quickstartSampleV2(String projectId, String filePath, String recognizerId)
            throws IOException, ExecutionException, InterruptedException {
//        PythonInterpreter pyInterp = new PythonInterpreter();
//
//        String command = "src/main/resources/whisper_api.py";
//        pyInterp.exec(command);


//        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
//        Process process = processBuilder.start();

//        System.out.println("what is here \n" + process);


        try (SpeechClient speechClient = SpeechClient.create()) {
            Path path = Paths.get(filePath);
            byte[] data = Files.readAllBytes(path);
            ByteString audioBytes = ByteString.copyFrom(data);
            System.out.printf("File size: %d bytes%n", data.length);
            String parent = String.format("projects/%s/locations/global", projectId);

            // First, create a recognizer
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
            recognizer = operationFuture.get();

            // Check if the operation was successful
            if (operationFuture.isDone()) {
                System.out.println("Recognizer created successfully: " + recognizer);
            } else {
                System.err.println("Error creating recognizer: ");
                return;
            }

            // Next, create the transcription request
            RecognitionConfig recognitionConfig = RecognitionConfig.newBuilder()
                    .setAutoDecodingConfig(AutoDetectDecodingConfig.newBuilder().build())
                    .build();
            System.out.println("Recognition configuration: " + recognitionConfig);

            RecognizeRequest request = RecognizeRequest.newBuilder()
                    .setConfig(recognitionConfig)
                    .setRecognizer(recognizer.getName())
                    .setContent(audioBytes)
                    .build();



            System.out.println("Audio content size: " + audioBytes.size());
            log.debug("audio content size: {}", audioBytes.size());

            log.debug("Recognition request " + request);


            RecognizeResponse response = speechClient.recognize(request);
            System.out.println("API Response: " + response);
            log.debug("API Response: {}", response);


            if (response.getResultsList().isEmpty()) {
                System.err.println("No transcription results found.");
                return;
            }
            System.out.println("Transcription Results: " + response.getResultsList());
            log.trace("Transcription Results: " + response.getResultsList());
            log.info("Transcription Results: " + response.getResultsList());
            log.debug("Transcription Results: " + response.getResultsList());



            List<SpeechRecognitionResult> results = response.getResultsList();

            for (SpeechRecognitionResult result : results) {
                // There can be several alternative transcripts for a given chunk of speech. Just use the
                // first (most likely) one here.
                if (result.getAlternativesCount() > 0) {
                    SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                    System.out.printf("Transcription: %s%n", alternative.getTranscript());

                    log.debug("Transcription: %s%n {}", alternative.getTranscript());
                    log.debug("Transcription: %s%n {}", alternative.getTranscript());
                }
            }
        }
    }
}