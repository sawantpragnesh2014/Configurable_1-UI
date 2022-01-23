package com.example.configurableui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.configurableui.R;
import com.example.configurableui.apiservice.ApiClient;
import com.example.configurableui.models.Root;
import com.example.configurableui.models.Screen;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String baseFolderName = "UI";
    private static final String TAG = "LoginActivity";
    public static String baseFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*try {
            baseFolder = getBaseFolder(this);
            createFolderStructure();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        findViewById(R.id.btn_api).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchUI();
            }
        });

        /*Root root = readFromJson();
        createViews(root);*/

    }

    public void fetchUI(){
        Log.d(TAG, "fetchUI: ");
        ApiClient.getAuthApi().fetchUI().enqueue(fetchUICallback());
    }

    private Callback<Root> fetchUICallback() {
        return new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if(response.isSuccessful()){
                    Root root = response.body();
                    Log.d("TAG", "onResponse: root "+root);
                    if (root != null) {
                        for(Screen screen : root.screens){
                            String entityJson = new GsonBuilder().serializeSpecialFloatingPointValues().create().toJson(screen);
                            Log.d(TAG, "onResponse: entityJson "+entityJson);

                            FileOutputStream outputStream;

                            try {
                                outputStream = openFileOutput(screen.groupName, Context.MODE_PRIVATE);
                                outputStream.write(entityJson.getBytes());
                                outputStream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            /*File direct = new File(getJsonStorageLocation() + screen.groupName + ".json");

                            try {
                                Writer output;
                                output = new BufferedWriter(new FileWriter(direct));
                                output.write(entityJson);
                                output.close();
                            } catch (Exception e){
                                e.printStackTrace();
                            }*/
                            /*if (!direct.exists()) {
                                boolean successfullyCreatedFolder = direct.mkdirs();
                                Log.d("TAG", "downloadImage: created: " + successfullyCreatedFolder);
                                if (!successfullyCreatedFolder) {
                                    return;
                                }
                            }*/



                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        };
    }

    public Root readFromJson(){
        StringBuilder text = new StringBuilder();
        File file = new File(this.getFilesDir(), "login.json");
        if (file.exists()){
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    Log.i("Test", "text : " + text + " : end");
                    text.append('\n');
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new GsonBuilder().serializeSpecialFloatingPointValues().create().fromJson(String.valueOf(text), Root.class);

    }

    private static void createFolderStructure() {

        File _baseFolder = new File(baseFolder);

        if (!_baseFolder.exists()) {
            boolean isDirCreated = _baseFolder.mkdir();
            Log.d("TAG", "Directory Created: " + isDirCreated);
        }

        File testPdfsFolder = new File(baseFolder + File.separator + "json_files");
        if (!testPdfsFolder.exists()) {
            boolean isDirCreated = testPdfsFolder.mkdirs();
            Log.d("TAG", "Directory Created: " + isDirCreated);
        }

    }

    public static String getJsonStorageLocation() {
        return baseFolder + File.separator + "json_files" + File.separator;
    }

    private static String getBaseFolder(final Context context) throws Exception {
        PackageManager m = context.getPackageManager();
        String s = context.getPackageName();
        PackageInfo p;
        try {
            p = m.getPackageInfo(s, 0);
            return (p.applicationInfo.dataDir + File.separator + baseFolderName);
        } catch (PackageManager.NameNotFoundException e) {
            throw new Exception("Data dir not found : " + e.getMessage());
        }
    }

    @SuppressLint("ResourceType")
    private void createViews(Root root) {
        /*for (Screen group : root.screens)
            if (group.getGroupName() != null) {
                String title = group.getGroupName();
                TextView txtGroupName = (TextView) View.inflate(this, R.layout.textview_title, null);
                txtGroupName.setText(title);
                linearLayout.addView(txtGroupName);
                for (final Elements elementGroup : group.getElements()) {
                    String Type = elementGroup.getType();
                    switch (Type) {

                        case Constants.TEXTFIELD:
                            CardView cvTextView = (CardView) View.inflate( this,R.layout.cardview_txtview,null );
                            LinearLayout.LayoutParams layoutparamstxt = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            );
                            linearLayout.addView(cvTextView);

                            TextView tvGrpName = (TextView) View.inflate(this, R.layout.textview, null);
                            tvGrpName.setText(elementGroup.getTitle());
                            cvTextView.addView(tvGrpName);

                            LinearLayout view = new LinearLayout(this);

                            final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    15
                            );
                            view.setLayoutParams(layoutParams);

                            view.setBackgroundColor(getResources().getColor(android.R.color.white));

                            linearLayout.addView(view);

                            linearLayout.removeView(cvTextView);
                            LinearLayout.LayoutParams layoutParamsValue = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            );
                            cvTextView.setLayoutParams(layoutParamsValue);
                            cvTextView.setBackgroundResource(R.drawable.rounded_carview_settings);

                            TextView tvTxtValue = (TextView) View.inflate(this, R.layout.textview_value, null);

                            String versionCode = "" + getPackageInfo().versionName;

                            if (elementGroup.getIdentifier().equalsIgnoreCase( Constants.APP_VERSION)) {
                                tvTxtValue.setText(versionCode);
                            } else if (elementGroup.getIdentifier().equalsIgnoreCase( Constants.APP_NAME))
                                tvTxtValue.setText(elementGroup.getValue());
                            cvTextView.addView(tvTxtValue);
                            linearLayout.addView(cvTextView);

                            LinearLayout viewSlider = new LinearLayout(this);
                            LinearLayout.LayoutParams layoutParamsSlider = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    43
                            );
                            viewSlider.setLayoutParams(layoutParamsSlider);

                            viewSlider.setBackgroundColor(getResources().getColor(android.R.color.white));

                            linearLayout.addView(viewSlider);
                            break;

                        case Constants.BUTTON:
                            Button btn = (Button) View.inflate(this, R.layout.button, null);
                            btn.setText(elementGroup.getTitle());
                            LinearLayout.LayoutParams layoutparamsbtn = new LinearLayout.LayoutParams(
                                    320,
                                    110
                            );
                            layoutparamsbtn.setMarginStart(330);
                            btn.setLayoutParams(layoutparamsbtn);
                            linearLayout.addView(btn);
                            layoutparamsbtn = (LinearLayout.LayoutParams) btn.getLayoutParams();
                            layoutparamsbtn.setMargins(40, 40, 40, 30);
                            btn.setLayoutParams(layoutparamsbtn);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(ActivityUniversalBuild.this, "Cache cleared", Toast.LENGTH_SHORT).show();
                                    clearApplicationData();
                                }
                            });
                            break;

                    }

                }
            }*/

    }



}