package og.net.api.exception;

public class ChatNaoEncontradoException extends Exception{

    public ChatNaoEncontradoException() {
        super("Chat não existe no banco");
    }
}
