package com.example.rss.presentation.categoryList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;

import com.example.rss.AndroidApplication;
import com.example.rss.R;
import com.example.rss.presentation.global.GlobalActions;

import java.util.Objects;

import javax.inject.Inject;

public class EditDialogFragment extends DialogFragment {

    private AndroidApplication app;

    @Inject
    GlobalActions globalActions;

    @Inject
    NavController navController;

    private String name = "";
    private Long id = 0L;
    private EditText editText;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (AndroidApplication) Objects.requireNonNull(getActivity()).getApplication();
        app.getFragmentModule(this).inject(this);

        context = globalActions.getActivityContext();

        if (getArguments() != null) {
            id = getArguments().getLong(CategoryListFragment.RESULT_ID_CATEGORY, 0L);
            name = getArguments().getString(CategoryListFragment.RESULT_CATEGORY_NAME, "");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        app.releaseFragmentModule();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        editText = new EditText(context);
        editText.setText(name);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getText(R.string.alert_edit_cat_new_title))
                .setPositiveButton(context.getResources().getText(R.string.alert_edit_cat_save), (dialog, which) -> positiveClick())
                .setNegativeButton(context.getResources().getText(R.string.alert_edit_cat_cancel), (dialog, which) -> negativeClick())
                .setView(editText)
                .setTitle(context.getResources().getText(R.string.alert_edit_cat_title));

        return builder.create();
    }

    private void negativeClick() {
        navController.popBackStack();
    }

    private void positiveClick() {
        if (editText.getText().length() > 0) {
            NavBackStackEntry navBackStackEntry = navController.getPreviousBackStackEntry();
            if (navBackStackEntry != null) {
                navBackStackEntry.getSavedStateHandle().set(CategoryListFragment.RESULT_CATEGORY_NAME, editText.getText().toString());
                navBackStackEntry.getSavedStateHandle().set(CategoryListFragment.RESULT_ID_CATEGORY, this.id);
            }
            navController.navigate(R.id.action_nav_cat_edit_dialog_to_nav_categoryListFragment);
        }
    }
}
