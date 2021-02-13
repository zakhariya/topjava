package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.dao.MealsDaoMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    private static final int CALORIES_PER_DAY = 2000;

    public static List<MealTo> filteredSortedList(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .sorted(Comparator.comparing(MealTo::getDateTime))
                .collect(Collectors.toList());
    }

    public static List<MealTo> sortedList() {
        List<Meal> meals = MealsDaoMemory.getInstance().getAll();

        return filteredSortedList(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal, excess);
    }

    private static MealTo createTo(long id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        return new MealTo(id, dateTime, description, calories, excess);
    }

    public static int getCaloriesPerDay() {
        return CALORIES_PER_DAY;
    }
}
