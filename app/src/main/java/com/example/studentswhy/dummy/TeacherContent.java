package com.example.studentswhy.dummy;

import com.example.studentswhy.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TeacherContent
{
    public static final List<Teacher> ITEMS = new ArrayList<>();

    public static final Map<String, Teacher> ITEM_MAP = new HashMap<>();


    static
    {
        addItem(new Teacher("1" ,"Авдошин Сергей Михайлович",
                "https://www.hse.ru/staff/avdoshin",
                "+7(495) 772-9590 *22521",
                "savdoshin@hse.ru",
                "Ординарный профессор, Факультет компьютерных наук;" +
                        "Департамент программной инженерии: руководитель департамента, Профессор"));

        addItem(new Teacher("2" ,"Береснева Екатерина Николаевна",
                "https://www.hse.ru/staff/beresneva",
                "+7(499)152-14-71",
                "eberesneva@hse.ru",
                "Факультет компьютерных наук; " +
                        "Департамент программной инженерии: Преподаватель"));

        addItem(new Teacher("3" ,"Горденко Мария Константиновна",
                "https://www.hse.ru/staff/gordenko",
                "+7(499)152-14-71",
                "mgordenko@hse.ru",
                "Факультет компьютерных наук; " +
                        "Департамент программной инженерии: Преподаватель"));

        addItem(new Teacher("4" ,"Горяинова Елена Рудольфовна",
                "https://www.hse.ru/org/persons/439121",
                "+7(495) 621-13-42",
                "egoryainova@hse.ru",
                "Факультет экономических наук; " +
                        "Департамент математики: Доцент"));

        addItem(new Teacher("5" , "Дегтярев Константин Юрьевич",
                "https://www.hse.ru/staff/kdegtiarev",
                "+7(495) 772-9590",
                "kdegtiarev@hse.ru",
                "Факультет компьютерных наук;" +
                        "Департамент программной инженерии: Доцент"));
    }

    private static void addItem(Teacher item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getUrl(), item);
    }
}