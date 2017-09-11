package com.example.mylibrary.uiframwork.base.mvp.presenter;


import com.example.mylibrary.uiframwork.base.mvp.view.MvpView;

/**
 * Interface representing a Presenter in a model view presenter (MVP) pattern.
 */
public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);

    void detachView();

    V getView();

    void initialize();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    void resume();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    void pause();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    void destroy();
}
