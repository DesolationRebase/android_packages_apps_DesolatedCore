package com.desolationrom.settings;

import android.app.*;
import android.os.*;
import android.support.v4.widget.*;
import android.widget.*;
import java.util.*;
import android.view.*;
import java.lang.reflect.*;
import android.content.*;
import com.desolationrom.settings.fragments.*;

public class MainActivity extends Activity 
{
	private StatusBarFragment s =  new StatusBarFragment();
	private NavBarFragment n = new NavBarFragment();
	private RecentsFragment r = new RecentsFragment();
	private LockScreenFragment l = new LockScreenFragment();
	private DisplayFragment d = new DisplayFragment();
	private ButtonFragment b = new ButtonFragment();
	private BatteryFragment ba = new BatteryFragment();
	private SoundFragment so = new SoundFragment();
	private ExtrasFragment e = new ExtrasFragment();
	private OTAFragment o = new OTAFragment();

	private String mTitle;
	private String[] mFragmentNames, mFragmentValues;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private int itemSelected;
	private boolean virgin = true; // Was the Drawer touched yet?
	private StableArrayAdapter mDrawerAdapter;
	private int[] icons = {
		R.drawable.ic_statusbar,
		R.drawable.ic_nav_home,
		R.drawable.ic_recents,
		R.drawable.ic_lock_screen,
		R.drawable.ic_display,
		R.drawable.ic_buttons,
		R.drawable.ic_battery,
		R.drawable.ic_sound,
		R.drawable.ic_extras,
		R.drawable.ic_ota
	};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
		Fragment fragment = new DefaultFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
			.replace(R.id.content_frame, fragment)
			.commit();
		mFragmentNames = getResources().getStringArray(R.array.dashboard_fragment_names);
		mFragmentValues = getResources().getStringArray(R.array.dashboard_fragment_values);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerAdapter = new StableArrayAdapter(this, R.layout.drawer_list_item, mFragmentNames);
        mDrawerList.setAdapter(mDrawerAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		View headerView = LayoutInflater.from(this).inflate(R.layout.drawer_header, null);
		((TextView) headerView.findViewById(R.id.textviewName)).setText("Desolated Core");
		mDrawerList.addHeaderView(headerView);
		setTitle(R.string.app_name);

    }

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long s)
		{
			if(position == 0) {
				MainActivity.this.virgin = true; // Touched the "Holy Ghost" as good as pure
				mDrawerAdapter.notifyDataSetChanged();
				MainActivity.this.itemSelected = position;
				selectItem(--position);
				return;
			}
			--position;
			MainActivity.this.itemSelected = position;
			MainActivity.this.virgin = false;
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		try {
			Fragment fragment = null;
			if(position >= 0) {
					Class<?> clazz = Class.forName("com.desolationrom.settings.fragments."+mFragmentValues[position]+"Fragment");
					fragment = (Fragment) clazz.newInstance();
				} else {
					setTitle(R.string.app_name);
					Class<?> clazz = Class.forName("com.desolationrom.settings.fragments.DefaultFragment");
					fragment = (Fragment) clazz.newInstance();
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment)
						.commit();
						mDrawerLayout.closeDrawer(mDrawerList);
						return;
			}
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment)
				.commit();

			mDrawerList.setItemChecked(position, true);
			setTitle(mFragmentNames[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} catch (Exception x) {
			x.printStackTrace();
			Toast.makeText(this, x.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title.toString();
		getActionBar().setTitle(mTitle);
	}

	private class StableArrayAdapter extends ArrayAdapter < String > {

		HashMap < String, Integer > mIdMap = new HashMap < String, Integer > ();

		public StableArrayAdapter(Context context, int textViewResourceId,
								  String[] objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.length; ++i) {
				mIdMap.put(objects[i], i);
			}
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = MainActivity.this.getLayoutInflater();
			RelativeLayout v = (RelativeLayout) inflater.inflate(R.layout.drawer_list_item, parent, false);
			TextView name = (TextView) v.findViewById(R.id.fragmentText);
			ImageView icon = (ImageView) v.findViewById(R.id.icon);
			icon.setImageResource(icons[position]);
			if(position == MainActivity.this.itemSelected && !MainActivity.this.virgin) {
				v.setBackgroundResource(R.color.itemSelected);
			} else {
				v.setBackgroundResource(R.color.drawer_background);
			}
			name.setText(mFragmentNames[position]);
			return v;
		}
	}

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
        }
}
