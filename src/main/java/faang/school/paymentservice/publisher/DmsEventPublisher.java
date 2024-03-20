package faang.school.paymentservice.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.paymentservice.dto.DmsEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DmsEventPublisher {
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    @Value("${spring.data.redis.channels.dms_channel.name}")
    private String dmsChannel;

    public void publish(DmsEvent dmsEvent) {
        try {
            String json = objectMapper.writeValueAsString(dmsEvent);
            redisTemplate.convertAndSend(dmsChannel, json);
        } catch (JsonProcessingException e) {
            log.error("Json processing exception ", e);
            throw new RuntimeException("Json processing exception");
        }
    }
}
