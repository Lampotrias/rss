package com.example.rss.presentation.categoryList;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {

    private final AlertDialog.Builder builder;

    public AlertDialogFragment(AlertDialog.Builder builder) {
        super();
        this.builder = builder;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return builder.create();
    }
}
