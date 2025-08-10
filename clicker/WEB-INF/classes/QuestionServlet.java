import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String idParam = request.getParameter("question_id"); 
        if (idParam == null) {
            out.write("{\"error\":\"Missing question ID\"}");
            return;
        }

        try {
            int questionId = Integer.parseInt(idParam);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/clicker", "myuser", "xxxx");

            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM questions WHERE question_id = ?");
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String json = String.format(
                    "{ \"question_id\": %d, \"text\": \"%s\", \"a\": \"%s\", \"b\": \"%s\", \"c\": \"%s\", \"d\": \"%s\" }",
                    rs.getInt("question_id"),
                    escape(rs.getString("question_text")),
                    escape(rs.getString("option_a")),
                    escape(rs.getString("option_b")),
                    escape(rs.getString("option_c")),
                    escape(rs.getString("option_d"))
                );
                out.write(json);
            } else {
                out.write("{\"error\":\"Question not found\"}");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.write("{\"error\":\"Server error\"}");
        }
    }

    private String escape(String str) {
        return str == null ? "" : str.replace("\"", "\\\"").replace("\n", " ");
    }
}
