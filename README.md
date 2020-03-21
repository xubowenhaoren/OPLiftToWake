## OnePlus Lift To Wake

#### What does it do?

This is a simple app to enable Lift To Wake for OnePlus 7 series.
It is not the Lift To "Ambient Display" in the stock Settings App; it enables the true Lift To "Lock Screen" like other phones.

#### How does it do this?

It listens to the native system sensor (`oneplus.sensor.pickup`). In my testing, it is very power efficient.

#### What phones does it support?

I have tested this on a OnePlus 7T with Android 10. It should also work with other OnePlus 7 series.
Note that for earlier OnePlus phones, there is also a [pickup sensor](http://stools.gleamolabs.com/sensors/devices/227) but with a different name.
If you want this app to work with earlier OnePlus phones, you should try replacing this line with the correct sensor name:

```
        mSensor = Utils.getSensor(context, mSensorManager, "oneplus.sensor.pickup");
```





