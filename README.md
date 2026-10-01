# XP Solitaire

A Windows XP-styled Solitaire desktop app built with [Tauri](https://tauri.app/). Classic Luna look, XP-style menu bar, and a 1.0x / 1.5x / 2.0x scale option.

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

## Credits

- Solitaire game logic and card artwork from [win98-web](https://github.com/azayrahmad/win98-web) by azayrahmad (MIT).
- Window and menu styling by [XP.css](https://github.com/botoxparty/XP.css) (MIT).
- Look and feel modelled on [Minesweeper-XP](https://github.com/AkshayKalose/Minesweeper-XP) by Akshay Kalose (MIT).

See [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md) for the full licence texts.

## Disclaimer

This is an unofficial fan project and is not affiliated with or endorsed by Microsoft. "Windows" and "Solitaire" are trademarks of Microsoft Corporation.

Card artwork comes from win98-web and may derive from Microsoft's original Solitaire. I'm happy to remove it on request.

## Licence

The original code in this repository is released under the [MIT licence](LICENSE). Third-party components keep their own licences, listed in THIRD_PARTY_NOTICES.md.
