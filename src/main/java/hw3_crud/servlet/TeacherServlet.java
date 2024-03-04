package hw3_crud.servlet;

import com.google.gson.Gson;
import hw3_crud.entity.users.Teacher;
import hw3_crud.service.TeacherService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/teachers")
public class TeacherServlet extends HttpServlet {
    private TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String teacherJsonString = new Gson().toJson(teacherService.getAllTeachers());
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(teacherJsonString);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Teacher teacherEncoding = gson.fromJson(reader, Teacher.class);
        teacherService.createTeacher(teacherEncoding);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        Teacher teacherEncoding = gson.fromJson(reader, Teacher.class);
        teacherService.updateTeacher(id, teacherEncoding);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        teacherService.deleteTeacher(id);
    }
}
