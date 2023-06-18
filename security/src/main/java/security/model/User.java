package security.model;

import org.evs.utils.GsonUtils;

import java.util.Map;

public class User {

    private Long id;
    private String name;
    private String password;

    private Map<String, Object> extra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return GsonUtils.toJsonString(this);
    }
}




