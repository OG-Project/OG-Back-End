package og.net.api.exception;

public class UsuarioJaExistenteException extends Exception{

    public UsuarioJaExistenteException(){
        super("Já um usuário com esse cadastro!!");
    }
}
