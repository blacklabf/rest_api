package soyoung.eventrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
//@WebMvcTest // slicing Test
@SpringBootTest// 실제 repository 값 사용해서 적용 됨.
@AutoConfigureMockMvc
public class EventControllerTests { // 단위테스트는 아님 (9강-2:40)

    @Autowired
    MockMvc mocMvc; // SpringBootTest - MockMVC용 테스트가 설정

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createEvent() throws Exception {


        EventDto event = EventDto.builder().name("Spring").description("REST API Development with Spring").beginEnrollmentDateTime(LocalDateTime.of(2024, 01, 16, 14, 21)).closeEnrollmentDateTime(LocalDateTime.of(2024, 01, 17, 22, 40)).beginEventDateTime(LocalDateTime.of(2024, 01, 18, 11, 20)).endEventDateTime(LocalDateTime.of(2024, 01, 20, 11, 20)).basePrice(100).maxPrice(200).limitOfEnrollment(100).location("강남역").build();

        mocMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON).content(objectMapper.writeValueAsString(event))).andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("id").exists()).andExpect(header().exists(HttpHeaders.LOCATION)) // Location header 있는지 확인
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE)).andExpect(jsonPath("id").value(Matchers.not(100))).andExpect(jsonPath("free").value(Matchers.not(true))).andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }


    @Test
    public void createEvent_Bad_Request() throws Exception {

        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2024, 01, 16, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2024, 01, 17, 22, 40))
                .beginEventDateTime(LocalDateTime.of(2024, 01, 18, 11, 20))
                .endEventDateTime(LocalDateTime.of(2024, 01, 20, 11, 20))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        mocMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }


}
