import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/correctAnswer")
public class CorrectAnswerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String questionId = request.getParameter("question_id");
        response.setContentType("application/json");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/clicker", "myuser", "xxxx");

            String query = "SELECT correct_option FROM questions WHERE question_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, questionId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String correctOption = rs.getString("correct_option");
                response.getWriter().write("{\"correct_option\": \"" + correctOption + "\"}");
            } else {
                response.getWriter().write("{\"correct_option\": null}");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500); // Internal Server Error
            response.getWriter().write("{\"error\": \"Server error: " + e.getMessage() + "\"}");
        }
    }
}