package com.hashirbaig.developer.textit.HostingActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hashirbaig.developer.textit.MainFragment.BackgroundListFragment;
import com.hashirbaig.developer.textit.Model.FilterAlbum;
import com.hashirbaig.developer.textit.Model.FiltersManager;
import com.hashirbaig.developer.textit.R;

import java.util.List;

public class BackgroundListActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private BackgroundListFragment mListFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mRecyclerView;
    private NavAdapter mAdapter;

    private Context mContext;

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, BackgroundListActivity.class);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_backgrounds);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view_backgrounds);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mContext = this;
        setSupportActionBar(mToolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.frames_grid_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer_string, R.string.close_drawer_string);
        mDrawerToggle.syncState();

        updateUI();

        mListFragment = BackgroundListFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = mListFragment;
            fm.beginTransaction()
                    .add(R.id.fragment_list_container, fragment)
                    .commit();

    }

    public void updateUI() {
        if(mAdapter == null) {
            mAdapter = new NavAdapter(FiltersManager.get(mContext).getList());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class NavViewHolder extends RecyclerView.ViewHolder
                                            implements View.OnClickListener{

        private TextView mTextView;
        private FilterAlbum mAlbum;

        public NavViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.album_name_view);
            v.setOnClickListener(this);
        }

        public void bindHolder(FilterAlbum album) {
            mAlbum = album;
            mTextView.setText(mAlbum.getName());
        }

        @Override
        public void onClick(View view) {
            FiltersManager.get(mContext).setCurrentAlbum(mAlbum);
            mListFragment.updateUI();
            mDrawerLayout.closeDrawers();
            view.setBackgroundColor(getResources().getColor(R.color.selected_list_view_background));
            mTextView.setTextColor(getResources().getColor(R.color.selected_list_text_color));
        }
    }

    private class NavAdapter extends RecyclerView.Adapter<NavViewHolder> {

        private List<FilterAlbum> mAlbumList;

        public NavAdapter(List<FilterAlbum> list) {
            mAlbumList = list;
        }

        @Override
        public NavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.single_album_frame_layout, parent, false);
            return new NavViewHolder(v);
        }

        @Override
        public void onBindViewHolder(NavViewHolder holder, int position) {
            holder.bindHolder(mAlbumList.get(position));
        }

        @Override
        public int getItemCount() {
            return mAlbumList.size();
        }
    }

}
