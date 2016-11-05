package kode.kinopoisk.vedmedenko.ui.activities.base;

public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;

    @Override
    public void attach(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detach() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }
}
