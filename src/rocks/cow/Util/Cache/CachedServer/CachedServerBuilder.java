package rocks.cow.Util.Cache.CachedServer;

public class CachedServerBuilder {
    private String name;
    private String address;
    private int id;

    public CachedServerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CachedServerBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public CachedServerBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CachedServer createCachedServer() {
        return new CachedServer(name, address, id);
    }
}