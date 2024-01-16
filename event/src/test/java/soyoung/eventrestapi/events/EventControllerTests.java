package soyoung.eventrestapi.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest // web과 관련된 bean들 모두 등록
public class EventControllerTests { // 단위테스트는 아님 (9강-2:40)

    @Autowired
    MockMvc mocMvc; // spring MVC

    @Test
    public void createEvent() throws Exception {
        mocMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON)
                )
                .andExpect(status().isCreated());
    }



}
