package soyoung.eventrestapi.events;

import org.modelmapper.ModelMapper;
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

    // 생성자 주입
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    //@Autowired : 생성자가 하나만 존재하고 생성자의 파라미터가 bean으로 등록되어있으면 생략가능
    public EventController(EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }


    @PostMapping
    public ResponseEntity createEvent(@RequestBody EventDto eventDto) {
//        URI createdUri = linkTo(methodOn(EventController.class).createEvent(null)).slash("{id}").toUri(); // mehtodOn을 안 쓰게 된 계기
//        return ResponseEntity.created(createdUri).build();

        // EventDto 값 -> Event (eventRepository 사용하기 위해)
//        Event event = Event.builder()
//                .name(eventDto.getName())
//                .description(eventdto.getDescription())
//                .build();

        // modelMapper의 map으로 위에서 한 거를 한 번에 옮길 수 있음 (reflection 발생 가능) - 의존성 추가 해야 함
        Event event = modelMapper.map(eventDto, Event.class);

        Event newEvent = this.eventRepository.save(event);
        URI createdUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
        return ResponseEntity.created(createdUri).body(event);
    }
}
