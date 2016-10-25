package com.augmentis.ayp.yeutsen.fragment.main.strertchlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.augmentis.ayp.yeutsen.R;
import com.augmentis.ayp.yeutsen.jsonManager.jsonManager;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class StretchListFragment extends Fragment {


    private static final String TAG = "StretchListFragment";
    private RecyclerView mRecyclerView;
    private ArrayList<HashMap<String, String>> stretchList;
    private StretchAdapter mStretchAdapter;

    private int gridSize = 2;
    private String stretchPhotoFolder = "stretches";

    public static StretchListFragment newInstance(){
        Bundle args = new Bundle();
        StretchListFragment fragment = new StretchListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stretchList = new ArrayList<>();
        stretchList = jsonManager.getInstance(getContext()).getStetchList();
        mStretchAdapter =  new StretchAdapter(stretchList);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stretct_list_recycler_view, container, false);
        Log.d(TAG, "onCreateView: ");
        AnimationSet set = new AnimationSet(true);
        Animation ani = new AlphaAnimation(0.0f, 0.5f);
        ani.setDuration(350);
        set.addAnimation(ani);
        LayoutAnimationController con = new LayoutAnimationController(set,0.5f);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_stretch_list_recycler_view);
        mRecyclerView.setLayoutAnimation(con);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), gridSize));
        mRecyclerView.setAdapter(mStretchAdapter);
        return view;
    }

    private class StretchHolder extends RecyclerView.ViewHolder{
        private ImageView mStretchPhoto;
        private String sname;
        private String spath;
        private String sinfo;

        public StretchHolder(View itemView) {
            super(itemView);
            mStretchPhoto = (ImageView) itemView.findViewById(R.id.stretch_photo);
            mStretchPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StretchInfoFragment fragment = StretchInfoFragment.newInstance(sname, spath, sinfo);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container ,fragment)
                            .addToBackStack(null)
                            .commit();

                }
            });
        }
        protected void bindBitmap(Bitmap bitmap){
            mStretchPhoto.setImageBitmap(bitmap);
        }


        protected void setStretch(String sname, String spath, String sinfo){
            this.sname = sname;
            this.spath = spath;
            this.sinfo = sinfo;
        }
    }

    private class StretchAdapter extends RecyclerView.Adapter<StretchHolder>{
        ArrayList<HashMap<String, String>> _stretchList;

        protected StretchAdapter(ArrayList<HashMap<String, String>> stretchList){
            _stretchList = stretchList;
        }

        @Override
        public StretchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_stretch, parent, false);
            return new StretchHolder(view);
        }

        @Override
        public void onBindViewHolder(StretchHolder holder, int position) {
            try {
                InputStream inputStream = getActivity().getAssets().open(stretchPhotoFolder + File.separator + "thumbnail_" + _stretchList.get(position).get("spath") + ".png");
                Log.d(TAG, "onBindViewHolder: " + stretchPhotoFolder + File.separator + "thumbnail_" + _stretchList.get(position).get("spath") + ".png");
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                holder.bindBitmap(bitmap);
            }catch(Exception e){

            }
            holder.setStretch(_stretchList.get(position).get("sname"), _stretchList.get(position).get("spath"), _stretchList.get(position).get("sinfo"));
        }

        @Override
        public int getItemCount() {
            return _stretchList.size();
        }
    }
}
