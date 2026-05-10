package demo;

import java.io.Serializable;

public class jiadian implements Switch, Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private boolean status;

    @Override
    public void press() {
        status = !status;
        System.out.println(name + "开关状态：" + status);
    }

    public jiadian() {
    }

    public jiadian(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
