package og.net.api.exception;

public class UsuarioInesxistenteException extends Exception{

    public UsuarioInesxistenteException(){
        super("Usuário não encontrado!!");
    }
}
