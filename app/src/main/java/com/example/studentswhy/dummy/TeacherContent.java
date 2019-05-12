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

    public static void addItem(Teacher item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getUrl(), item);
    }
}