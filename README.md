## Introduction

This is a spring boot app that is built and compiled in Java 11.
Spring Boot version is 2.3.4.RELEASE

## How to start app

1. Ensure your java home path is set to java 11 jdk
2. Ensure your ide sdk is set to java 11
3. Locate the main class from the package "com.rockstone" , configure your run configs to be java 11

## Features

1. Convert audio to text
  - To use this API, you must be given access permission from the owner by providing your google email address
  - When this is done, you will get an email from the owner  with a link for you to click and accept invitation
  - Download and install GoogleCloudSDKInstaller and then run the following command in cmd : gcloud auth application-default login
  - The command will then redirect you to google to authenticate using your email address
  - when authentication is successful, you will be able to use the endpoint.

2. Create & Fetch ticket
  - ticket is automatically created when an audio is converted to text.
  - all tickets can be fetched by accessing another endpoint.