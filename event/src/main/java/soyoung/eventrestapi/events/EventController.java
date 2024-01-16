package soyoung.eventrestapi.events;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.hateoas.MediaTypes;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Controller
@RequestMapping(value="/api/events", produces = MediaTypes.HAL_JSON_VALUE) // 여기안에 handler는 이제 HAL JSON으로 응답
public class EventController {

    @PostMapping
    public ResponseEntity createEvent(@RequestBody Event event) {
//        URI createdUri = linkTo(methodOn(EventController.class).createEvent(null)).slash("{id}").toUri(); // mehtodOn을 안 쓰게 된 계기
//        return ResponseEntity.created(createdUri).build();
        URI createdUri = linkTo(EventController.class).slash("{id}").toUri();
        event.setId(10); // body에 event 보내도 id는 setting이 안됨
        return ResponseEntity.created(createdUri).body(event);
    }
}
