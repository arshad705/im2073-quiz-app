import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet({"/startAcceptingAnswers", "/stopAcceptingAnswers"})
public class AnswerControlServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        
        if (path.equals("/startAcceptingAnswers")) {
            SubmitAnswerServlet.startAcceptingAnswers();
            response.getWriter().write("Started accepting answers");
        } else if (path.equals("/stopAcceptingAnswers")) {
            SubmitAnswerServlet.stopAcceptingAnswers();
            response.getWriter().write("Stopped accepting answers");
        }
    }
} 