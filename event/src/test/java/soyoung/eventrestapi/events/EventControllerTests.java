package soyoung.eventrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
@WebMvcTest // web과 관련된 bean들 모두 등록
public class EventControllerTests { // 단위테스트는 아님 (9강-2:40)

    @Autowired
    MockMvc mocMvc; // spring MVC

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EventRepository eventRepository;

    @Test
    public void createEvent() throws Exception {


        Event event = Event.builder().name("Spring").description("REST API Development with Spring").beginEnrollmentDateTime(LocalDateTime.of(2024, 01, 16, 14, 21)).closeEnrollmentDateTime(LocalDateTime.of(2024, 01, 17, 22, 40)).beginEventDateTime(LocalDateTime.of(2024, 01, 18, 11, 20)).endEventDateTime(LocalDateTime.of(2024, 01, 20, 11, 20)).basePrice(100).maxPrice(200).limitOfEnrollment(100).location("강남역").build();
        event.setId(10);
        Mockito.when(eventRepository.save(event)).thenReturn(event); // nullPointerException 방지

//        mocMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON).content(objectMapper.writeValueAsString(event))).andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("id").exists()).andExpect(header().exists("Location")) // Location header 있는지 확인
//                .andExpect(header().string("Content-Type", "application/hal+json"));

        mocMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON).content(objectMapper.writeValueAsString(event))).andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("id").exists()).andExpect(header().exists(HttpHeaders.LOCATION)) // Location header 있는지 확인
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE));
    }


}
