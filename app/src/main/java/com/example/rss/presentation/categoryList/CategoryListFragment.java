package com.example.rss.presentation.categoryList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.rss.AndroidApplication;
import com.example.rss.R;
import com.example.rss.databinding.CategoryListFragmentBinding;
import com.example.rss.presentation.BaseFragment;

import java.util.Objects;

import javax.inject.Inject;

public class CategoryListFragment extends BaseFragment implements CategoryListContract.V {

    private CategoryListFragmentBinding binding;
    private AndroidApplication app;

    public static final int REQUEST_NEW_CATEGORY_NAME = 1;
    public static final int REQUEST_ANOTHER_ONE = 2;

    public static final String RESULT_CATEGORY_NAME = "RESULT_NEW_CATEGORY_NAME";
    public static final String RESULT_ID_CATEGORY = "RESULT_ID_CATEGORY";

    @Inject
    public CategoryListPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("logo", "onCreate");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        app = (AndroidApplication) Objects.requireNonNull(getActivity()).getApplication();
        app.getFragmentModule(this).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("logo", "onCreateView");
        binding = CategoryListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("logo", "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
    }

    @Override
    public Context context() {
        return this.getActivity();
    }

    @Override
    public ViewGroup getRootView() {
        return binding.catListContainer;
    }

    @Override
    public LayoutInflater getInflater() {
        return this.getLayoutInflater();
    }

    @Override
    public int getRowResourceId() {
        return R.layout.category_row;
    }

    @Override
    public FragmentManager getManager() {
        return this.getChildFragmentManager();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.category_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_category:
                mPresenter.addCategoryMenu();
                break;
        }
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("logo", "onActivityCreated");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("logo", "onActivityResult");
        /*if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_NEW_CATEGORY_NAME:
                    if (data != null) {
                        String name = data.getStringExtra(RESULT_NEW_CATEGORY_NAME);
                        Long id = data.getLongExtra(RESULT_NEW_CATEGORY_NAME, -1);
                        mPresenter.processAnswerAddEditDialog(id, name);
                    }
                break;
            }
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
