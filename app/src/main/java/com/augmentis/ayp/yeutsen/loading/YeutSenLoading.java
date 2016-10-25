package com.augmentis.ayp.yeutsen.loading;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.augmentis.ayp.yeutsen.activity.MainActivity;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class YeutSenLoading extends AsyncTask<Integer, Long, Boolean> {
    public ProgressDialog pd;
    public Context _context;

    public YeutSenLoading(Context context){
        this._context =context;
        pd = new ProgressDialog(_context);
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        pd.setTitle("Loading Activity");
        pd.setMessage("Please Wait ...");
        pd.setMax(integers[0]);
        pd.setIndeterminate(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        publishProgress(0L);

        long start = System.currentTimeMillis();
        long waitTime = integers[0] * 1000;
        try {
            while (System.currentTimeMillis() - start < waitTime) {
                Thread.sleep(200);
                publishProgress(System.currentTimeMillis() - start);
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        if (values[0] == 0) {
            pd.show();
        } else {
            pd.setProgress((int) (values[0] / 1000));
        }
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

    }
}
