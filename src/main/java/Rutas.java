package main.java;

import java.util.Objects;

public class Rutas {
    private String system;
    private String type;
    private String version;
    private String href;
    private String name;
    private String path;
    private String md5;
    private String file;

    public Rutas(String system, String type, String version, String href, String name, String path, String md5, String file) {
        this.system = system;
        this.type = type;
        this.version = version;
        this.href = href;
        this.name = name;
        this.path = path;
        this.md5 = md5;
        this.file = file;
    }

    public String getSystem() {
        return system;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public String getHref() {
        return href;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getMd5() {
        return md5;
    }

    public String getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "ListaDeRutas{" +
                "system='" + system + '\'' +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", href='" + href + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", md5='" + md5 + '\'' +
                ", file='" + file + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rutas)) return false;
        Rutas rutas = (Rutas) o;
        return getPath().equals(rutas.getPath()) &&
                getMd5().equals(rutas.getMd5()) &&
                getFile().equals(rutas.getFile());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getMd5(), getFile());
    }
}
