package utils;

public class AgentIdMemorizer {
    private int id = -1;

    public void forgetId() {
        id = -1;
    }

    public void memorizeId(int id) {
        this.id = id;
    }

    public boolean checkId(int id) {
        return id != this.id && this.id != -1;
    }
}
