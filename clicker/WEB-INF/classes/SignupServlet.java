import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;



@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone"); // Get phone number

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/clicker", "myuser", "xxxx");

            // Prepare SQL query to insert data
            String query = "INSERT INTO users (username, password, phone) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, phone); // Insert phone number

            int rowsInserted = stmt.executeUpdate();

            response.setContentType("text/plain");
            if (rowsInserted > 0) {
                response.getWriter().write("Signup successful!");
            } else {
                response.getWriter().write("Signup failed.");
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500); // Internal server error
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}


