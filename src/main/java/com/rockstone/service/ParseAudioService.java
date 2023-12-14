package com.rockstone.service;

import com.google.protobuf.ByteString;
import com.rockstone.exception.TranscriptionException;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
public interface ParseAudioService {
    String convertAudioToText() throws IOException, TranscriptionException;

    ByteString convertAudioToByteString(MultipartFile audioFile) throws IOException;
}
