# NoSleep utility

NoSleep is a tool written in Java that prevents OS from automatically going into ScreenSaver / Sleep / Lock modes.
It's meant for those cases where user cannot change settings (due to corporate-enforced policy, for example).
Inspired by [NoSleep](https://github.com/CHerSun/NoSleep) and NoLockPC.

## Usage

Download **NoSleep.jar** from [the lastest release](https://github.com/rihalvnium76/NoSleep/releases/latest). Save it anywhere you like.
Then run it using `java`, and once minimized, you can leave it running in the background.

There are several ways to launch it from the command line:

```bat
:: When launched without parameters, it uses the system call mode 1 on Windows and the simulated key press mode on other OS.
java -jar .\NoSleep.jar

:: Simulated key press mode (Sleep is prevented and display is always on)
java -jar .\NoSleep.jar key

:: System call mode 1 (Sleep is prevented, but display can go off)
java -jar .\NoSleep.jar api

:: System call mode 2 (Sleep is prevented and display is always on)
java -jar .\NoSleep.jar api disp
```

Tool running in the command line can be terminated using `Ctrl+C`.

Alternatively, you can use `javaw` to launch the program without a command-line interface window, but terminating the program will require forcibly ending the process using the Task Manager.

## Requirements

JDK 1.8

## How it works

NoSleep has two operating modes: simulated key presses and system calls.

The simulated key press mode works by pressing the Scroll Lock key 1-3 times every 6-12 seconds, with an interval of 100-250ms between each key press, simulating user input.

The system call mode is currently exclusive to Windows OS. Its principle is to call [`SetThreadExecutionState()`](https://learn.microsoft.com/zh-cn/windows/win32/api/winbase/nf-winbase-setthreadexecutionstate) every 6-12 seconds to reset display/idle Windows timers.

The simulated key press mode uses approximately 21 MiB of memory, while the system call mode uses approximately 30 MiB.

## License

[MPL 2.0](LICENSE-MPL)