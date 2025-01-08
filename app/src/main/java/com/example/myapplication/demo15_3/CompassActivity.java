package com.example.myapplication.demo15_3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class CompassActivity  extends AppCompatActivity implements SensorEventListener {
    // 定义显示指南针的图片
    private ImageView znzIV;
    // 记录指南针图片转过的角度
    private float currentDegree = 0f;
    // 定义Sensor管理器
    private SensorManager mSensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        znzIV = findViewById(R.id.znzImage);
        // 获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        // 为系统的方向传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    public void onPause()
    {
        // 取消注册
        mSensorManager.unregisterListener(this);
        super.onPause();
    }
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        // 获取触发event的传感器类型
        int sensorType = event.sensor.getType();
        switch (sensorType)
        {
            case Sensor.TYPE_ORIENTATION:
                // 获取绕Z轴转过的角度
                float degree = event.values[0];
                // 创建旋转动画（反向转过degree度）
                RotateAnimation ra = new RotateAnimation(currentDegree, -degree,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                // 设置动画的持续时间
                ra.setDuration(200);
                // 运行动画
                znzIV.startAnimation(ra);
                currentDegree = -degree;
                break;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }
}
