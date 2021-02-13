package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsDaoMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "/meals.jsp";
        String action = req.getParameter("action");
        long id = 0;

        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {}

        log.debug("GET: action: " + action + ", id:" + id);

        if (action != null && action.equals("create")) {
            forward = "/meal.jsp";
        } else if (action != null && action.equals("update")) {
            forward = "/meal.jsp";

            MealTo mealTo = new MealTo(MealsDaoMemory.getInstance().getOne(id), false);
            req.setAttribute("meal", mealTo);
        } else if (action != null && action.equals("delete")) {
            MealsDaoMemory.getInstance().delete(id);

            resp.sendRedirect("meals");
            return;
        } else {
            List<MealTo> mealsTO = MealsUtil.sortedList();
            req.setAttribute("meals", mealsTO);
        }

        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        long id = 0;
        LocalDateTime dateTime = LocalDateTime.now();
        String description = req.getParameter("description");
        int calories = 0;

        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {}

        try {
            dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        } catch (Exception e) {}

        try {
            calories = Integer.parseInt(req.getParameter("calories"));
        } catch (NumberFormatException e) {}

        MealTo mealTO = new MealTo(id, dateTime, description, calories, false);

        if (id > 0) {
            Meal meal = MealsDaoMemory.getInstance().getOne(id);
            MealsDaoMemory.getInstance().edit(mealTO);
            log.debug(String.format("POST: updated %s to %s", meal, mealTO));
        } else {
            MealsDaoMemory.getInstance().add(mealTO);
            log.debug("POST: created " + mealTO);
        }

        List<MealTo> mealsTO = MealsUtil.sortedList();
        req.setAttribute("meals", mealsTO);

        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
