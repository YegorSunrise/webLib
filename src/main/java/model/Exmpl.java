package model;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class Exmpl implements Serializable {

    private String name = "CDI";

    private String inner;

    public void doInner() {
        System.out.println(inner);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInner() {
        return inner;
    }

    public void setInner(String inner) {
        this.inner = inner;
    }
}
