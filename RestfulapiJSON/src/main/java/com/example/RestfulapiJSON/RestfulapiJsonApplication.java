package com.example.RestfulapiJSON;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Vue 개발 서버 주소
public class RestfulapiJsonApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulapiJsonApplication.class, args);
	}


    String hostname = hostnameService.getHostnameB();


    private List<Map<String, Object>> dataList = new ArrayList<>();
	// GitHub 테스트 입니다.
	
    // POST: 데이터 추가 
    @PostMapping("/data")
    public String createData(@RequestBody Map<String, String> request) {
    	Map<String, Object> data = new HashMap<>();
        int id = dataList.size() + 1;

        data.put("KeyNo", id);
        data.put("Value", request.get("value"));
        
        dataList.add(data);
        return "Data created with ID: " + id + ", JSON : " + data;
    }

    // PUT: 데이터 업데이트
    @PutMapping("/data/{id}")
    public String updateData(@PathVariable int id, @RequestBody Map<String, String> request) {
    	for (Map<String, Object> data : dataList) {
            if (data.get("KeyNo").equals(id)) {
                data.put("Value", request.get("value")); // 새로운 값으로 업데이트
                return "Data with ID " + id + " updated, JSON : "+ data;
            }
        }
        return "Data with ID " + id + " not found.";
    }

    // DELETE: 데이터 삭제
    @DeleteMapping("/data/{id}")
    public String deleteData(@PathVariable int id) {
    	for (Map<String, Object> data : dataList) {
            if (data.get("KeyNo").equals(id)) {
            	dataList.remove(data); // 데이터 삭제
                return "Data with ID " + id + " deleted.";
            }
        }
        return "Data with ID " + id + " not found.";
    }

    // GET: 데이터 조회 (테스트용)
    @GetMapping("/data/{id}")
    public String getData(@PathVariable int id) {
    	if (id == 0) {
            try {
                // ObjectMapper 인스턴스 생성
                ObjectMapper objectMapper = new ObjectMapper();
                
                // Map을 JSON 문자열로 변환
                return objectMapper.writeValueAsString(dataList);
            } catch (Exception e) {
                return e.getMessage();
            } 
    	} else {
        	for (Map<String, Object> data : dataList) {
                if (data.get("KeyNo").equals(id)) { 
                    return "Data :" + data.get("Value") + ", Hostname :" + hostname; // environment.getProperty("server.hostname");
                }
            }
            String rtnStr = "Data with ID " + id + " not found" + ", Hostname :" + hostname; // environment.getProperty("server.hostname");
            return rtnStr;
    	}
    }
}
