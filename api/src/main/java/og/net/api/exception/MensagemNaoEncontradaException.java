package og.net.api.exception;

public class MensagemNaoEncontradaException extends Exception{

    public MensagemNaoEncontradaException() {
        super("Mensagem não existe no banco");
    }
}
