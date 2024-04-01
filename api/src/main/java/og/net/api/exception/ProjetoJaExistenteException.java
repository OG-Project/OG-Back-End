package og.net.api.exception;

public class ProjetoJaExistenteException extends Exception{

    public ProjetoJaExistenteException(){
        super("Projeto ja existe!!");
    }
}
