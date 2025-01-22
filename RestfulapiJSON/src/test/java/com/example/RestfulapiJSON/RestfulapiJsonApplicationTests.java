package com.example.RestfulapiJSON;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.http.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestfulapiJsonApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(RestfulapiJsonApplicationTests.class);
	
    @Autowired
    private MockMvc mockMvc;
    
	@Test
	void contextLoads() throws Exception  {
		// 테스트 시나리오 : 입력 --> 조회 --> 수정 --> 삭제 --> 조회 
		 
		// 테스트 Value 
        int  lvKeyNo = 1;
        String lvValue ="JUnit 테스트";
        String lvNewValue ="Update 데이터";
        String requestBody ="";

        // 입력Test)MockMvc로 HTTP POST 요청 수행 및 결과 검증
        logger.info(">>입력Test)MockMvc로 HTTP POST 요청 수행 및 결과 검증");
        requestBody = "{\"KeyNo\": " + lvKeyNo + ", \"value\": \""+lvValue+"\"}";
        mockMvc.perform(post("/api/data")
                .contentType(MediaType.APPLICATION_JSON) // 요청 Content-Type 설정
                .content(requestBody)) // 요청 본문 데이터 전송
                .andExpect(status().isOk()) // HTTP 200 상태 확인
                .andExpect(content().string("Data created with ID: " + lvKeyNo + ", JSON : {KeyNo="+lvKeyNo+", Value="+lvValue+"}")); // 응답 내용 검증 
        
        // 조회Test)MockMvc로 HTTP GET 요청 수행 및 결과 검증
        logger.info(">>조회Test)MockMvc로 HTTP GET 요청 수행 및 결과 검증");
        mockMvc.perform(get("/api/data/{id}", lvKeyNo))
                .andExpect(status().isOk()) // HTTP 200 OK 상태 확인
                .andExpect(content().string("Data :" + lvValue)); // 응답 내용 확인		

        // 수정Test)MockMvc로 HTTP POST 요청 수행 및 결과 검증
        logger.info(">>수정Test)MockMvc로 HTTP POST 요청 수행 및 결과 검증");
        requestBody = "{\"value\": \""+lvNewValue+"\"}";
        mockMvc.perform(put("/api/{id}", lvKeyNo)
                .contentType(MediaType.APPLICATION_JSON) // 요청 Content-Type 설정
                .content(requestBody)) // 요청 본문 데이터 전송
                .andExpect(status().isOk()) // HTTP 200 상태 확인
                .andExpect(content().string("Data with ID " + lvKeyNo + " updated, JSON : {KeyNo="+lvKeyNo+", Value="+lvValue+"}")); // 응답 내용 검증 

        // 삭제Test)MockMvc로 HTTP POST 요청 수행 및 결과 검증
        logger.info(">>수정Test)MockMvc로 HTTP POST 요청 수행 및 결과 검증"); 
        requestBody = "";
        mockMvc.perform(delete("/api/{id}", lvKeyNo)
                .contentType(MediaType.APPLICATION_JSON) // 요청 Content-Type 설정
                .content(requestBody)) // 요청 본문 데이터 전송        		
                .andExpect(status().isOk()) // HTTP 200 상태 확인
                .andExpect(content().string("Data with ID " + lvKeyNo + " deleted.")); // 응답 내용 검증 

        // 조회Test)MockMvc로 HTTP GET 요청 수행 및 결과 검증
        logger.info(">>조회Test)MockMvc로 HTTP GET 요청 수행 및 결과 검증");
        mockMvc.perform(get("/api/data/{id}", lvKeyNo))
                .andExpect(status().isOk()) // HTTP 200 OK 상태 확인
                .andExpect(content().string("Data with ID " + lvKeyNo + " not found.")); // 응답 내용 확인		

        logger.info(">>JUnit 테스트 시나리오 Pass! ^____^");
	}

}
