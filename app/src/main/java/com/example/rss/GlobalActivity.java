package com.example.rss;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.rss.presentation.BaseActivity;
import com.example.rss.presentation.global.GlobalActions;
import com.example.rss.presentation.global.GlobalPresenter;
import com.example.rss.presentation.global.GlobalContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class GlobalActivity extends BaseActivity implements GlobalContract.V, GlobalActions {

	private ActionBar actionBar;
	private FloatingActionButton fab;
	private CoordinatorLayout container;

	private DrawerLayout drawer;
	private ExpandableListView expandableListView;
	private AndroidApplication app;

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

		app = (AndroidApplication) getApplication();
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
		fab.setOnClickListener(view -> Snackbar.make(container, "Прочитать все", Snackbar.LENGTH_LONG)
				.setAction("Action", null).show());

		Button btnAdd = findViewById(R.id.btnAddChannelDrawer);
		btnAdd.setOnClickListener(v -> mPresenter.OnClickChannelAdd(v));

		Button btnService = findViewById(R.id.btnTestService);
		btnService.setOnClickListener(v -> mPresenter.OnClickChannelTest(v));
	}

	@Override
	public void ShowChannelListMenu(List<Map<String, String>> groupData, List<List<Map<String, String>>> childData, String attrParentTitle, String attrChildTitle) {
		expandableListView = findViewById(R.id.expandableListView);

		String[] groupFrom = new String[]{attrParentTitle};
		int[] groupTo = new int[]{android.R.id.text1};

		String[] childFrom = new String[]{attrChildTitle};
		int[] childTo = new int[]{android.R.id.text1};

		SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
				this,
				groupData,
				android.R.layout.simple_expandable_list_item_1,
				groupFrom,
				groupTo,
				childData,
				android.R.layout.simple_list_item_1,
				childFrom,
				childTo);

		expandableListView.setAdapter(adapter);
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
		drawer.openDrawer(GravityCompat.START);
	}

	@Override
	public void closeDrawer() {
		drawer.closeDrawer(GravityCompat.START);
	}

	@Override
	public Context context() {
		return this;
	}

	@Override
	public void displayError(String errorMessage) {
		Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
	}


    @Override
    public int getNavHostViewId() {
        return R.id.nav_host_fragment;
    }

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId){
			case R.id.action_settings:
				onClickMenuSettings();
				break;
			case R.id.action_close_app:
				finish();
				break;
		}

		return false;
	}

	@Override
	public void onClickMenuSettings() {
		mPresenter.openSettingsFragment();
	}

	@Override
	public void setTitle(String title) {
		actionBar.setTitle(title);
	}

	@Override
	protected void onDestroy() {
		app.releaseGlobalActivity();
		mPresenter.destroy();
		mPresenter = null;
		super.onDestroy();
	}
}
