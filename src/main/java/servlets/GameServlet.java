package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.*;

import java.io.IOException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        String playerName = (String) session.getAttribute("playerName");

        if (playerName == null) {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        Quest quest = (Quest) session.getAttribute("quest");
        if (quest == null) {
            quest = new Quest();
            try {
                quest.initializeGame();
            } catch (Exception e) {
                throw new ServletException("Failed to initialize the game", e);
            }
            session.setAttribute("quest", quest);
        }

        Integer lastStepId = (Integer) session.getAttribute("lastStepId");
        if (lastStepId != null) {
            quest.setCurrentStepId(lastStepId);
        }

        if (quest.getCurrentStepId() < 0) {
            String finaleId = "FINALE_" + Math.abs(quest.getCurrentStepId());
            Finale finale = quest.getFinaleById(finaleId);
            req.setAttribute("finale", finale);
            quest.resetGame();
        } else {
            req.setAttribute("currentStep", quest.getCurrentStep());
        }
        req.getRequestDispatcher("/game.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String action = req.getParameter("action");

        if ("exit".equals(action)) {
            String playerName = (String) session.getAttribute("playerName");
            Integer gamesPlayed = (Integer) session.getAttribute("gamesPlayed");
            session.invalidate();
            resp.sendRedirect("index.jsp");
            return;
        }

        if (session.getAttribute("playerName") == null) {
            String playerName = req.getParameter("playerName");
            if (playerName != null && !playerName.isEmpty()) {
                session.setAttribute("playerName", playerName);

                resp.sendRedirect(req.getRequestURI());
                return;
            }
        }

        String userChoice = req.getParameter("choice");

        if (userChoice != null) {
            Quest quest = (Quest) session.getAttribute("quest");
            quest.processUserChoice(userChoice);

            session.setAttribute("lastStepId", quest.getCurrentStepId());
        }

        if ("restart".equals(action)) {
            Integer gamesPlayed = (Integer) session.getAttribute("gamesPlayed");
            session.setAttribute("gamesPlayed", gamesPlayed != null ? gamesPlayed + 1 : 1);
            Quest quest = (Quest) session.getAttribute("quest");
            quest.resetGame();
            session.setAttribute("lastStepId", null);
        }

        resp.sendRedirect(req.getRequestURI());
    }
}