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
import com.google.cloud.speech.v2.SpeechRecognitionAlternative;
import com.google.cloud.speech.v2.SpeechRecognitionResult;
import com.google.protobuf.ByteString;
import com.rockstone.exception.TranscriptionException;
import com.rockstone.service.ParseAudioService;
import com.rockstone.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import static com.rockstone.utils.RecognizerIdGenerator.generateRandomRecognizerId;
@Service
@Slf4j
public class ParseAudioServiceImpl implements ParseAudioService {


    String projectId = "sustained-vial-384519";

    @Override
    public String convertAudioToText(MultipartFile audioFile) {
        log.trace("Enter Method convertAudioToText");


        String recognizerId = generateRandomRecognizerId();
        log.info("Generating recognizerId...{}", recognizerId);

        try{
            try (SpeechClient speechClient = SpeechClient.create()) {

                ByteString audioBytes = convertAudioToByteString(audioFile);
                log.info("Reading audio file...");

                Recognizer recognizer = createRecognizer(projectId, recognizerId, speechClient);
                log.info("Creating recognizer... {}",recognizer);

                RecognitionConfig recognitionConfig = createRecognitionConfig();
                log.info("Creating recognitionConfig... {}",recognitionConfig);

                RecognizeRequest request = createRecognizeRequest(recognizer, audioBytes, recognitionConfig);
                log.info("Create recognize request...:{}", request);

                RecognizeResponse response = speechClient.recognize(request);
                log.info("Create recognize response...:{}", response);

                String transcription = processTranscription(response);
                log.info("Exit Method convertAudioToText: audio converted to text successfully: {}", transcription);
                return  transcription;
            }
        }
        catch (Exception e) {
            log.error("Exit Method convertAudioToText: an error occurred when trying to convert audio to text", e);
            throw new RuntimeException("Failed to convert audio");
        }
    }

    private ByteString convertAudioToByteString(MultipartFile audioFile) throws IOException {
        try (InputStream inputStream = audioFile.getInputStream()) {
            byte[] data = IOUtils.toByteArray(inputStream);
            return ByteString.copyFrom(data);
        }
    }


    private Recognizer createRecognizer(String projectId, String recognizerId, SpeechClient speechClient)  {
        log.trace("Enter Method createRecognizer");
        String parent = String.format("projects/%s/locations/global", projectId);

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
            return operationFuture.get();
        }
        catch (Exception e) {
            log.error("Exit Method createRecognizer: an error occurred when trying to create recognizer", e);
            throw new RuntimeException("Failed to create recognizer");
        }
    }

    private RecognitionConfig createRecognitionConfig() {
        return RecognitionConfig.newBuilder()
                .setAutoDecodingConfig(AutoDetectDecodingConfig.newBuilder().build())
                .build();
    }

    private RecognizeRequest createRecognizeRequest(Recognizer recognizer, ByteString audioBytes,
                                                           RecognitionConfig recognitionConfig) {
        return RecognizeRequest.newBuilder()
                .setConfig(recognitionConfig)
                .setRecognizer(recognizer.getName())
                .setContent(audioBytes)
                .build();
    }

    private String processTranscription(RecognizeResponse response) throws TranscriptionException {
        log.trace("Enter Method processTranscription {}:", response);

        if (response.getResultsList().isEmpty()) {
            log.error("Exit Method processTranscription : no transcription found");
            throw new TranscriptionException(HttpStatus.NOT_FOUND.value(), Constants.NO_TRANSCRIPTION_FOUND);
        }
        try {
            List<SpeechRecognitionResult> results = response.getResultsList();
            for (SpeechRecognitionResult result : results) {
                log.info("Exit Method processTranscription: transcription processed successfully {}", result);
                String transcription = extractTranscriptionFromAudio(result);

                return transcription;
            }
        }
        catch (Exception e) {
            log.error("Exit Method processTranscription: an error occurred when trying to process translation", e);
            throw new RuntimeException("Failed to process translation");
        }
        return Constants.NO_TRANSCRIPTION_FOUND;
    }

    private String extractTranscriptionFromAudio(SpeechRecognitionResult result) {
        log.trace("Enter Method extractTranscriptionFromAudio");

        try{
            if (result.getAlternativesCount() > 0) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                log.info("Exit Method extractTranscriptionFromAudio: Transcription successfully extracted from audio {}",alternative.getTranscript());

                return alternative.getTranscript();
            }
            else {
                log.error("Exit Method extractTranscriptionFromAudio: transcription not in correct format");
                throw new TranscriptionException(HttpStatus.BAD_REQUEST.value(), Constants.INVALID_DATA_FORMAT);
            }
        }
        catch (Exception e) {
            log.error("Exit Method extractTranslationFromAudio: an error occurred when trying to extract translation", e);
            throw new RuntimeException("Failed to translate audio");
        }
    }
}