package com.meteoros;

public class DeviceBasic {

    private String deviceName;
    private String deviceId;
    private String accessToken;

    public DeviceBasic() {}

    public DeviceBasic(String deviceName, String deviceId) {
        this.deviceName = deviceName;
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }
    public String getDeviceId() {
        return deviceId;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

