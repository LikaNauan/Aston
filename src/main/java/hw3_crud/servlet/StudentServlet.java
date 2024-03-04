package hw3_crud.servlet;

import com.google.gson.Gson;
import hw3_crud.entity.users.Student;
import hw3_crud.service.StudentService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {
    private StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String studentJsonString = new Gson().toJson(studentService.getAllStudents());
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(studentJsonString);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Student studentEncoding = gson.fromJson(reader, Student.class);
        studentService.createStudent(studentEncoding);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        Student studentEncoding = gson.fromJson(reader, Student.class);
        studentService.updateStudent(id, studentEncoding);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        studentService.deleteStudent(id);
    }
}
