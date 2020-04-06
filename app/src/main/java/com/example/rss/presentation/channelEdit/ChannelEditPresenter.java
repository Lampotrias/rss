package com.example.rss.presentation.channelEdit;


import android.content.Context;

import androidx.navigation.NavController;
import androidx.room.EmptyResultSetException;

import com.example.rss.R;
import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.domain.exception.DefaultErrorBundle;
import com.example.rss.domain.exception.IErrorBundle;
import com.example.rss.domain.interactor.CategoryInteractor;
import com.example.rss.domain.interactor.ChannelInteractor;
import com.example.rss.domain.interactor.FileInteractor;
import com.example.rss.presentation.exception.ChannelExistsException;
import com.example.rss.presentation.exception.ChannelNotFoundException;
import com.example.rss.presentation.exception.ErrorMessageFactory;
import com.example.rss.presentation.global.GlobalActions;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class ChannelEditPresenter implements ChannelEditContract.P<ChannelEditContract.V> {
    private ChannelEditContract.V mView;
    private final ChannelInteractor channelInteractor;
    private final CategoryInteractor categoryInteractor;
    private final FileInteractor fileInteractor;
    private final CompositeDisposable compositeDisposable;
    protected Context context;
    private Channel oldChannel;

    @Inject
    GlobalActions globalActions;

    @Inject
    NavController navController;

    @Inject
    ChannelEditPresenter(ChannelInteractor channelInteractor, CategoryInteractor categoryInteractor, FileInteractor fileInteractor) {
        this.channelInteractor = channelInteractor;
        this.categoryInteractor = categoryInteractor;
        this.fileInteractor = fileInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onSaveButtonClicked(String url, Long categoryId, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi) {
        mView.setSaveButtonEnable(false);
        if (mView.getCurChannelId() == 0) {
            compositeDisposable.add(channelInteractor.checkChannelExistsByUrl(url)
                    .subscribe(channel -> {
                                showErrorMessage(new DefaultErrorBundle(new ChannelExistsException()));
                                mView.setSaveButtonEnable(true);
                            }, throwable -> {
                                if (throwable instanceof EmptyResultSetException) {
                                    compositeDisposable.add(channelInteractor.getRawChannel(url)
                                            .flatMap(rawObject -> fileInteractor.parseFileAndSave(rawObject)
                                                    .map(fileId -> channelInteractor.prepareChannelObj(rawObject, url, fileId, categoryId, bCacheImage, bDownloadFull, bOnlyWifi)))
                                            .flatMap(channelInteractor::add)
                                            .subscribe(
                                                    aLong -> {
                                                        mView.displaySuccess("new id: " + aLong);
                                                        mView.setSaveButtonEnable(true);
                                                        globalActions.updDrawerMenu();
                                                        navController.navigate(R.id.action_nav_channel_edit_fragment_to_nav_item_list_fragment);
                                                    },
                                                    throwableChannelAdd -> showErrorMessage(new DefaultErrorBundle((Exception) throwableChannelAdd))));
                                } else {
                                    showErrorMessage(new DefaultErrorBundle((Exception) throwable));
                                }
                                mView.setSaveButtonEnable(true);
                            }
                    )
            );
        } else {
            //edit
            final boolean needReload = (!categoryId.equals(oldChannel.getCategoryId()));

            oldChannel.setChannelId(mView.getCurChannelId());
            oldChannel.setCategoryId(categoryId);
            oldChannel.setCacheImage(bCacheImage);
            oldChannel.setDownloadFullText(bDownloadFull);
            oldChannel.setOnlyWifi(bOnlyWifi);

            compositeDisposable.add(channelInteractor.update(oldChannel)
                    .subscribe(integer -> {
                        if (needReload)
                            globalActions.updDrawerMenu();

                        navController.navigateUp();
                    }, throwable -> {
                        showErrorMessage(new DefaultErrorBundle((Exception) throwable));
                        mView.setSaveButtonEnable(true);
                    })
            );
        }
    }

    @Override
    public void onCancelButtonClicked() {
        navController.popBackStack();
    }

    @Override
    public void setView(ChannelEditContract.V view) {
        mView = view;
        buildCategoryList();
        initWindow();
    }

    private void initWindow() {

        if (mView.getCurChannelId() > 0) {
            compositeDisposable.add(channelInteractor.getChannelById(mView.getCurChannelId())
                    .subscribe(channel -> {
                        oldChannel = channel;
                        mView.drawEditChannelWindow(channel);
                    }, throwable -> showErrorMessage(new DefaultErrorBundle(new ChannelNotFoundException()))));
        } else
            mView.drawNewChannelWindow();
    }

    private void buildCategoryList() {
        compositeDisposable.add(categoryInteractor.getCategoriesByType(CategoryInteractor.CATEGORY_TYPE)
                .subscribe(categories -> {
                            mView.fillingCategories(categories);
                        },
                        throwable -> {
                            showErrorMessage(new DefaultErrorBundle((Exception) throwable));
                        },
                        () -> {
                            Category category = new Category();
                            category.setType(CategoryInteractor.CATEGORY_TYPE);
                            category.setName(mView.context().getString(R.string.not_category));
                            category.setCategoryId(1L);
                            compositeDisposable.add(categoryInteractor.addCategory(category)
                                    .subscribe(aLong -> {
                                                ArrayList<Category> categories = new ArrayList<>();
                                                categories.add(category);
                                                mView.fillingCategories(categories);
                                            },
                                            throwable -> {
                                                showErrorMessage(new DefaultErrorBundle((Exception) throwable));
                                            }
                                    )
                            );
                        }
                )
        );
    }

    private void showErrorMessage(IErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.mView.context(), errorBundle.getException());
        mView.displayError(errorMessage);
    }

    @Override
    public void resume() {
        globalActions.setTitle("Add channel");
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        if (!compositeDisposable.isDisposed())
            compositeDisposable.dispose();
    }
}
