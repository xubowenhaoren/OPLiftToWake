[English](https://github.com/xubowenhaoren/OPLiftToWake/blob/master/README.md) | 中文

## 一加抬腕亮屏

#### 哪里下载？

[https://github.com/xubowenhaoren/OPLiftToWake/releases](https://github.com/xubowenhaoren/OPLiftToWake/releases)

#### 这个是做什么的？

这是一个非常简单的小工具，能开启一加7系列的真-抬腕亮屏功能。
这不是系统设置里面的『息屏显示』；它开启的是像其他友商手机的真-抬腕亮屏。

#### 我为什么想要这个功能？

你大概知道一加在安卓10里增加了面部解锁后在锁屏展开通知信息的功能。但如果你想看这些信息的话，你需要：

1. 拿起手机，息屏显示点亮，指纹解锁。需要两步。
2. 拿起手机，按电源键。还是需要两步。

现在有了这个小工具，你可以拿起手机，锁屏自动点亮。一步到位。

#### 这是怎么做到的？

这个软件监听一个系统原生传感器（`oneplus.sensor.pickup`）。市面上有其他的抬腕解锁应用，但大多都是自己通过监听加速度传感器实现的。
相比之下，这个软件专为一加优化，更省电。

#### 这个软件支持哪些手机？

我在自己的一加7T（安卓10稳定版，氧OS）测试过。其他的一加7系列应该是通用的。
一加老型号的手机也有一个[原生传感器](http://stools.gleamolabs.com/sensors/devices/227)，但名字不太一样。
如果你想让这个 app 支持老型号的话，修改在 `Utils.java` 的这行代码中的传感器名称：

```
        mSensor = Utils.getSensor(context, mSensorManager, "oneplus.sensor.pickup");
```

#### 如何使用？

你可能需要先在设置里把息屏显示关掉。

安装 app 以后，点开『抬腕亮屏』的开关。
建议关掉电池优化，防止系统杀服务。（设置 - 电池- 电池优化 - 一加抬腕亮屏 - 不优化）

#### 我需要 Magisk 吗？

不，这个 app 不需要 root。

#### 感谢

这个 app 中的很多代码来自 Lineage OS。
其他引用可在代码注释里面找到。


