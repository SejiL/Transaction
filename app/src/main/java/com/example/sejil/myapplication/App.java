package com.example.sejil.myapplication;

import android.app.Application;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class App extends Application {

    private final String defaultFontPath = "fonts/vazir_medium.ttf";

    @Override
    public void onCreate() {
        super.onCreate();

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath(defaultFontPath)
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

    }
}
