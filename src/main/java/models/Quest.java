package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Quest {
    private List<QuestNode> questNodes = new ArrayList<>();
    private int currentStepId;
    private List<Finale> finales = new ArrayList<>();

    public void initializeGame() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream questStream = getClass().getClassLoader().getResourceAsStream("quest.json");
        InputStream finalesStream = getClass().getClassLoader().getResourceAsStream("finale.json");

        questNodes = mapper.readValue(questStream,
                mapper.getTypeFactory().constructCollectionType(List.class, QuestNode.class));
        finales = mapper.readValue(finalesStream,
                mapper.getTypeFactory().constructCollectionType(List.class, Finale.class));

        currentStepId = 1;
    }

    public QuestNode getCurrentStep() {
        return questNodes.stream()
                .filter(node -> node.getStep() == currentStepId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid step id: " + currentStepId));
    }

    public void processUserChoice(String userChoice){
        QuestNode currentStep = getCurrentStep();

        for (Choice choice : currentStep.getChoices()) {
            if (choice.getText().trim().equalsIgnoreCase(userChoice.trim())) {
                String nextStepId = choice.getNextStepId();
                if (nextStepId.startsWith("FINALE_")) {
                    currentStepId = -Integer.parseInt(nextStepId.replace("FINALE_", ""));
                    return;
                }
                currentStepId = Integer.parseInt(nextStepId);
                return;
            }
        }

        throw new RuntimeException("Incorrect user selection: " + userChoice);
    }


    public Finale getFinaleById(String finaleId) {
        return finales.stream()
                .filter(finale -> finale.getId().equals(finaleId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid finale id: " + finaleId));
    }

    public void resetGame() {
        currentStepId = 1;
    }

    public int getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(int currentStepId) {
        this.currentStepId = currentStepId;
    }
}
