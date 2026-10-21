# Manifest and strings edits

## src-tauri/gen/android/app/src/main/AndroidManifest.xml

1. On the existing `<activity ...>` tag (the one named `.MainActivity`), add:

       android:resizeableActivity="true"

2. Inside `<application>`, after the `</activity>` line, add:

       <service
           android:name=".SplitScreenService"
           android:exported="true"
           android:label="XP Solitaire split screen"
           android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
         <intent-filter>
           <action android:name="android.accessibilityservice.AccessibilityService" />
         </intent-filter>
         <meta-data
             android:name="android.accessibilityservice"
             android:resource="@xml/split_screen_service" />
       </service>

## src-tauri/gen/android/app/src/main/res/values/strings.xml

Add inside `<resources>`:

    <string name="split_screen_service_description">Lets the Maximise button in XP Solitaire switch split screen on and off. It does not read or change anything on your screen.</string>
