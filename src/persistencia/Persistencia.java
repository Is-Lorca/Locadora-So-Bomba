package persistencia;

public abstract class Persistencia {
    abstract void salvar();
    abstract void carregar();
    abstract void atualizar();
    abstract void remover();
}
