package com.example.configurableui.universal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.configurableui.R;

import java.io.File;
import java.util.Set;

public class ConfigActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "ConfigActivity";

    private Toolbar toolbar;
    private SeekBar seekBar;
    private LinearLayout linearLayout;
    private UniversalBuildViewModel viewModel;
    private boolean countEditText = true;
    private int value = 0;
    private Button btnEditSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);


        initViewModels();
        initViews();
        init();
        setObservers();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ConfigActivity.class);
        context.startActivity(starter);
    }

    protected void initViewModels() {
        viewModel = ViewModelProviders.of(this).get(UniversalBuildViewModel.class);
    }


    protected void initViews() {
        toolbar = findViewById(R.id.toolbar);
        btnEditSave = findViewById(R.id.btn_save);
        linearLayout = findViewById(R.id.linear_layout);
    }


    protected void init() {
        Group group = new Group();
        Groups groups = new Groups();
        viewModel.readFileLocal(getApplicationContext());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    protected void setObservers() {
        viewModel.getGroupsMutableLiveData().observe(this, new Observer<Groups>() {
            @Override
            public void onChanged(Groups groups) {
                createViews(groups);

            }
        });

    }

    private PackageInfo getPackageInfo() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = this.getPackageManager().getPackageInfo(
                    this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {

        }
        return packageInfo;
    }

    public int getProgressValue() {
        return UniversalBuildRepositiory.getInstance().getProgress();
    }

    public boolean getProxy() {
        return UniversalBuildRepositiory.getInstance().getProxyBoolean();
    }

    public boolean getRoot() {
        return UniversalBuildRepositiory.getInstance().getRootBoolean();
    }

    public boolean getCache() {
        return UniversalBuildRepositiory.getInstance().getCacheBoolean();
    }

    public int getRadioButtonClicked() {
        return UniversalBuildRepositiory.getInstance().getRadioButtonId();
    }

    @SuppressLint("ResourceType")
    private void createViews(Groups groups) {
        for (final Group group : groups.getGroups())
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
                                    Toast.makeText(ConfigActivity.this, "Cache cleared", Toast.LENGTH_SHORT).show();
                                    clearApplicationData();
                                }
                            });
                            break;

                        case Constants.SLIDER:
                            CardView cvSlider = (CardView) View.inflate( this,R.layout.cardview_top_rounded,null );
                            LinearLayout.LayoutParams layoutparamsslider = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            );
                            cvSlider.setLayoutParams(layoutparamsslider);
                            cvSlider.setBackgroundResource(R.drawable.cradview_seekbar_rounded);

                            final TextView tvSlider = (TextView) View.inflate(this, R.layout.textview, null);
                            int updatedValue = getProgressValue();
                            if (updatedValue != 0) {
                                value = updatedValue;
                            }
                            tvSlider.setText(elementGroup.getTitle() + "  :-  " + value);
                            cvSlider.addView(tvSlider);
                            linearLayout.addView(cvSlider);

                            CardView cvSliderbar = (CardView) View.inflate( this,R.layout.cardview_top_rounded,null );
                            LinearLayout.LayoutParams layoutParamsSliderbar = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            );
                            cvSliderbar.setLayoutParams(layoutParamsSliderbar);
                            cvSliderbar.setBackgroundResource(R.drawable.cardview_bottom_rounded);

                            seekBar = (SeekBar) View.inflate(this, R.layout.slider, null);
                            seekBar.setProgress(updatedValue);
                            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams3.setMargins(30, 30, 30, 30);
                            cvSliderbar.addView(seekBar);

                            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                                    value = progress;
                                    tvSlider.setText(elementGroup.getTitle() + "  :-  " + value);
                                    tvSlider.setTypeface(null, Typeface.BOLD);
                                    int progressSeekbar = progress;
                                    SharedPref.getInstance().addInt("ProgressValue", progressSeekbar);
                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            });

                            linearLayout.addView(cvSliderbar);
                            break;

                        case Constants.TOGGLE:
                            LinearLayout.LayoutParams layoutParamsToggle = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            final TextView textViewtoggle = (TextView) View.inflate(this, R.layout.textview_title, null);
                            textViewtoggle.setText(elementGroup.getTitle());

                            Switch toggleBtn = (Switch) View.inflate(this, R.layout.toggle, null);
                            String toggleIdentifier = elementGroup.getIdentifier();
                            switch (toggleIdentifier) {
                                case "enableProxy":
                                    toggleBtn.setChecked(getProxy());
                                    break;
                                case "enableRoot":
                                    toggleBtn.setChecked(getRoot());
                                    break;
                                case "enableCaching":
                                    toggleBtn.setChecked(getCache());
                            }
                            toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                    if (elementGroup.getIdentifier().equalsIgnoreCase("enableProxy")) {

                                        if (isChecked) {
                                            SharedPref.getInstance().addBoolean("enableProxyStatus", true);
                                        } else {
                                            SharedPref.getInstance().addBoolean("enableProxyStatus", false);
                                        }
                                    } else if (elementGroup.getIdentifier().equalsIgnoreCase("enableRoot")) {

                                        if (isChecked) {
                                            SharedPref.getInstance().addBoolean("enableRootStatus", true);
                                        } else {
                                            SharedPref.getInstance().addBoolean("enableRootStatus", false);
                                        }
                                    } else if (elementGroup.getIdentifier().equalsIgnoreCase("enableCaching")) {

                                        if (isChecked) {
                                            SharedPref.getInstance().addBoolean("enableCacheStatus", true);
                                        } else {
                                            SharedPref.getInstance().addBoolean("enableCacheStatus", false);
                                        }
                                    }

                                }
                            });

                            LinearLayout.LayoutParams layoutParamsSwitch = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            toggleBtn.setLayoutParams(layoutParamsSwitch);//layoutParamSwitch for toogle Button
                            layoutParamsToggle.setMargins(280, 25, 15, 15);
                            toggleBtn.setBackgroundColor(getResources().getColor(R.color.white));
                            LinearLayout layout = new LinearLayout(this);
                            LinearLayout layoutToggle = new LinearLayout(this);
                            layoutToggle.setOrientation(LinearLayout.HORIZONTAL);
                            layout.setOrientation(LinearLayout.HORIZONTAL);
                            layout.addView(toggleBtn);
                            layout.setPadding(380, 15, 0, 10);
                            layout.setGravity(View.TEXT_ALIGNMENT_VIEW_END);
                            layout.setWeightSum(1);
                            layout.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);

                            layoutToggle.addView(textViewtoggle); //layoutToggle is for textview
                            layoutToggle.addView(layout);
                            linearLayout.addView(layoutToggle);
                            break;

                        case Constants.DROPDOWN:
                            final TextView tvBaseUrl = (TextView) View.inflate(this, R.layout.textview_radio, null);
                            tvBaseUrl.setText(elementGroup.getTitle());
                            linearLayout.addView(tvBaseUrl);

                            int size = elementGroup.getOptions().size();
                            Set setkeys = elementGroup.getOptions().keySet();
                            final RadioGroup rbGroup = new RadioGroup(this);
                            rbGroup.setLayoutParams(new RadioGroup.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            rbGroup.setOrientation(LinearLayout.VERTICAL);

                            linearLayout.removeView(rbGroup);

                            for (Object keys : setkeys) {
                                RadioButton rb = (RadioButton) View.inflate(this, R.layout.radio_button, null);
                                LinearLayout linearLayoutMain = new LinearLayout(this);
                                LinearLayout.LayoutParams layoutParamsMain = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                layoutParamsMain.setMargins(50, 10, 0, 10);
                                TextView tvRb = (TextView) View.inflate(this, R.layout.textview_radio, null);
                                tvRb.setText(keys.toString());
                                tvRb.setLayoutParams(new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                                linearLayoutMain.addView(tvRb);
                                rb.setTag(keys.toString());
                                rb.setText(elementGroup.getOptions().get(keys).toUpperCase());
                                rb.setLayoutParams(new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                                rbGroup.addView(rb);

                                int rbValue = getRadioButtonClicked();

                                if (rbValue > elementGroup.getOptions().size()) {
                                    rbValue = rbValue % (elementGroup.getOptions().size());
                                }

                                if ((rb.getId() % elementGroup.getOptions().size()) == rbValue) {
                                    rb.setChecked(true);
                                }
                            }

                            rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {

                                    int id = group.getCheckedRadioButtonId();
                                    if (id != -1) {
                                        int rbId = group.getCheckedRadioButtonId();
                                        RadioButton radioButton = (RadioButton) group.findViewById(rbId);

                                        String selectedRbtext = (String) radioButton.getText();
                                        SharedPref.getInstance().addString("Base Url", selectedRbtext);

                                        if (((String) radioButton.getText()).equalsIgnoreCase("INSERT YOUR URL")) {
                                            btnEditSave.setVisibility(View.VISIBLE);
                                            final TextView tvManual = (TextView) View.inflate(getApplicationContext(), R.layout.textview_title, null);
                                            tvManual.setText("Custom Base Url");
                                            linearLayout.addView(tvManual);
                                            final EditText etManual = (EditText) View.inflate(getApplicationContext(), R.layout.edittext, null);
                                            LinearLayout.LayoutParams layoutParamsEditText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                            layoutParamsEditText.setMargins(50, 10, 60, 10);
                                            etManual.setLayoutParams(layoutParamsEditText);
                                            linearLayout.addView(etManual);
                                            countEditText = false;
                                            btnEditSave.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String textEditText = etManual.getText().toString();
                                                    SharedPref.getInstance().addString("Base Url", textEditText);
                                                    btnEditSave.setVisibility(View.GONE);
                                                    tvManual.setVisibility(View.GONE);
                                                    etManual.setVisibility(View.GONE);
                                                }
                                            });
                                        }

                                        SharedPref.getInstance().addInt("CheckRadioButtonId", rbId);
                                    }
                                }
                            });

                            linearLayout.addView(rbGroup);

                    }

                }
            }

    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        Log.d(TAG, "clearApplicationData: " + cache);
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));

                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}