package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Quest {
    private List<QuestNode> questNodes = new ArrayList<>();
    private int currentStepId;
    private List<Finale> finales = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Quest.class);

    public void initializeGame() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream questStream = getClass().getClassLoader().getResourceAsStream("quest.json");
        InputStream finalesStream = getClass().getClassLoader().getResourceAsStream("finale.json");

        questNodes = mapper.readValue(questStream,
                mapper.getTypeFactory().constructCollectionType(List.class, QuestNode.class));
        finales = mapper.readValue(finalesStream,
                mapper.getTypeFactory().constructCollectionType(List.class, Finale.class));

        logger.debug("Quest nodes size : {} and {} finales", questNodes.size(), finales.size());
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
                    Finale finale = getFinaleById("FINALE_" + Math.abs(currentStepId));
                    logger.info("Game over. Finale: \"{}\"", finale.getDescription());
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

    public List<QuestNode> getQuestNodes() {
        return questNodes;
    }

    public List<Finale> getFinales() {
        return finales;
    }
}
