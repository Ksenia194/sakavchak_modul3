package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Quest {
    private List<QuestNode> questNodes = new ArrayList<>();
    private int currentStepId;
    private HttpSession session;

    public void initializeGame() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("quest.json");

        if (inputStream == null) {
            throw new RuntimeException("Quest data file is missing or empty");
        }

        questNodes = mapper.readValue(inputStream,
                mapper.getTypeFactory().constructCollectionType(List.class, QuestNode.class));

        if (questNodes == null || questNodes.isEmpty()) {
            throw new RuntimeException("Quest data file is empty or failed to load.");
        }

        currentStepId = questNodes.get(0).getId();
    }

    public QuestNode getCurrentStep() {
        if (questNodes == null || questNodes.isEmpty()) {
            throw new RuntimeException("Quest is not initialize");
        }

        for (QuestNode node : questNodes) {
            if (node.getId() == currentStepId) {
                return node;
            }
        }
        throw new RuntimeException("Invalid step id: " + currentStepId);
    }

    public void processUserChoice(String userChoice) {
        QuestNode currentStep = getCurrentStep();
        List<Choice> choices = currentStep.getChoices();

        if (choices == null || choices.isEmpty()) {
            throw new RuntimeException("No available choices for this step");
        }

        for (Choice choice : choices) {
            if (choice.getText().equals(userChoice)) {
                currentStepId = choice.getNextStepId();
                return;
            }
        }

        throw new RuntimeException("Invalid user choice: " + userChoice);
    }

    public void savePlayerData(String playerName, int gamesPlayed) {
        if (session == null) {
            throw new RuntimeException("Session is not initialized");
        }

        session.setAttribute("playerName", playerName);
        session.setAttribute("currentStepId", currentStepId);
        session.setAttribute("gamesPlayed", gamesPlayed);
    }

    public void resetGame() {
        currentStepId = 1;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public List<QuestNode> getQuestNodes() {
        if (questNodes == null || questNodes.isEmpty()) {
            throw new RuntimeException("Quest data is not initialize");
        }
        return questNodes;
    }

    public int getCurrentStepId() {
        return currentStepId;
    }
}
