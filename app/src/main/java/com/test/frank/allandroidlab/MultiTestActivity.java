package com.test.frank.allandroidlab;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Frank on 16/3/26.
 */
public class MultiTestActivity extends Activity {

    private ImageView displayimg,img1,img2,img3;
    private Button btnshow1,btnshow2,btnshow3;

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     * {@link #managedQuery(Uri, String[], String, String[], String)} to retrieve
     * cursors for data being displayed, etc.
     * <p/>
     * <p>You can call {@link #finish} from within this function, in
     * which case onDestroy() will be immediately called without any of the rest
     * of the activity lifecycle ({@link #onStart}, {@link #onResume},
     * {@link #onPause}, etc) executing.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multitest);

        displayimg = (ImageView) findViewById(R.id.imgDisplay);
        img1 = (ImageView) findViewById(R.id.imageView1);
        img2 = (ImageView) findViewById(R.id.imageView2);
        img3 = (ImageView) findViewById(R.id.imageView3);

        btnshow1= (Button) findViewById(R.id.btnshow1);
        btnshow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadImageAsync(img1,R.id.showimgparent1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        btnshow2= (Button) findViewById(R.id.btnshow2);
        btnshow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadImageAsync(img2,R.id.showimgparent2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        btnshow3= (Button) findViewById(R.id.btnshow3);
        btnshow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadImageAsync(img3,R.id.showimgparent3).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

    }


    public void displayHandler(View view) {

        ImageView iv= (ImageView) findViewById(R.id.imgDisplay);
        iv.setVisibility(View.VISIBLE);

    }

    public void eraseHandler(View view) {

        ImageView iv= (ImageView) findViewById(R.id.imgDisplay);
        iv.setVisibility(View.INVISIBLE);

    }

    class LoadImageAsync extends AsyncTask<Integer, Integer, Void > {

        private ProgressBar progressBar = null;
        private ViewGroup viewGroup=null;
        private ImageView image;
        private int parent_id;


        public LoadImageAsync(ImageView image,int parent_id) {
            this.image = image;
            this.parent_id = parent_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(image.getVisibility()==View.INVISIBLE)
                showProgressBar();
            else
                Toast.makeText(getBaseContext(),"Already show the Image",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Integer... params) {

            for (int i=10; i<101; i=i+10) {
                try {
                    Thread.sleep(500);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(progressBar!=null)
                progressBar.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            image.setVisibility(View.VISIBLE);
            dismissProgressBar();

            // mPBar.setVisibility(ProgressBar.INVISIBLE);


        }

        /**
         * 在母布局中间显示进度条
         */
        private void showProgressBar(){


            progressBar = new ProgressBar(getBaseContext(), null, android.R.attr.progressBarStyleHorizontal);
            progressBar.setIndeterminate(false);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT,  RelativeLayout.TRUE);
            progressBar.setVisibility(View.VISIBLE);
            viewGroup = (ViewGroup)findViewById(parent_id);
            viewGroup.addView(progressBar, params);
        }
        /**
         * 隐藏进度条
         */
        private void dismissProgressBar(){
            if(progressBar != null){
                progressBar.setVisibility(View.GONE);
                viewGroup.removeView(progressBar);
            }
        }
    }
}
