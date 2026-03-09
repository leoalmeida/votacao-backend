package space.lasf.pautas.core.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(final String mensagem) {
        super(mensagem);
    }

    public BusinessException(final String mensagem, final Object... params) {
        super(String.format(mensagem, params));
    }
}
