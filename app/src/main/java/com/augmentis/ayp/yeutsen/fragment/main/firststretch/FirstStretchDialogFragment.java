package com.augmentis.ayp.yeutsen.fragment.main.firststretch;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augmentis.ayp.yeutsen.R;
import com.augmentis.ayp.yeutsen.jsonManager.jsonManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static android.support.v7.appcompat.R.styleable.AlertDialog;
import static android.support.v7.recyclerview.R.styleable.RecyclerView;


/**
 * Created by Nutdanai on 10/24/2016.
 */

public class FirstStretchDialogFragment extends DialogFragment {
    private static final String TAG = "DialogShowFragment";
    private FragmentManager fragmentManager;
    private RecyclerView mRecyclerView;
    private StretchAdapter mAdapter;
    private String jsonFilename = "stretch.json";
    private String stretchPhotoFolder = "stretches";
    private ArrayList<HashMap<String, String>> stretchShowList;
    protected int position;

    public static FirstStretchDialogFragment newInstance(int position){
        Bundle args = new Bundle();
        args.putInt("position", position);
        FirstStretchDialogFragment fragment = new FirstStretchDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
        stretchShowList = new ArrayList<>();
        stretchShowList = jsonManager.getInstance(getContext()).getStetchList();
        stretchShowList.get(position).put("isselect", "true");
        mAdapter = new StretchAdapter(stretchShowList, position);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_recycler_view, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.show_stretching_recycler_view_dialog);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.invalidate();

        builder.setView(view);
        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //send position back to ShowStretchingFragment.java to change stretching in webview
                Intent intent = new Intent();
                intent.putExtra("position", position);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            }
        });
        builder.setNegativeButton("Cancel", null);

        return builder.show();
    }

    private class StretchHolder extends RecyclerView.ViewHolder{
        private TextView mDetail;
        private ImageView mShow;

        public StretchHolder(final View itemView) {
            super(itemView);
            mDetail = (TextView) itemView.findViewById(R.id.show_holder_detail_text_view);
            mShow = (ImageView) itemView.findViewById(R.id.show_holder_image_view);
        }

        protected void bindBitmap(Bitmap bitmap){
            mShow.setImageBitmap(bitmap);
        }

        protected void setStretchDetail(String stringDetail){
            mDetail.setText(stringDetail);
        }

        protected void setPosition(int bposition){
            position = bposition;
        }

    }

    private class StretchAdapter extends RecyclerView.Adapter<StretchHolder>{
        ArrayList<HashMap<String, String>> _stretchShowList;
        int selected_position;

        protected StretchAdapter(ArrayList<HashMap<String, String>> stretchShowList, int position){
            _stretchShowList = stretchShowList;
            selected_position = position;
        }

        @Override
        public StretchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.dialog_holder, parent, false);
            return new StretchHolder(view);
        }

        @Override
        public void onBindViewHolder(final StretchHolder holder, final int position) {
            try {
                InputStream inputStream = getActivity().getAssets().open(stretchPhotoFolder + File.separator + "thumbnail_" + _stretchShowList.get(position).get("spath") + ".png");

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                holder.bindBitmap(bitmap);
            }catch (Exception e){
            }

            holder.setStretchDetail(_stretchShowList.get(position).get("sname"));

            Log.d(TAG, "onBindViewHolder: " + _stretchShowList.get(position).get("isselect"));

            if(stretchShowList.get(position).get("isselect").equals("true")){
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }else {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _stretchShowList.get(selected_position).put("isselect", "false");
                    notifyItemChanged(selected_position);
                    selected_position = Integer.valueOf(_stretchShowList.get(position).get("sid"));
                    _stretchShowList.get(selected_position).put("isselect", "true");
                    notifyItemChanged(selected_position);
                    holder.setPosition(selected_position);
                }
            });

        }

        @Override
        public int getItemCount() {
            return stretchShowList.size();
        }


    }
}
