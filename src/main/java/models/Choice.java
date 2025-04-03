package models;

public class Choice {
    private String text;
    private String nextStepId;

    public void setText(String text) {
        this.text = text;
    }

    public void setNextStepId(String nextStepId) {
        this.nextStepId = nextStepId;
    }

    public String getText() {
        return text;
    }

    public String getNextStepId() {
        return nextStepId;
    }
}
