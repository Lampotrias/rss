package com.example.rss;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

		initExpandableListView();
	}

	void initExpandableListView(){
		ExpandableListView expandableListView = findViewById(R.id.expandableListView);

		// названия компаний (групп)
		String[] groups = new String[] {"HTC", "Samsung", "LG"};

		// названия телефонов (элементов)
		String[] phonesHTC = new String[] {"Sensation", "Desire", "Wildfire", "Hero"};
		String[] phonesSams = new String[] {"Galaxy S II", "Galaxy Nexus", "Wave"};
		String[] phonesLG = new String[] {"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};

		// коллекция для групп
		ArrayList<Map<String, String>> groupData;

		// коллекция для элементов одной группы
		ArrayList<Map<String, String>> childDataItem;

		// общая коллекция для коллекций элементов
		ArrayList<ArrayList<Map<String, String>>> childData;
		// в итоге получится childData = ArrayList<childDataItem>

		// список атрибутов группы или элемента
		Map<String, String> m;

		groupData = new ArrayList<Map<String, String>>();
		for (String group : groups) {
			// заполняем список атрибутов для каждой группы
			m = new HashMap<String, String>();
			m.put("groupName", group); // имя компании
			groupData.add(m);
		}

		// список атрибутов групп для чтения
		String groupFrom[] = new String[] {"groupName"};
		// список ID view-элементов, в которые будет помещены атрибуты групп
		int groupTo[] = new int[] {android.R.id.text1};

		// создаем коллекцию для коллекций элементов
		childData = new ArrayList<ArrayList<Map<String, String>>>();

		// создаем коллекцию элементов для первой группы
		childDataItem = new ArrayList<Map<String, String>>();
		// заполняем список атрибутов для каждого элемента
		for (String phone : phonesHTC) {
			m = new HashMap<String, String>();
			m.put("phoneName", phone); // название телефона
			childDataItem.add(m);
		}
		// добавляем в коллекцию коллекций
		childData.add(childDataItem);

		// создаем коллекцию элементов для второй группы
		childDataItem = new ArrayList<Map<String, String>>();
		for (String phone : phonesSams) {
			m = new HashMap<String, String>();
			m.put("phoneName", phone);
			childDataItem.add(m);
		}
		childData.add(childDataItem);

		// создаем коллекцию элементов для третьей группы
		childDataItem = new ArrayList<Map<String, String>>();
		for (String phone : phonesLG) {
			m = new HashMap<String, String>();
			m.put("phoneName", phone);
			childDataItem.add(m);
		}
		childData.add(childDataItem);

		// список атрибутов элементов для чтения
		String childFrom[] = new String[] {"phoneName"};
		// список ID view-элементов, в которые будет помещены атрибуты элементов
		int childTo[] = new int[] {android.R.id.text1};


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
