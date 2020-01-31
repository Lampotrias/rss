package com.example.rss.presentation.global;

import android.view.View;
import androidx.navigation.NavController;
import com.example.rss.R;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

public class GlobalPresenter implements GlobalContract.P<GlobalContract.V> {

    private GlobalContract.V mView;
    private GlobalInteractor globalInteractor;
    private CompositeDisposable compositeDisposable;

    @Inject
    GlobalActions globalActions;

    @Inject
    NavController navController;

    @Inject
    public GlobalPresenter(GlobalInteractor globalInteractor) {
        this.globalInteractor = globalInteractor;
        compositeDisposable = new CompositeDisposable();
//
    }

    @Override
    public void setView(GlobalContract.V view) {
        mView = view;
    }

    @Override
    public void OnClickChannelAdd(View view) {
        navController.navigate(R.id.nav_channel_edit_fragment);
        mView.closeDrawer();
    }

    @Override
    public void openSettingsFragment() {
        navController.navigate(R.id.nav_settingsFragment);
    }

    @Override
    public void resume() {
        globalActions.setTitle("Main fragment");
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
