package com.example.RestfulapiJSON;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

@Component
public class hostnameService {

    @Value("${server.hostname}")
    private static String hostname;

    public static String getHostname() {
        return hostname;
    }

    public static String getHostnameB() {
        String hostname = "";
        try {
            // "hostname -f" 명령어 실행
            ProcessBuilder processBuilder = new ProcessBuilder("hostname", "-f");
            Process process = processBuilder.start();

            // 출력 결과 읽기
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            hostname = reader.readLine(); // 첫 번째 줄 읽기

            reader.close();

            // 프로세스 종료 기다리기
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("명령어 실행 실패, 종료 코드: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("호스트명 가져오기 실패", e);
        }

        return hostname;
    }
}
