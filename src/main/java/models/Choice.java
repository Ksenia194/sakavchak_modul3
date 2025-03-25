package models;

public class Choice {
    public String text;
    public int nextStepId;

    public void setText(String text) {
        this.text = text;
    }

    public void setNextStepId(int nextStepId) {
        this.nextStepId = nextStepId;
    }

    public String getText() {
        return text;
    }

    public int getNextStepId() {
        return nextStepId;
    }
}
