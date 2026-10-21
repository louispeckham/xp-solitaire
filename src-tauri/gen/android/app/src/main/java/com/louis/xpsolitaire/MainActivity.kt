package com.louis.xpsolitaire   // must match the package line of your generated MainActivity.kt

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebStorage
import android.webkit.WebView
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : TauriActivity() {
  private var webView: WebView? = null
  private var fullscreen = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    window.decorView.setBackgroundColor(0xFF0054E3.toInt())   // Luna blue behind the system bars

    // Let fullscreen use the whole display, including around a camera notch
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      val lp = window.attributes
      lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
      window.attributes = lp
    }

    // Keep the game clear of the status bar, navigation bar and camera cut-out
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
      val bars = insets.getInsets(
        WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
      )
      v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
      insets
    }
  }

  override fun onWebViewCreate(webView: WebView) {
    this.webView = webView
    webView.settings.useWideViewPort = true          // lets the page's viewport tag scale the board to fit
    webView.settings.loadWithOverviewMode = true
    webView.settings.setSupportZoom(false)
    webView.addJavascriptInterface(Bridge(), "AndroidBridge")
    super.onWebViewCreate(webView)
  }

  /** Called from the page's title-bar buttons. Only local pages are loaded, so exposing this is safe. */
  inner class Bridge {

    // Minimise: back to the home screen. The activity stays alive, so the game is exactly as you left it.
    @JavascriptInterface
    fun minimize() {
      runOnUiThread { moveTaskToBack(true) }
    }

    // Maximise: toggle fullscreen (hides the status and navigation bars; swipe from an edge to peek at them).
    @JavascriptInterface
    fun toggleFullscreen() {
      runOnUiThread {
        fullscreen = !fullscreen
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        if (fullscreen) {
          controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
          controller.hide(WindowInsetsCompat.Type.systemBars())
        } else {
          controller.show(WindowInsetsCompat.Type.systemBars())
        }
      }
    }

    // Close: wipe the caches, remove the app from recents, and end the process.
    @JavascriptInterface
    fun closeAndClearCache() {
      runOnUiThread {
        webView?.apply { clearCache(true); clearHistory(); clearFormData() }
        WebStorage.getInstance().deleteAllData()
        CookieManager.getInstance().removeAllCookies(null)
        cacheDir.deleteRecursively()
        externalCacheDir?.deleteRecursively()
        finishAndRemoveTask()
        Handler(Looper.getMainLooper()).postDelayed({ Process.killProcess(Process.myPid()) }, 400)
      }
    }

    // Size of the area the game has, in dp (changes with rotation and split screen).
    @JavascriptInterface
    fun windowSizeDp(): String {
      val density = resources.displayMetrics.density
      val w = webView?.width ?: 0
      val h = webView?.height ?: 0
      return "${Math.round(w / density)},${Math.round(h / density)}"
    }
  }
}
