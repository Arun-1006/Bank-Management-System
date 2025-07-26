package bank.management.system;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

public class OtpSender {

    public static int sendOtp(String phone) {
        try {
            // Generate 6-digit OTP
            int otp = 100000 + new Random().nextInt(900000);

            String apiKey = "BxtM7spmX8de17Yv9JEV6Xvqeyzyt7FAIUjaR7UKFlVvEGpHwM6IMIVI2ryJ"; // Replace with your API key
            String message = "Your OTP is: " + otp;

            // Fast2SMS URL
            String url = "https://www.fast2sms.com/dev/bulkV2";
            String data = "sender_id=TXTIND&message=" + message +
                    "&language=english&route=v3&numbers=" + phone;

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("authorization", apiKey);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                Scanner sc = new Scanner(conn.getInputStream());
                while (sc.hasNext()) {
                    System.out.println(sc.nextLine());
                }
                sc.close();
                return otp;
            } else {
                System.out.println("Failed to send OTP. Response Code: " + responseCode);
                return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
