package hw3_crud.servlet;

import com.google.gson.Gson;
import hw3_crud.dto.LectureDTO;
import hw3_crud.entity.Lecture;
import hw3_crud.service.LectureService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/lectures")
public class LectureServlet extends HttpServlet {
    private LectureService lectureService = new LectureService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        LectureDTO lectureDTO = lectureService.getLectureWithStudentsAndTeachers(id);

        String lectureJsonString = new Gson().toJson(lectureDTO);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(lectureJsonString);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Lecture lectureEncoding = gson.fromJson(reader, Lecture.class);
        lectureService.createLecture(lectureEncoding);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        Lecture lectureEncoding = gson.fromJson(reader, Lecture.class);
        lectureService.updateLecture(id, lectureEncoding);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        lectureService.deleteLecture(id);
    }
}
