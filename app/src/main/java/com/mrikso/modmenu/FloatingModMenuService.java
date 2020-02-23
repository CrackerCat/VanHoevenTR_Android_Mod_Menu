package com.mrikso.modmenu;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextWatcher;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextSwitcher;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.Parser;

import java.io.IOException;
import java.io.InputStream;

public class FloatingModMenuService extends Service {


    /* access modifiers changed from: private */
    public View mFloatingView;
    private LinearLayout modBody;
    /* access modifiers changed from: private */
    public WindowManager windowManager;


    //инициализируем методы из нативной библиотеки
    // private native String toastFromJNI();
    private native void godmode_on();
    private native void godmode_off();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        InstallMenu();
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                FloatingModMenuService.this.Thread();
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void InstallMenu() {
        int i = VERSION.SDK_INT >= 26 ? 2038 : 2002;
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new LayoutParams(-2, -2));
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(convertDipToPixels(50.0f), convertDipToPixels(50.0f)));
        try {
            InputStream open = getAssets().open("Platinmods.png");
            imageView.setImageDrawable(Drawable.createFromStream(open, null));
            open.close();
            relativeLayout.addView(imageView);
            this.mFloatingView = relativeLayout;
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            linearLayout.setBackgroundColor(Color.parseColor("#ff1f2b3f"));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            textView.setGravity(1);
            textView.setText(Html.fromHtml("<font face='fantasy'><b><font color='#57c4aa'>MENU MOD</b></font>"));
            textView.setTextSize(20.0f);
            WebView webView = new WebView(this);
            webView.setLayoutParams(new LinearLayout.LayoutParams(-2, convertDipToPixels(25.0f)));
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) webView.getLayoutParams();
            layoutParams.gravity = 17;
            layoutParams.bottomMargin = 10;
            webView.setBackgroundColor(Color.parseColor("#ff1f2b3f"));
            webView.setVerticalScrollBarEnabled(false);
            webView.setHorizontalScrollBarEnabled(false);
            webView.loadData("<html><head><style>body{color: #f3c930;font-weight:bold;font-family:Courier, monospace;}</style></head><body><marquee class=\"GeneratedMarquee\" direction=\"left\" scrollamount=\"4\" behavior=\"scroll\">[Platinmods.com] If Game Update Visit Platinmods.com</marquee></body></html>", "text/html", "utf-8");
            ScrollView scrollView = new ScrollView(this);
            scrollView.setLayoutParams(new LinearLayout.LayoutParams(-1, convertDipToPixels(250.0f)));
            scrollView.setScrollBarSize(convertDipToPixels(5.0f));
            this.modBody = new LinearLayout(this);
            this.modBody.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            this.modBody.setOrientation(LinearLayout.VERTICAL);
            scrollView.addView(this.modBody);
            RelativeLayout relativeLayout2 = new RelativeLayout(this);
            relativeLayout2.setLayoutParams(new RelativeLayout.LayoutParams(-2, -1));
            relativeLayout2.setPadding(10, 10, 10, 10);
            relativeLayout2.setVerticalGravity(16);
            Button button = new Button(this);
            button.setBackgroundColor(Color.parseColor("#12a56b"));
            button.setText("HIDE MENU");
            button.setTextColor(Color.parseColor("#e8f8f4"));
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams2.addRule(11);
            button.setLayoutParams(layoutParams2);
            Button button2 = new Button(this);
            button2.setBackgroundColor(Color.parseColor("#c41313"));
            button2.setText("KILL MENU");
            button2.setTextColor(Color.parseColor("#e8f8f4"));
            relativeLayout2.addView(button);
            relativeLayout2.addView(button2);
            linearLayout.addView(textView);
            linearLayout.addView(webView);
            linearLayout.addView(scrollView);
            linearLayout.addView(relativeLayout2);
            frameLayout.addView(linearLayout);
            final AlertDialog create = new Builder(this, 2).create();
            create.getWindow().setType(i);
            create.setView(frameLayout);
            final WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams(-2, -2, i, 8, -3);
            layoutParams3.gravity = 51;
            layoutParams3.x = 0;
            layoutParams3.y = 100;
            this.windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            this.windowManager.addView(this.mFloatingView, layoutParams3);
            this.mFloatingView.setOnTouchListener(new OnTouchListener() {
                private float initialTouchX;
                private float initialTouchY;
                private int initialX;
                private int initialY;

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case 0:
                            this.initialX = layoutParams3.x;
                            this.initialY = layoutParams3.y;
                            this.initialTouchX = motionEvent.getRawX();
                            this.initialTouchY = motionEvent.getRawY();
                            return true;
                        case 1:
                            create.show();
                            Toast.makeText(FloatingModMenuService.this, "Platinmods.com", Toast.LENGTH_SHORT).show();
                            return true;
                        case 2:
                            float round = (float) Math.round(motionEvent.getRawX() - this.initialTouchX);
                            float round2 = (float) Math.round(motionEvent.getRawY() - this.initialTouchY);
                            layoutParams3.x = this.initialX + ((int) round);
                            layoutParams3.y = this.initialY + ((int) round2);
                            FloatingModMenuService.this.windowManager.updateViewLayout(FloatingModMenuService.this.mFloatingView, layoutParams3);
                            return true;
                        default:
                            return false;
                    }
                }
            });
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    create.hide();
                }
            });
            button2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    create.hide();
                    FloatingModMenuService.this.stopSelf();
                }
            });
        } catch (IOException unused) {
        }
        modMenu();
    }


    //ru: основное меню патчей
    //en: current patch menu
    private void modMenu() {
        addSwitch("Platinmods 4ever", new SW() {
            public void OnWrite(boolean isChecked) {
                if (isChecked) {
                    godmode_on();
                    Toast.makeText(getBaseContext(), "Platinmods 4ever is Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    godmode_off();
                    Toast.makeText(getBaseContext(), "Platinmods 4ever is Disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //метод для быстрого добавления свичей в меню патчей
    private void addSwitch(String name, final SW listner) {
        Switch sw = new Switch(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.setMargins(0, 2, 0, 0);
        sw.setLayoutParams(layoutParams);
        sw.setBackgroundColor(Color.parseColor("#2d4061"));
        sw.setPadding(10, 5, 10, 5);
        StringBuilder sb = new StringBuilder();
        sb.append("<font face='fantasy'><font color='red'>[!]</font> <font color='yellow'>");
        sb.append(name);
        sb.append("</font></font>");
        sw.setText(Html.fromHtml(sb.toString()));
        sw.setTextSize(20.0f);
        sw.setTypeface(sw.getTypeface(), Typeface.BOLD);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listner.OnWrite(isChecked);
            }
        });
        this.modBody.addView(sw);
    }


    private int convertDipToPixels(float f) {
        return (int) ((f * getResources().getDisplayMetrics().density) + 0.5f);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mFloatingView != null) {
            this.windowManager.removeView(this.mFloatingView);
        }
    }

    private boolean isChayNgam() {
        RunningAppProcessInfo runningAppProcessInfo = new RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(runningAppProcessInfo);
        return runningAppProcessInfo.importance != 100;
    }

    /* access modifiers changed from: private */
    public void Thread() {
        if (this.mFloatingView == null) {
            return;
        }
        if (isChayNgam()) {
            this.mFloatingView.setVisibility(View.INVISIBLE);
        } else {
            this.mFloatingView.setVisibility(View.VISIBLE);
        }
    }

    private interface SW {
        void OnWrite(boolean z);
    }

}