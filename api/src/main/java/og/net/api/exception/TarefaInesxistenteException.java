package og.net.api.exception;

public class TarefaInesxistenteException extends Exception{

    public TarefaInesxistenteException(){
        super("Essa tarefa não foi encontrada!!");
    }
}
