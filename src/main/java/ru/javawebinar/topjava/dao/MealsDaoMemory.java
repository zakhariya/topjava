package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class MealsDaoMemory {
    private static MealsDaoMemory instance;
    private long currentId;

    private static final Map<Long, Meal> meals = new HashMap<>();

    static {
        meals.put(1L, new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(2L, new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(3L, new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(4L, new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(5L, new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(6L, new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(7L, new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        meals.put(8L, new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 1, 2, 0), "Ночь", 300));
        meals.put(9L, new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 1, 9, 0), "Завтрак", 300));
        meals.put(10L, new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 1, 22, 0), "Ужин", 600));
        meals.put(11L, new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 1, 13, 30), "Обед", 500));
    }

    private MealsDaoMemory() {
    }

    public static MealsDaoMemory getInstance() {
        if (instance == null) {
            instance = new MealsDaoMemory();
        }

        return instance;
    }

    public synchronized long getAutoIncrementId() {
        return ++currentId;
    }

    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    public Meal getOne(long id) {
        return meals.get(id);
    }

    public void add(MealTo mealTO) {
        Meal meal = new Meal(mealTO.getDateTime(), mealTO.getDescription(), mealTO.getCalories());

        meals.put(currentId, meal);
    }

    public void edit(MealTo mealTO) {
        long id = mealTO.getId();

        Meal meal = meals.get(id);

        if (meal != null) {
            meal.setDateTime(mealTO.getDateTime());
            meal.setDescription(mealTO.getDescription());
            meal.setCalories(mealTO.getCalories());

            meals.put(id, meal);
        }
    }

    public void delete(long id) {
        meals.remove(id);
    }
}
