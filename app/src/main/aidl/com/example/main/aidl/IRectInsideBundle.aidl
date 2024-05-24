// IRectInsideBundle.aidl
package com.example.main.aidl;

/** Example service interface */
interface IRectInsideBundle {
    /** Rect parcelable is stored in the bundle with key "rect". */
    void saveRect(in Bundle bundle);
}