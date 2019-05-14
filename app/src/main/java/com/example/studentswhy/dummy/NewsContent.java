package com.example.studentswhy.dummy;

import com.example.studentswhy.News;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
public class NewsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<News> ITEMS = new ArrayList<>();
    public static final List<News> searchItems = new ArrayList<>();
    public static final List<News> AllItems = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, News> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static
    {
        AllItems.addAll(ITEMS);
    }

    public static void addItem(News item)
    {
        ITEMS.add(item);
        ITEM_MAP.put(item.getTitle(), item);
    }
}