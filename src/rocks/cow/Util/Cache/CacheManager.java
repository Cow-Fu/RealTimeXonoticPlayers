package rocks.cow.Util.Cache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.SystemUtils;
import rocks.cow.Util.Cache.CachedServer.CachedServer;
import rocks.cow.Util.Cache.CachedServer.CachedServerList.CachedServerList;
import rocks.cow.Util.FileManager;

import java.io.IOException;
import java.lang.reflect.Type;

public class CacheManager {
    private FileManager fm = new FileManager();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String fp = System.getProperty("user.home");
    private CachedServerList cachedServers;

    public CacheManager() {
        setOs();
        fm.setFile(fp);
    }

    private void setOs() {
        fp = System.getProperty("user.home");

        if (SystemUtils.IS_OS_LINUX) {
            fp += ".cache/XonoticServerCache.json";
        } else if(SystemUtils.IS_OS_WINDOWS){
            // fp += "";  //figure out a path
        }
    }

    private CacheManager createCache() {
        if (!fm.fileExists()) {
            fm.createFile();
        }
        return this;
    }

    public void loadCache() {
        String data = null;
        try {
            if ((data = fm.read()) != null) {
                if (!data.equals("")) {
                    Gson gson = new GsonBuilder().create();
                    cachedServers = new CachedServerList();
                    cachedServers.addAll(gson.fromJson(data, (Type) CachedServer[].class));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CachedServerList getCachedServers() {
        return cachedServers;
    }
}
