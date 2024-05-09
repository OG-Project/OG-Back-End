package og.net.api.model.entity;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Formatter;
import java.util.List;

public class ValorDeserializer extends StdDeserializer<Valor> {

    JsonNode jsonNode;
    protected ValorDeserializer() {
        super(Valor.class);
    }

    @Override
    public Valor deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        Long id=null;
        jsonNode= deserializationContext.readTree(jsonParser);
        try {
             id = jsonNode.get("id").asLong();
        }catch (Exception e){
        }

        if(isPresent("texto")){
            String valorJson= jsonNode.get("texto").asText();
            return new Texto(id,valorJson);

        }else if(isPresent("data")){
            LocalDateTime data = null;
            if(!jsonNode.get("data").isNull()){
                String valorJson = jsonNode.get("data").asText();

                data = LocalDateTime.parse(valorJson);
            }
            return new Data(id,data);

        }else if(isPresent("numero")){
            Double valorJson= jsonNode.get("numero").asDouble();
            return new Numero(id,valorJson);
        }
        List<String> valoresJson= jsonNode.findValuesAsText("valores");
        return new Selecao(id, valoresJson);
    }

    private boolean isPresent(String texto){
        try{
            if(jsonNode.findParent(texto)!=null){
                return true;
            }else{
                throw new NullPointerException();
            }
        }catch (NullPointerException e){
            return false;
        }
    }
}
