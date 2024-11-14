package an.kondratev.orders.controller;

import an.kondratev.orders.kafka.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class MessagesController {

    private final KafkaProducer producer;

    @PostMapping("kafka/send_order/{id}")
    public String sendOrder(@PathVariable("id") Long id) {
        return producer.sendOrderById(id);
    }
}