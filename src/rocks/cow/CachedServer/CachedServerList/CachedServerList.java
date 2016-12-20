package rocks.cow.CachedServer.CachedServerList;

import rocks.cow.CachedServer.CachedServer;

import java.util.ArrayList;
import java.util.Comparator;

public class CachedServerList extends ArrayList<CachedServer> {
    public final byte NAME = 0;
    public final byte ID = 1;

    public CachedServerList getSortedListBy(byte key) {
        CachedServerList temp = new CachedServerList();
        switch (key) {
            case(NAME):
                this.stream()
                        .sorted(Comparator.comparing(CachedServer::getName))
                        .forEach(temp::add);
                break;
            case(ID):
                this.stream()
                        .sorted(Comparator.comparingInt(CachedServer::getId).reversed())
                        .forEach(temp::add);
                break;
        }
        return temp;
    }
}
