package br.com.joaoretamero.popularmovies.domain.usecase;

public abstract class UseCase<T, H> {

    private UseCase.Callback<H> callback;

    public void setCallback(Callback<H> callback) {
        this.callback = callback;
    }

    public abstract void execute(T requestValue);

    protected void notifySuccess(H response) {
        if (callback != null) {
            callback.onSuccess(response);
        }
    }

    protected void notifyError(Throwable error) {
        if (callback != null) {
            callback.onError(error);
        }
    }

    public interface RequestValue {
    }

    public interface Callback<H> {
        void onSuccess(H response);

        void onError(Throwable error);
    }
}
