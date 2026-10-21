# XP Solitaire

A Windows XP-styled Solitaire game built with [Tauri](https://tauri.app/) for Windows, macOS, Linux and Android. Classic Luna look, XP-style menu bar, and touch-screen support.

## Download

Grab the latest build from the [Releases](../../releases) page:

- **Windows:** installer or `.exe`. Windows SmartScreen may warn about an unsigned app: choose "More info", then "Run anyway".
- **macOS:** `.dmg`. The app is unsigned, so right-click it, choose Open, then confirm.
- **Linux:** `.deb` or `.AppImage`.
- **Android:** `.apk`. You'll need to allow installs from your browser or file manager the first time.

## Features

- Windows XP window frame, title bar and menus (via XP.css)
- Drag cards or tap them to auto-move; works with mouse and touch
- Win animation
- Desktop: 1.0x / 1.5x / 2.0x scale option in the Help menu
- Android: the game scales to fit your screen, with a landscape layout that makes the cards bigger

### Android title-bar buttons

| Button | What it does |
| --- | --- |
| Minimise | Goes to the home screen. The game stays exactly as you left it. |
| Maximise | Toggles fullscreen (hides the status and navigation bars). |
| Close | Clears the app's cache, removes it from recent apps and closes it. |

## Run it

Requires Node.js, Rust and the Tauri prerequisites ([guide](https://v2.tauri.app/start/prerequisites/)).

```
npm install
npm run tauri dev      # run in development
npm run tauri build    # build the .exe and installer
```

Built binaries are in `src-tauri/target/release/`.

Run the following to change the icon (I use the Windows 3.1 Solitaire icon):

```
npx tauri icon yourimage.png
```

### Building for Android

Needs Android Studio (SDK and NDK), `JAVA_HOME`, `ANDROID_HOME` and `NDK_HOME` set, and the Rust Android targets (see the [Tauri Android prerequisites](https://v2.tauri.app/start/prerequisites/#android)).

```
npm run tauri android dev                                   # run on a phone or emulator
npm run tauri android build -- --apk --target aarch64       # release APK for most modern phones
```

Release APKs must be signed. Create your own keystore and put its details in `src-tauri/gen/android/keystore.properties` (this file is git-ignored; see [Tauri's Android signing guide](https://v2.tauri.app/distribute/sign/android/)). Never commit a keystore.

The Android-specific code (the title-bar buttons, fullscreen and cache clearing) is in `src-tauri/gen/android/app/src/main/java/`.

### Releases

Pushing a version tag (for example `v0.1.4`) makes GitHub Actions build the Windows, macOS and Linux versions and attach them to a draft release. The Android APK is built locally and added to the release by hand.

## Changes to the original game code

The Solitaire logic in `src/solitairemain.js` comes from win98-web and has been modified:

- Dragging starts as soon as you move, instead of after a 200ms hold, and cards drop the moment you let go.
- Drops are more forgiving: a card lands on the pile it overlaps most.
- The win animation is no longer cancelled by the tail end of a double-click, and no longer causes scroll bars.
- Touch input is translated into the mouse events the game expects.

## Credits

- Solitaire game logic and card artwork from [win98-web](https://github.com/azayrahmad/win98-web) by azayrahmad (MIT).
- Window and menu styling by [XP.css](https://github.com/botoxparty/XP.css) (MIT).
- Look and feel modelled on [Minesweeper-XP](https://github.com/AkshayKalose/Minesweeper-XP) by Akshay Kalose (MIT).

See [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md) for the full licence texts.

## Disclaimer

This is an unofficial fan project and is not affiliated with or endorsed by Microsoft. "Windows" and "Solitaire" are trademarks of Microsoft Corporation.

Card artwork comes from win98-web and may derive from Microsoft's original Solitaire, and the app icon is based on the Windows 3.1 Solitaire icon. I'm happy to remove either on request.

## Licence

The original code in this repository is released under the [MIT licence](LICENSE). Third-party components keep their own licences, listed in THIRD_PARTY_NOTICES.md.
