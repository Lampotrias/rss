package com.example.rss.presentation.global;

import android.view.View;

import com.example.rss.data.exception.DatabaseConnectionException;
import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.domain.exception.DefaultErrorBundle;
import com.example.rss.domain.exception.IErrorBundle;
import com.example.rss.presentation.exception.ErrorMessageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.navigation.NavController;
import com.example.rss.R;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

public class GlobalPresenter implements GlobalContract.P<GlobalContract.V> {

    private GlobalContract.V mView;
    private final GlobalInteractor globalInteractor;
    private final CompositeDisposable compositeDisposable;
    private final String CATEGORY_TYPE = "C";
    private final String FAVORITE_TYPE = "F";

    private List<Channel> mChannels = null;
    private List<Category> mCategories = null;

    private void setCategories(List<Category> mCategories) {
        this.mCategories = mCategories;
    }

    private void setChannels(List<Channel> mChannels) {
        this.mChannels = mChannels;
    }

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
        mView.openDrawer();
        prepareDataForMenu();
    }

    private void prepareDataForMenu() {
          compositeDisposable.add(
          globalInteractor.getCategoriesByType(CATEGORY_TYPE)
                .subscribe(categories -> {
                    this.setCategories(categories);
                    compositeDisposable.add(
                            globalInteractor.getAllChannels()
                            .subscribe(channels -> {
                                setChannels(channels);
                                initChannelListMenu();
                            }, throwable -> showErrorMessage(new DefaultErrorBundle(new DatabaseConnectionException())))
                    );
                }, throwable -> showErrorMessage(new DefaultErrorBundle(new DatabaseConnectionException())))
        );
    }

    private void initChannelListMenu(){
        if (!mChannels.isEmpty() && !mCategories.isEmpty()){
            final String attrParentTitle = "attrParentTitle";
            final String attrChildTitle = "attrChildTitle";

            List<Map<String, String>> groupData = new ArrayList<>();
            List<List<Map<String, String>>> childData = new ArrayList<>();

            Map<String, String> mCategoryTitle;
            Map<String, String> mChildTitle;
            List<Map<String, String>> tmpChild;

            for (Category category: mCategories) {

                for (Channel channel: mChannels) {
                    tmpChild = new ArrayList<>();
                    if (category.getCategoryId().equals(channel.getCategoryId())){
                        mChildTitle = new HashMap<>();
                        mChildTitle.put(attrChildTitle, channel.getTitle());
                        tmpChild.add(mChildTitle);
                    }
                    if (!tmpChild.isEmpty())
                        childData.add(tmpChild);
                }

                mCategoryTitle = new HashMap<>();
                mCategoryTitle.put(attrParentTitle, category.getName());
                groupData.add(mCategoryTitle);
            }

            mView.ShowChannelListMenu(groupData, childData, attrParentTitle, attrChildTitle);
        }
    }

    private void showErrorMessage(IErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.mView.context(), errorBundle.getException());
        mView.displayError(errorMessage);
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
        mView = null;
        globalActions = null;
    }


    public void OnClickChannelTest(View v) {
        navController.navigate(R.id.nav_testFragment);
        mView.closeDrawer();
    }
}
