import com.padeoe.platformtools.ADBTool;
import com.padeoe.platformtools.ApkInfo;
import com.padeoe.platformtools.ApkReader;
import com.padeoe.platformtools.EnvironmentNotConfiguredException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by padeoe on 2017/4/23.
 */
public class ApkInfoTest {
    @Test
    public void testApkInfo() throws InterruptedException, EnvironmentNotConfiguredException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("NJUNet-2.5.4.apk").getFile());
        ApkInfo apkInfo = new ApkInfo(new ApkReader(file).readApkManifest());
        String expected="ApkInfo{apkName='NJUNet', versionName='2.5.4', packageName='com.padeoe.njunet', " +
                "sdkVersion='14', targetSdkVersion='23', versionCode='1405', launchableActivity=" +
                "'com.padeoe.njunet.deploy.FirstSettingActivity', permissions=[android.permission.RECEIVE_BOOT_COMPLETED" +
                ", android.permission.ACCESS_NETWORK_STATE, android.permission.ACCESS_WIFI_STATE, android.permission." +
                "INTERNET, android.permission.CHANGE_NETWORK_STATE, android.permission.CHANGE_WIFI_STATE, android.permission.ACCESS_COARSE_LOCATION]}";
        assertEquals(apkInfo.toString(),expected);
    }
}
