package ch.template.remote;

public class TodoJson {

    private String userId;
    private int id;
    private String title;
    private boolean completed;

    public String getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
