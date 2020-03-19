package com.example.rss.presentation.global;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;

import com.example.rss.R;
import com.example.rss.data.exception.DatabaseConnectionException;
import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.domain.exception.DefaultErrorBundle;
import com.example.rss.domain.exception.IErrorBundle;
import com.example.rss.domain.interactor.CategoryInteractor;
import com.example.rss.domain.interactor.ChannelInteractor;
import com.example.rss.domain.interactor.ItemInteractor;
import com.example.rss.presentation.channelEdit.ChannelEditFragment;
import com.example.rss.presentation.exception.ErrorMessageFactory;
import com.example.rss.presentation.itemList.ItemListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.disposables.CompositeDisposable;

public class GlobalPresenter implements GlobalContract.P<GlobalContract.V> {

    private GlobalContract.V mView;
    private final ChannelInteractor channelInteractor;
    private final CategoryInteractor categoryInteractor;
    private final ItemInteractor itemInteractor;
    private final CompositeDisposable compositeDisposable;
    private final String CATEGORY_TYPE = "C";
    private final String FAVORITE_TYPE = "F";

    private List<Channel> mChannels = null;
    private List<Category> mCategories = null;
    private List<List<Map<String, String>>> channelTreeData;

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
    public GlobalPresenter(ChannelInteractor channelInteractor, CategoryInteractor categoryInteractor, ItemInteractor itemInteractor) {
        this.channelInteractor = channelInteractor;
        this.categoryInteractor = categoryInteractor;
        this.itemInteractor = itemInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void setView(GlobalContract.V view) {
        mView = view;
        globalActions.updDrawerMenu();

        InitBackgroundWork work = new InitBackgroundWork(mView.context());
	    work.createAllTasks();
    }

    private void reCalcMenu() {
        compositeDisposable.add(
                categoryInteractor.getCategoriesByType(CATEGORY_TYPE)
                        .subscribe(categories -> {
                                    this.setCategories(categories);
                                    compositeDisposable.add(
                                            channelInteractor.getAllChannels()
                                                    .subscribe(channels -> {
                                                                setChannels(channels);
                                                                initChannelListMenu();
                                                            }, throwable -> showErrorMessage(new DefaultErrorBundle(new DatabaseConnectionException()))
                                                            , this::initChannelListMenu)
                                    );
                                }, throwable -> showErrorMessage(new DefaultErrorBundle(new DatabaseConnectionException()))
                                , this::initChannelListMenu)
        );
    }

    private void initChannelListMenu() {
        final String attrParentTitle = "attrParentTitle";
        final String attrChildTitle = "attrChildTitle";
        final String attrChildId = "attrChildId";

        channelTreeData = new ArrayList<>();
        List<Map<String, String>> groupData = new ArrayList<>();

        if (!mChannels.isEmpty() && !mCategories.isEmpty()) {
            Category category1 = new Category();
            category1.setCategoryId(33L);
            category1.setName("test2");
            category1.setType(CATEGORY_TYPE);

            Channel channel1 = new Channel();
            channel1.setChannelId(55L);
            channel1.setTitle("test123");
            channel1.setCategoryId(33L);

            mChannels.add(channel1);
            mCategories.add(category1);

            Map<String, String> mCategoryTitle;
            Map<String, String> mChildAttrArr;

            List<Map<String, String>> tmpChild;

            for (Category category : mCategories) {
                tmpChild = new ArrayList<>();
                for (Channel channel : mChannels) {
                    if (category.getCategoryId().equals(channel.getCategoryId())) {
                        mChildAttrArr = new HashMap<>();
                        mChildAttrArr.put(attrChildTitle, channel.getTitle());
                        mChildAttrArr.put(attrChildId, channel.getChannelId().toString());
                        tmpChild.add(mChildAttrArr);
                    }
                }
                if (!tmpChild.isEmpty())
                    channelTreeData.add(tmpChild);
                mCategoryTitle = new HashMap<>();
                mCategoryTitle.put(attrParentTitle, category.getName());
                groupData.add(mCategoryTitle);
            }
        }
        mView.ShowChannelListMenu(groupData, channelTreeData, attrParentTitle, attrChildTitle);
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
    public void openLogFragment() {
        navController.navigate(R.id.nav_logFragment);
    }

    @Override
    public void selectTreeItemChannel(int groupPos, int childPos) {
        String index = channelTreeData.get(groupPos).get(childPos).get("attrChildId");
        Bundle bundle = new Bundle();
        bundle.putLong(ItemListFragment.CHANNEL_ID, Long.parseLong(index));
        navController.navigate(R.id.nav_item_list_fragment, bundle);
        mView.closeDrawer();
    }

    @Override
    public void updLeftMenu() {
        reCalcMenu();
    }

    @Override
    public void contextChannelEdit(Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(ChannelEditFragment.CHANNEL_ID_PARAM, id);
        navController.navigate(R.id.nav_channel_edit_fragment, bundle);
        mView.closeDrawer();
    }

    @Override
    public void contextChannelDelete(Long id) {
        compositeDisposable.add(
                Maybe.merge(channelInteractor.deleteChannelById(id), itemInteractor.deleteItemsByChannelId(id), itemInteractor.getAllItems(), itemInteractor.deleteAllItems())

                //TODO удаление favorites
                //TODO удаление files
                .subscribe(
                integer -> {
                    reCalcMenu();
                    navController.navigate(R.id.nav_item_list_fragment);
                    mView.closeDrawer();
                },
                throwable -> showErrorMessage(new DefaultErrorBundle((Exception) throwable))));
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
}
