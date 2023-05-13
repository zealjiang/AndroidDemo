package com.example.myapplication.annotation;

import androidx.annotation.IntDef;

@IntDef({Year.LAST, Year.NOW, Year.NEXT})
public @interface Year {
    int LAST = 2016;
    int NOW = 2017;
    int NEXT = 2018;
}
