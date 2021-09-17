package fr.martdel;

public class Item {

    private String name;
    private int count;
    private boolean is_tag;

    public Item(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public boolean isTag() {
        return is_tag;
    }

    public void setIsTag(boolean is_tag) {
        this.is_tag = is_tag;
    }
}
