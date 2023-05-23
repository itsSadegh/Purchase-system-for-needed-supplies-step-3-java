package ut.ie.baloot3.models;

public class Provider {
    @Override
    public String toString() {
        return "Provider{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registryDate='" + registryDate + '\'' +
                '}';
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getRegistryDate() { return registryDate; }

    private int id;

    private String name;

    private String registryDate;
}
