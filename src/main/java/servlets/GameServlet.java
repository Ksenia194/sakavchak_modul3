package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Quest;
import models.QuestNode;

import java.io.IOException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        Quest quest = (Quest) request.getSession().getAttribute("quest");
        if (quest == null) {
            quest = new Quest();
            try {
                quest.initializeGame();
            } catch (Exception e) {
                throw new ServletException("Failed to initialize the game", e);
            }
            request.getSession().setAttribute("quest", quest);
        }

        QuestNode currentStep = quest.getCurrentStep();

        request.setAttribute("currentStep", currentStep);
        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userChoice = req.getParameter("choice");

        System.out.println("User choice: " + userChoice);

        if (userChoice == null || userChoice.isEmpty()) {
            throw new RuntimeException("Invalid user choice: " + userChoice);
        }

        Quest quest = (Quest) req.getSession().getAttribute("quest");
        quest.processUserChoice(userChoice);

        resp.sendRedirect(req.getRequestURI());
    }
}