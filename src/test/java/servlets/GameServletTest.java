package servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import models.Quest;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

class GameServletTest {

    @Test
    void doGet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        GameServlet gameServlet = new GameServlet();

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("playerName")).thenReturn("Player");
        when(request.getRequestDispatcher("/game.jsp")).thenReturn(dispatcher);

        gameServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        GameServlet gameServlet = new GameServlet();
        Quest mockQuest = mock(Quest.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("quest")).thenReturn(mockQuest);
        when(request.getParameter("choice")).thenReturn("Go to the forest");

        gameServlet.doPost(request, response);

        verify(mockQuest).processUserChoice("Go to the forest");
        verify(response).sendRedirect(request.getRequestURI());
    }
}