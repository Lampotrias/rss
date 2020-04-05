package com.example.rss.presentation.categoryList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;

import com.example.rss.R;
import com.example.rss.domain.Category;
import com.example.rss.domain.interactor.CategoryInteractor;
import com.example.rss.presentation.global.GlobalActions;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CategoryListPresenter implements CategoryListContract.P {

    private CategoryListContract.V mView;
    private final CompositeDisposable compositeDisposable;
    private final CategoryInteractor categoryInteractor;

    @Inject
    NavController navController;

    @Inject
    GlobalActions globalActions;

    @Inject
    CategoryListPresenter(CategoryInteractor categoryInteractor) {
        this.categoryInteractor = categoryInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if (!compositeDisposable.isDisposed())
            compositeDisposable.dispose();
    }

    @Override
    public void setView(CategoryListContract.V view) {
        this.mView = view;

        NavBackStackEntry navBackStackEntry = navController.getPreviousBackStackEntry();
        if (navBackStackEntry != null) {
            if (navBackStackEntry.getSavedStateHandle().contains(CategoryListFragment.RESULT_CATEGORY_NAME)) {
                Long id = navBackStackEntry.getSavedStateHandle().get(CategoryListFragment.RESULT_ID_CATEGORY);
                String name = navBackStackEntry.getSavedStateHandle().get(CategoryListFragment.RESULT_CATEGORY_NAME);
                if (id == 0 && !name.isEmpty()) {
                    Category newCategory = new Category();
                    newCategory.setType(CategoryInteractor.CATEGORY_TYPE);
                    newCategory.setName(name);
                    compositeDisposable.add(categoryInteractor.addCategory(newCategory).subscribe(aLong -> {
                                navBackStackEntry.getSavedStateHandle().remove(CategoryListFragment.RESULT_ID_CATEGORY);
                                navBackStackEntry.getSavedStateHandle().remove(CategoryListFragment.RESULT_CATEGORY_NAME);
                                globalActions.updDrawerMenu();
                            }
                    ));

                } else if (id > 0 && !name.isEmpty()) {
                    compositeDisposable.add(categoryInteractor.updateCategoryNameById(id, name)
                            .subscribe(integer -> {
                                navBackStackEntry.getSavedStateHandle().remove(CategoryListFragment.RESULT_ID_CATEGORY);
                                navBackStackEntry.getSavedStateHandle().remove(CategoryListFragment.RESULT_CATEGORY_NAME);
                                globalActions.updDrawerMenu();
                            })
                    );
                }
            }
        }

        DrawCategoryList();
    }

    @Override
    public void addCategoryMenu() {
        navController.navigate(R.id.action_nav_categoryListFragment_to_nav_cat_edit_dialog);
    }

    private void DrawCategoryList() {
        compositeDisposable.add(
                categoryInteractor.getCategoriesByType(CategoryInteractor.CATEGORY_TYPE)
                        .doOnSuccess(categories -> {
                            mView.getRootView().removeAllViews();
                        })
                        .toObservable()
                        .concatMapIterable(categories -> categories)
                        .subscribe(category -> {
                            View view = mView.getInflater().inflate(mView.getRowResourceId(), mView.getRootView(), false);
                            TextView textView = view.findViewById(R.id.catName);
                            textView.setText(category.getName());
                            ImageView edit = view.findViewById(R.id.btnEdit);
                            ImageView delete = view.findViewById(R.id.btnDelete);
                            if (category.getCategoryId() == 1) {
                                delete.setVisibility(View.GONE);
                            } else {
                                delete.setTag(category.getCategoryId() + "=" + category.getName());
                                delete.setOnClickListener(this::deleteClick);
                            }
                            edit.setTag(category.getCategoryId() + "=" + category.getName());
                            edit.setOnClickListener(this::editClick);
                            mView.getRootView().addView(view);
                        }));
    }

    private void deleteClick(View v) {
        String value = (String) v.getTag();
        String[] arr = value.split("=");
        if (arr.length == 2) {
            Long id = Long.valueOf(arr[0]);
            String name = String.valueOf(arr[1]);
            AlertDialog.Builder builder = new AlertDialog.Builder(mView.context());
            builder.setMessage(mView.context().getResources().getText(R.string.alert_delete_cat_message))
                    .setPositiveButton(mView.context().getResources().getText(R.string.alert_delete_cat_yes), (dialog, which) -> doDelete(id))
                    .setNegativeButton(mView.context().getResources().getText(R.string.alert_delete_cat_no), (dialog, which) -> {
                    })
                    .setTitle(mView.context().getResources().getText(R.string.alert_delete_cat_title));

            DialogFragment dialogFragment = new AlertDialogFragment(builder);
            dialogFragment.show(mView.getManager(), "deleteDialog");
        }
    }

    private void editClick(View v) {
        String value = (String) v.getTag();
        String[] arr = value.split("=");
        if (arr.length == 2) {
            long id = Long.parseLong(arr[0]);
            String name = String.valueOf(arr[1]);

            Bundle bundle = new Bundle();
            bundle.putString(CategoryListFragment.RESULT_CATEGORY_NAME, name);
            bundle.putLong(CategoryListFragment.RESULT_ID_CATEGORY, id);

            navController.navigate(R.id.action_nav_categoryListFragment_to_nav_cat_edit_dialog, bundle);
        }
    }

    private void doDelete(Long id) {
        Log.e("logo", id.toString());
        compositeDisposable.add(categoryInteractor.deleteCategoryById(id)
                .subscribe(count -> {
                            DrawCategoryList();
                            globalActions.updDrawerMenu();
                        }
                ));
    }

    private void doEdit(Long id) {

    }
}
