package com.example.studentswhy.dummy;

import com.example.studentswhy.Lesson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class LessonContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Lesson> ITEMS = new ArrayList<Lesson>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Lesson> ITEM_MAP = new HashMap<String, Lesson>();

    public static void addItem(Lesson item)
    {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId().toString(), item);
    }

    /**
     * A Lesson item representing a piece of content.
     */
    public static class LessonItem
    {
        public final String id;
        public final String content;
        public final String details;

        public LessonItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
