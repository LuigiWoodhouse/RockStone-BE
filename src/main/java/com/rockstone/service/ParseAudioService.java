package com.rockstone.service;

import com.rockstone.exception.TranscriptionException;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
public interface ParseAudioService {
    String convertAudioToText(MultipartFile audioFile) throws IOException, TranscriptionException;
}