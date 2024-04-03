package og.net.api.model.Factory;

import og.net.api.model.entity.*;

import java.time.LocalDateTime;

public class ValorFactory {

    public static Valor getValor(Tipo tipoPropriedade){
        switch (tipoPropriedade){
            case DATA -> {
                return new Data(null, LocalDateTime.now());
            }
            case TEXTO -> {
                return new Texto(null,"");
            }
            case NUMERO -> {
                return new Numero(null,null);
            }
            case SELECAO -> {
                return new Selecao(null,null);
            }
        }
        throw new RuntimeException();
    }


}
