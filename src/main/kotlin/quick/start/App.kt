package quick.start

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
class App{

}
open class SmartDevice(val name: String, val category: String) {

    var deviceStatus = "online"
        protected set


    open val deviceType = "unknown"

    open fun turnOn() {
        deviceStatus = "on"
    }

    open fun turnOff() {
        deviceStatus = "off"
    }
    open fun printDeviceInfo() {
        println("Device name: $name, category: $category, type: $deviceType")
    }
}

class SmartTvDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart TV"

    private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)

    private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)

    fun decreaseSpeakerVolume() {
        speakerVolume--
        println("Speaker volume decreased to $speakerVolume")
    }
    fun increaseSpeakerVolume() {
        speakerVolume++
        println("Speaker volume increased to $speakerVolume.")
    }

    fun nextChannel() {
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }
    fun previousChannel() {
        channelNumber--
        println("Channel number decreased to $channelNumber")
    }

    override fun turnOn() {
        super.turnOn()
        println(
            "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                    "set to $channelNumber."
        )
    }

    override fun turnOff() {
        super.turnOff()
        println("$name turned off")
    }
}

class SmartLightDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart Light"

    private var brightnessLevel by RangeRegulator(initialValue = 0, minValue = 0, maxValue = 100)

    fun increaseBrightness() {
        brightnessLevel++
        println("Brightness increased to $brightnessLevel.")
    }
    fun decreaseBrightness() {
        brightnessLevel--
        println("Brightness decreased to $brightnessLevel")
    }

    override fun turnOn() {
        super.turnOn()
        brightnessLevel = 2
        println("$name turned on. The brightness level is $brightnessLevel.")
    }

    override fun turnOff() {
        super.turnOff()
        brightnessLevel = 0
        println("Smart Light turned off")
    }
}

class SmartHome(
    val smartTvDevice: SmartTvDevice,
    val smartLightDevice: SmartLightDevice,
) {

    var deviceTurnOnCount = 0
        private set

    fun turnOnTv() {
        deviceTurnOnCount++
        smartTvDevice.turnOn()
        smartTvDevice.deviceStatus
        println("current - $deviceTurnOnCount")
    }


    fun turnOffTv() {
        deviceTurnOnCount--
        smartTvDevice.turnOff()
        println("current - $deviceTurnOnCount")
    }

    fun increaseTvVolume() {
        smartTvDevice.increaseSpeakerVolume()
    }
    fun decreaseTvVolume() {
        smartTvDevice.decreaseSpeakerVolume()
    }

    fun changeTvChannelToNext() {
        smartTvDevice.nextChannel()
    }
    fun changeTvChannelToPrevious() {
        smartTvDevice.previousChannel()
    }

    fun turnOnLight() {
        deviceTurnOnCount++
        smartLightDevice.turnOn()
        println("current - $deviceTurnOnCount")
    }

    fun turnOffLight() {
        deviceTurnOnCount--
        smartLightDevice.turnOff()
        println("current - $deviceTurnOnCount")
    }

    fun increaseLightBrightness() {
        smartLightDevice.increaseBrightness()
    }

    fun turnOffAllDevices() {
        turnOffTv()
        turnOffLight()
    }
    fun printSmartTvInfo() {
        smartTvDevice.printDeviceInfo()
    }
    fun printSmartLightInfo() {
        smartLightDevice.printDeviceInfo()
    }
}

class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {

    var fieldData = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue) {
            fieldData = value
        }
    }
}

fun main() {
    var smarttv: SmartTvDevice = SmartTvDevice("Android TV", "Entertainment")

    var smartlight: SmartLightDevice = SmartLightDevice("Google Light", "Utility")
    var smartHome: SmartHome = SmartHome(smarttv,smartlight)
    smarttv.turnOn()
    smartHome.turnOnTv()
    smarttv.printDeviceInfo()
    smartlight.turnOn()
    smartlight.printDeviceInfo()
}
