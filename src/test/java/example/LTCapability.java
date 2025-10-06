package example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.testng.annotations.DataProvider;

import com.google.gson.JsonObject;

public class LTCapability {
    @DataProvider(name = "getDefaultTestCapability")
    public static Object[] getDefaultTestCapability() {
        return getCapabilities("Playwright_Test");
    }
    
    public static Object[] getCapabilities(String testClassName) {
        JsonObject capabilities1 = new JsonObject();
        JsonObject ltOptions1 = new JsonObject();

        String user = "msainir"; // replace with your username
        String accessKey = "LT_s123"; // replace with your access key
        
        // Get current timestamp for build name
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        
        // Use the provided test class name
        String testName = testClassName;
        
        // Generate build name with timestamp
        String buildName = testClassName + "_" + timestamp;

        capabilities1.addProperty("browserName", "Chrome"); // Browsers allowed: `Chrome`, `MicrosoftEdge`, `pw-chromium`, `pw-firefox` and `pw-webkit`
        capabilities1.addProperty("browserVersion", "latest");
        ltOptions1.addProperty("platform", "Windows 10");
        ltOptions1.addProperty("name", testName);
        ltOptions1.addProperty("build", buildName);
        ltOptions1.addProperty("user", user);
        ltOptions1.addProperty("accessKey", accessKey);
        ltOptions1.addProperty("video", true);
        ltOptions1.addProperty("console", true);
        ltOptions1.addProperty("visual", true);
        capabilities1.add("LT:Options", ltOptions1);

        return new Object[]{
                capabilities1
        };
    }
}
