import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet({"/submitAnswer", "/startAcceptingAnswers", "/stopAcceptingAnswers"})
public class SubmitAnswerServlet extends HttpServlet {
    private static boolean acceptingAnswers = false;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        if (path.equals("/startAcceptingAnswers")) {
            acceptingAnswers = true;
            out.write("Started accepting answers");
            return;
        } else if (path.equals("/stopAcceptingAnswers")) {
            acceptingAnswers = false;
            out.write("Stopped accepting answers");
            return;
        }

        // Handle answer submission
        if (!acceptingAnswers) {
            response.setStatus(423); // Locked status code
            out.write("Answers are not being accepted right now. Please wait for the teacher to start.");
            return;
        }

        String userId = request.getParameter("user_id");
        String selectedOption = request.getParameter("selected_option");
        int questionId = Integer.parseInt(request.getParameter("question_id"));

        try {
            // Connect to your MySQL database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/clicker", "myuser", "xxxx");

            // Insert into answers table
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO answers (user_id, question_id, selected_option) VALUES (?, ?, ?)");

            stmt.setString(1, userId);
            stmt.setInt(2, questionId);
            stmt.setString(3, selectedOption);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                out.write("Answer submitted");
            } else {
                out.write("Submission failed");
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.write("Error: " + e.getMessage());
        }
    }
}
