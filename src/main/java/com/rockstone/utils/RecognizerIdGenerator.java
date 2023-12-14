package com.rockstone.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RecognizerIdGenerator {

    public static String generateRandomRecognizerId() {
        String randomString = RandomStringUtils.random(30, "abcdefghijklmnopqrstuvwxyz0123456789-");

        if (randomString.matches("[a-z]([a-z0-9-]{0,61}[a-z0-9])")) {
            return randomString;
        }
        else {
            return generateRandomRecognizerId();
        }
    }
}