# NoSleep Utility

NoSleep is a tool written in Java that prevents OS from automatically going into ScreenSaver / Sleep / Lock modes.
It's meant for those cases where user cannot change settings (due to corporate-enforced policy, for example).
Inspired by [NoSleep](https://github.com/CHerSun/NoSleep) and NoLockPC.

## Usage

Download **NoSleep.jar** from [the lastest release](https://github.com/rihalvnium76/NoSleep/releases/latest). Save it anywhere you like.

Run the tool from the command line using `java`. Once minimized, it will continue running in the background.

### Command-Line Options

```bat
:: Default mode: uses system call mode 1 on Windows, simulated key press mode on other OS.
java -jar NoSleep.jar

:: Simulated key press mode (prevents sleep and keeps display on)
java -jar NoSleep.jar key

:: System call mode 1 (prevents sleep, display may turn off)
java -jar NoSleep.jar api

:: System call mode 2 (prevents sleep and keeps display on)
java -jar NoSleep.jar api disp
```

To stop the tool when running in a command-line window, press `Ctrl+C`.

## Requirements

- Java 8 or later (JRE or JDK)
- For System Call Mode: Windows 7 or newer
- For Key Press Mode: A graphical desktop environment (does not work in headless mode)

## How it works

NoSleep operates in two modes: simulated key presses and system calls.

### Simulated Key Press Mode

This mode simulates user input by pressing the Scroll Lock key 1–3 times every 6–12 seconds, with a 100–250 ms delay between presses. This prevents the system from detecting idle time.

### System Call Mode (Windows Only)

This mode calls the Windows API function [`SetThreadExecutionState()`](https://learn.microsoft.com/zh-cn/windows/win32/api/winbase/nf-winbase-setthreadexecutionstate) every 6–12 seconds to reset the system’s idle and display timers. 

The `disp` option adds the `ES_DISPLAY_REQUIRED` flag to keep the screen on.

### Memory Usage

- Simulated key press mode: ~33 MiB
- System call mode: ~39 MiB

These values are approximate and may vary based on the JVM and system environment.

## License

[MPL 2.0](LICENSE-MPL)
