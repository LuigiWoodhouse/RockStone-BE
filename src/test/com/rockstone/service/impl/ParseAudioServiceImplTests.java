package com.rockstone.service.impl;

import com.rockstone.service.TicketManagementService;
import com.rockstone.service.impl.ParseAudioServiceImpl;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ParseAudioServiceImplTests {

    @InjectMocks
    ParseAudioServiceImpl parseAudioServiceImpl;


    @Mock
    TicketManagementService ticketManagementService;
    ParseAudioServiceImpl parseAudioServiceImplMock =  Mockito.mock(ParseAudioServiceImpl.class);
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void convert_Audio_To_Text_Return_200() throws IOException {
//
//        byte[] audioFileBytes = IOUtils.toByteArray(getClass().getResourceAsStream("/testMp4.mp4"));
//        MultipartFile file = new MockMultipartFile("audioFile", "testMp4.mp4", "video/mp4", audioFileBytes);
//        String transcription = parseAudioServiceImpl.convertAudioToText(file);
//        when(ticketManagementService.saveTicketToDatabase(transcription))
//
//        assertNotNull(transcription);
//    }

    @Test
    public void convert_Audio_To_Text_Return_500() throws IOException {

        byte[] audioFileBytes = IOUtils.toByteArray(getClass().getResourceAsStream("/testMp4.mp4"));
        MultipartFile file = new MockMultipartFile("audioFile", "testMp4.mp4", "video/mp4", audioFileBytes);

        Mockito.doThrow(new RuntimeException("Failed to convert audio"))
                .when(parseAudioServiceImplMock)
                .convertAudioToText(file);


        Exception exception = assertThrows(RuntimeException.class, ()
                -> parseAudioServiceImplMock.convertAudioToText(file));

        assertEquals("Failed to convert audio", exception.getMessage());

        System.out.println("Expected=Failed to convert audio");
        System.out.println("Actual =" + exception.getMessage());
    }
}