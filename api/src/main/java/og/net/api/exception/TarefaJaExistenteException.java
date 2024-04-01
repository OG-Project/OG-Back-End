package og.net.api.exception;

public class TarefaJaExistenteException extends Exception{

    public TarefaJaExistenteException(){
        super("Essa tarefa ja foi cadastrada!!");
    }
}
