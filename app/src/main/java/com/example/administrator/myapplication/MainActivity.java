package com.example.administrator.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.administrator.myapplication.fragment.JsCallBackJavaFragment;
import com.example.administrator.myapplication.fragment.MainFragment;
import com.example.administrator.myapplication.fragment.MySwipeResfreshFragment;
import com.example.administrator.myapplication.fragment.MyViewListFragment;
import com.example.administrator.myapplication.fragment.NestedScrollViewFragment;
import com.example.administrator.myapplication.fragment.OkHttpFragment;
import com.example.administrator.myapplication.fragment.ProgressBarFragment;
import com.example.administrator.myapplication.fragment.RXFragment;
import com.example.administrator.myapplication.fragment.RescyViewFragment;
import com.example.administrator.myapplication.fragment.RetrofitFragment;
import com.example.administrator.myapplication.fragment.ShuaxinFragment;
import com.example.administrator.myapplication.fragment.SwipeResfreshFragment;
import com.example.administrator.myapplication.fragment.ViewDraghelperLayoutFragment;
import com.example.administrator.myapplication.tools.SystemBarTintManager;

/**
 * 首页
 */
public class MainActivity extends AppCompatActivity implements MainFragment.OnItemClickListener{

    MainFragment fragment;
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initSystemBar(R.color.colorPrimary);
        if(null == fragment){
        fragment = new MainFragment();}
        fragment.setOnItemClickListener(this);
        FragmentTransaction  transaction= manager.beginTransaction();
        transaction.add(R.id.main_central_layout, fragment).commit();

    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected void initSystemBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        mTintManager.setTintColor(getResources().getColor(color));
        //.setTintDrawable(getResources().getDrawable(R.drawable.common_title_bg50));
        mTintManager.setStatusBarTintResource(color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            onCall("5566");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    callDirectly(mMobile);
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "CALL_PHONE Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = manager.findFragmentById(R.id.main_central_layout);
        if(fragment instanceof  MySwipeResfreshFragment){
            ((MySwipeResfreshFragment)fragment).stopRefresh(false);
            super.onBackPressed();
        }else{
            super.onBackPressed();
        }
    }

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    private String mMobile = "";

    public void onCall(String mobile) {
        this.mMobile = mobile;
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                //上面已经写好的拨号方法
                callDirectly(mobile);
            }
        } else {
            //上面已经写好的拨号方法
            callDirectly(mobile);
        }
    }

    /**
     * 拨打电话 6.0属于危险操作   会弹出权限确认按钮
     *
     * @param mobile
     */
    private void callDirectly(String mobile) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + mobile));
        MainActivity.this.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       FragmentTransaction transaction = manager.beginTransaction();
        switch (position) {
            case 0:
                transaction.replace(R.id.main_central_layout, new RXFragment()).addToBackStack(null);
                break;
            case 1:
                transaction.replace(R.id.main_central_layout, new RetrofitFragment()).addToBackStack(null);
                break;
            case 2:
                transaction.replace(R.id.main_central_layout, new OkHttpFragment()).addToBackStack(null);
                break;
            case 3:
                transaction.replace(R.id.main_central_layout, new NestedScrollViewFragment()).addToBackStack(null);
                break;
            case 4:
                transaction.replace(R.id.main_central_layout, new ViewDraghelperLayoutFragment()).addToBackStack(null);
                break;
            case 5:
                transaction.replace(R.id.main_central_layout, new JsCallBackJavaFragment()).addToBackStack(null);
                break;
            case 6:
                transaction.replace(R.id.main_central_layout, new ProgressBarFragment()).addToBackStack(null);
                break;
            case 7:
                transaction.replace(R.id.main_central_layout, new ShuaxinFragment()).addToBackStack(null);
                break;
            case 8:
                transaction.replace(R.id.main_central_layout, new MyViewListFragment()).addToBackStack(null);
                break;
        }
        transaction.commit();
    }
}
