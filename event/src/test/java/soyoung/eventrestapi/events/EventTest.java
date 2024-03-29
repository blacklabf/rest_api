package soyoung.eventrestapi.events;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test // builder가 있는지 확인
    public void builder(){
        Event event = Event.builder()
                .name("Spring REST API")
                .description("RSET API development with Spring")
                .build();
        assertThat(event).isNotNull();
    }

    @Test // java bean 준수 - default생성자, getter, setter
    public void javaBean(){
        // Given
        String name = "Event";
        String description = "Spring";

        // When
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        // Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }


}