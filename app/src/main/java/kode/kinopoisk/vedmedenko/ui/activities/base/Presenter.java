package kode.kinopoisk.vedmedenko.ui.activities.base;

public interface Presenter<V extends MvpView> {

    void attach(V mvpView);

    void detach();

}
