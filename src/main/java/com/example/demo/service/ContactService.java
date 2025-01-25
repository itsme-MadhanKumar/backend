package com.example.demo.service;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ContactService
{
    // Replace with your credentials and recipient details
    private static final String ACCESS_TOKEN = "EAANaV4k1LYoBO5d8vzJ6dyWZApZCWcePfo9g0DIfHKUzQwYldS0AOsTZA2yl9rdXUujZAywVgjDAePBkLsOq923ZCv2aOlvZCZCnFKJ3ciumvb8znmQxGuoLqXvpKM7z4gw5PIX7ZAqwMwMyZCiQWlt7rkaZCZBIk5zTnrMCzjf722K4ZBgCVSUXqGNHkhnQ8VBFPqu66QZDZD";
    private static final String PHONE_NUMBER_ID = "506479735890397"; // WhatsApp Business Number ID
    private static final String RECIPIENT_PHONE = "+919788358119"; // Replace with recipient's phone number

    public boolean sendWhatsAppMessage(String name, String email, String subject, String phone, String message) {
        try
        {
            // Construct the URL
            URL url = new URL("https://graph.facebook.com/v15.0/" + PHONE_NUMBER_ID + "/messages");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set request method and headers
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create the JSON payload
            String jsonPayload = String.format(
                    "{"
                            + "\"messaging_product\": \"whatsapp\","
                            + "\"to\": \"%s\","
                            + "\"type\": \"text\","
                            + "\"text\": {\"body\": \"Hello sir! You have an Enquiry.\\nName: %s\\nEmail: %s\\nSubject: %s\\nPhone Number: %s\\nMessage: %s\"}"
                            + "}",
                    RECIPIENT_PHONE, name, email, subject,phone,message
            );

            // Write the payload to the request body
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonPayload.getBytes());
                os.flush();
            }

            // Check the response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                System.out.println("Message sent successfully!");
                return true;
            }
            else
            {
                // If the response code is not 200 (OK), handle the error response
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    errorResponse.append(line);
                }
                br.close();
                System.out.println("Failed to send message. HTTP response code: " + responseCode);
                System.out.println("Error Response: " + errorResponse.toString());
                System.out.println("Failed to send message. HTTP response code: " + responseCode);
            }

            conn.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("An error occurred while sending the WhatsApp message.");
        }
        return false;
    }
}
