package com.example.rss;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.rss.presentation.BaseActivity;
import com.example.rss.presentation.global.GlobalActions;
import com.example.rss.presentation.global.GlobalPresenter;
import com.example.rss.presentation.global.GlobalContract;
import com.example.rss.presentation.main.MainFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

public class GlobalActivity extends BaseActivity implements GlobalContract.V, GlobalActions {

	private ActionBar actionBar;
	private FloatingActionButton fab;
	private CoordinatorLayout container;

	private DrawerLayout drawer;

    @Inject
    public GlobalPresenter mPresenter;

	@Override
	protected void onResume() {
		super.onResume();
		mPresenter.resume();
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mPresenter.setView(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AndroidApplication app = (AndroidApplication) getApplication();
		app.setGlobalActivity(this);
		app.getAppComponent().inject(this);


		container = findViewById(R.id.container);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setTitle("test");
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.draw_open, R.string.draw_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		fab = findViewById(R.id.fab);
		fab.setOnClickListener(view -> {
			Snackbar.make(container, "Прочитать все", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
		});

		Button btnAdd = findViewById(R.id.btnAddChannelDrawer);
		btnAdd.setOnClickListener(v -> mPresenter.OnClickChannelAdd(v));

		replaceFragment(new MainFragment());
	}

	@Override
	protected void setupActivityComponent() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onSupportNavigateUp() {
		return super.onSupportNavigateUp();
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void showMessage(String message) {
		Snackbar.make(container, message, Snackbar.LENGTH_LONG)
				.setAction("Action", null).show();
	}

	@Override
	public void openDrawer() {

	}

	@Override
	public void closeDrawer() {
		drawer.closeDrawer(GravityCompat.START);
	}

	@Override
	public void setTitle(String title) {
		actionBar.setTitle(title);
	}

	@Override
	public void replaceFragment(Fragment fragment) {

		final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.main_frame, fragment);
		fragmentTransaction.commit();
	}

}
