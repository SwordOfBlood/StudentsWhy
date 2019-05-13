package com.example.studentswhy.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class FAQContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static
    {
        addItem(new DummyItem("1","#ВШЭ", ""));
        addItem(new DummyItem("\t1.1", "#ФКН", ""));
        addItem(new DummyItem("\t\t1.1.1", "#ПМИ",""));
        addItem(new DummyItem("\t\t1.1.2", "#ПИ", ""));
        addItem(new DummyItem("\t1.2", "#ФМ", ""));
        addItem(new DummyItem("\t1.3", "#ФФ", ""));
        addItem(new DummyItem("\t1.4", "#МИЭМ", ""));
        addItem(new DummyItem("\t1.5", "#ФБМ", ""));
        addItem(new DummyItem("\t1.6", "#ФП", ""));
        addItem(new DummyItem("\t1.7", "#ФГН", ""));
        addItem(new DummyItem("\t1.8", "#ФСН", ""));
        addItem(new DummyItem("\t1.9", "#ФКМД", ""));
        addItem(new DummyItem("\t1.10", "#ФМЭМП", ""));
        addItem(new DummyItem("\t1.11", "#ФЭН", ""));
        addItem(new DummyItem("\t1.12", "#МИЭФ", ""));
        addItem(new DummyItem("\t1.13", "#ФГРР", ""));
        addItem(new DummyItem("\t1.14", "#ФХ", ""));
        addItem(new DummyItem("\t1.15", "#ФББ", ""));
        addItem(new DummyItem("2", "#События", ""));
        addItem(new DummyItem("3", "#Внеучебка", ""));
        addItem(new DummyItem("\t3.1", "#Ролевой клуб ВШЭ", ""));
    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.content, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "HashTag " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem
    {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
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