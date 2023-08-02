package com.spring.labs.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.labs.domain.web.dto.PostDto;
import com.spring.labs.util.ResponseData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PostDto createDto() {
        return new PostDto(null, "Test Title", "Test Content", "tag1, tag2", null, null, null);
    }

    @Test
    @DisplayName("Post 저장")
    public void testCreateNewPost() throws Exception {
        // given //
        PostDto postDto = createDto();
        String postDtoJson = objectMapper.writeValueAsString(postDto);

        // when //
        // mockMvc를 통해서 post 형태의 가짜 통신 실행
        // content : 매개변수로 전달할 항목
        // contentType : 전달할 타입을 JSON 형태로 지정
        // andExpect : 기대하는 통신 상태(200)
        // andReturn : 통신 후 결과값 반환(ResponseData.ApiResult<?>)
        MvcResult result = mockMvc.perform(post("/api/post")
                        .content(postDtoJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Mock 테스트를 통해 받은 결과 중 응답 결과를 문자열로 반환
        String responseJson = result.getResponse().getContentAsString();

        // 매퍼를 통해 반환 받은 문자열을 ResponseData.ApiResult 타입으로 변환
        ResponseData.ApiResult<?> response = objectMapper.readValue(responseJson, ResponseData.ApiResult.class);

        // ResponseData 안에 있는 data를 꺼내서 PostDto 타입으로 변환
        PostDto dto = objectMapper.convertValue(response.data(), PostDto.class);

        // then //
        // JSON 형태로 출력
        System.out.println("\n response = " + objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(dto)+ "\n");

        // 반환된 데이터 검증
        assertThat(dto.title()).isEqualTo("Test Title");
    }

}