package rocks.cow.Server.ServerList;

import rocks.cow.Server.Server;

import java.util.ArrayList;

public class ServerList extends ArrayList<Server> {
    // TODO: Add other options to retrive servers by
    public static final byte PLAYERS = 0;

    public ServerList getBy(byte key) {
        ServerList temp = new ServerList();
        switch (key) {
            case 0: // Players
                for (Server server : this) {
                    if (server.getNumplayers() > 0) {
                        temp.add(server);
                    }
                }
                break;
            default:
                return null;
        }
        return temp;
    }
}
