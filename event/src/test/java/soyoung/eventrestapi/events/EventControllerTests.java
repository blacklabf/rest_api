package soyoung.eventrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
                .build();
//        event.setId(10); - Id를 10으로 mocking
        // nullpointException 아래에서 mocking을 할 떄 event를 저장할 때 event를 리턴하게 했는데 - 실제 event를 받는게 아니라 실제로 save에 전달한 객체는 createEvent에서 새로 만든 object (여기 위에 event가 아님)
//        Mockito.when(eventRepository.save(event)).thenReturn(event); // nullPointerException 방지

        // mocking 안 함

//        mocMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON).content(objectMapper.writeValueAsString(event))).andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("id").exists()).andExpect(header().exists("Location")) // Location header 있는지 확인
//                .andExpect(header().string("Content-Type", "application/hal+json"));

        mocMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON).content(objectMapper.writeValueAsString(event))).andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("id").exists()).andExpect(header().exists(HttpHeaders.LOCATION)) // Location header 있는지 확인
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)));
    }


}
