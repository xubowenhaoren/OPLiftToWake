English | [中文](https://github.com/xubowenhaoren/OPLiftToWake/blob/master/README_cn.md)

## OnePlus Lift To Wake

#### TL, DR. Where is the download?

[https://github.com/xubowenhaoren/OPLiftToWake/releases](https://github.com/xubowenhaoren/OPLiftToWake/releases)

#### What does it do?

This is a simple app to enable Lift To Wake for OnePlus 7 series.
It is not the Lift To "Ambient Display" in the stock Settings App; it enables the true Lift To "Lock Screen" like other phones.

#### Why do I want this feature?

You've probably already known that OnePlus, in Android 10, reveals the notification content on the lock screen after Face Unlock.
But to see those notifications, you either need to:

1. Pick up the phone and unlock with your finger at the Ambient Display. A two-step process.
2. Pick up the phone and press the power button. Again, a two-step process.

Now with the app, you can pick up the phone and the lock screen immediately shows up.

#### How does it do this?

It listens to the native system sensor (`oneplus.sensor.pickup`). In my testing, it is very power efficient.

#### What phones does it support?

I have tested this on a OnePlus 7T with Android 10. It should also work with other OnePlus 7 series.
Note that for earlier OnePlus phones, there is also a [pickup sensor](http://stools.gleamolabs.com/sensors/devices/227) but with a different name.
If you want this app to work with earlier OnePlus phones, you should try replacing this line in `PickupSensor.java` with the correct sensor name:

```
        mSensor = Utils.getSensor(context, mSensorManager, "oneplus.sensor.pickup");
```

#### How do I use it?

You probably want to turn off the Ambient Display first.

After installing the app, turn on the "Lift To Wake" toggle.
You should also consider turning off the battery optimization of the app for a more consistent background performance.
Follow a guide [here](http://nine-faq.9folders.com/articles/11588-how-to-turn-off-battery-optimization-on-the-oneplus).

#### Do I need Magisk for this?

No, it is root-free.

#### Credits

Much of the code is from the Lineage OS team.
Other code references can be found in the code comments.




