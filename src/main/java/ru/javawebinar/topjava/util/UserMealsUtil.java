package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        LocalTime lta = LocalTime.of(7, 0);
        LocalTime ltb = LocalTime.of(12, 0);

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, lta, ltb, 2000);
        mealsTo.forEach(System.out::println);

        mealsTo = filteredByStreams(meals, lta, ltb, 2000);
        mealsTo.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesAmount = new HashMap<>();
        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();

        for (UserMeal meal : meals) {
            LocalDate ld = meal.getDateTime().toLocalDate();

            caloriesAmount.merge(ld, meal.getCalories(), (oldVal, newVal) -> oldVal + newVal);
        }

        for (UserMeal meal : meals) {
            LocalDate ld = meal.getDateTime().toLocalDate();
            LocalTime lt = meal.getDateTime().toLocalTime();

            int sumCalories = caloriesAmount.getOrDefault(ld, 0);

            if (TimeUtil.isBetweenHalfOpen(lt, startTime, endTime)) {
                boolean excess = sumCalories > caloriesPerDay;

                UserMealWithExcess mealWithExcess =
                        new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);

                mealsWithExcess.add(mealWithExcess);
            }
        }

        return mealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Collection<List<UserMeal>> list = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate())).values();

        return list.stream()
                .flatMap(dayMeals -> {
                    boolean excess = dayMeals.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay;
                    return dayMeals.stream()
                            .filter(meal -> isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                            .map(meal -> create(meal, excess));
                })
                .collect(toList());
    }

    private static UserMealWithExcess create(UserMeal meal, boolean excess) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}