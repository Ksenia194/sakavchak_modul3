package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestTest {

    @Test
    void textInitializeGame() {
        Quest quest = new Quest();
        try {
            quest.initializeGame();
        } catch (Exception e) {
            fail("Game initialization failed");
        }
        assertNotNull(quest.getQuestNodes(), "Quest nodes should be initialized");
        assertNotNull(quest.getFinales(), "Finales should be initialized");
    }

    @Test
    void testProcessUserChoice() {
        Quest quest = new Quest();
        try {
            quest.initializeGame();
        } catch (Exception e) {
            fail("Game initialization failed");
        }
        QuestNode currentStep = quest.getCurrentStep();
        String userChoice = currentStep.getChoices().get(0).getText();
        quest.processUserChoice(userChoice);

        String expectedNextStepId = currentStep.getChoices().get(0).getNextStepId();
        assertEquals(Integer.parseInt(expectedNextStepId), quest.getCurrentStepId(), "Step id should be updated after user choice");
    }

}