package rocks.cow.Server.ServerList;

import rocks.cow.Server.Server;

import java.util.ArrayList;
import java.util.Comparator;

public class ServerList extends ArrayList<Server> {
    // TODO: Add other options to retrieve servers by
    public static final byte PLAYERS = 0;

    public ServerList getSortedListBy(byte key) {
        ServerList temp = new ServerList();
        switch (key) {
            case 0: // Players
                this.stream()
                        .filter(server -> server.getNumplayers() > 0)
                        .sorted(Comparator.comparingInt(Server::getNumplayers).reversed())
                        .forEach(temp::add);
                break;
            default:
                return null;
        }
        return temp;
    }
}
