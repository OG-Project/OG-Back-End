package og.net.api.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExceptionErrorProducer {

    @Value("${topicos.log.request.topic}")
    private String topicrequest;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String string) throws JsonProcessingException{
        kafkaTemplate.send(topicrequest, string);
    }
}
