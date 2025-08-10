import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/answerStats")
public class AnswerStatsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("question_id"); // Updated to "question_id"
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (idParam == null) {
            out.write("{\"error\": \"Missing question ID\"}");
            return;
        }

        try {
            int questionId = Integer.parseInt(idParam);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/clicker", "myuser", "xxxx");

            PreparedStatement stmt = conn.prepareStatement(
                "SELECT selected_option, COUNT(*) AS count " +
                "FROM answers WHERE question_id = ? GROUP BY selected_option");

            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            int a = 0, b = 0, c = 0, d = 0;

            while (rs.next()) {
                String option = rs.getString("selected_option");
                int count = rs.getInt("count");

                switch (option) {
                    case "A": a = count; break;
                    case "B": b = count; break;
                    case "C": c = count; break;
                    case "D": d = count; break;
                }
            }

            String json = String.format("{\"A\":%d,\"B\":%d,\"C\":%d,\"D\":%d}", a, b, c, d);
            out.write(json);

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.write("{\"error\": \"Server error\"}");
        }
    }
}