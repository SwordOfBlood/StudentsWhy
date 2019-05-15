package com.example.studentswhy.dummy;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(Integer.toString(item.valueId), item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem
    {
        public DummyItem(String id, String content, int details, int parentId, int valueId)
        {
            valueText = content;
            this.valueId = valueId;
            itemParent = details == 0;
            this.parentId = parentId;
            this.details = details;
        }
        public int details;
        private String valueText = ""; //название значения
        public int valueId = 0; //идентификатор значения

        private boolean itemParent = false; //родительский или нет элемент
        private int parentId = -1; //id элемента, который является родительским
        private boolean childVisibility = false; //видимость дочерних элементов

        //проверить родительский элемент или нет
        public boolean isItemParent() {
            return itemParent;
        }

        //установить значение родительского элемента
        public void setItemParent(boolean newItemParent) {
            itemParent = newItemParent;
        }

        //проверить видимость дочерних элементов
        public boolean isChildVisibility() {
            return childVisibility;
        }

        //установить видимость для дочерних элементов
        public void setChildVisibility() {
            childVisibility = !childVisibility;
        }

        //получить номер родительского элемента
        public int getParentId() {
            return parentId;
        }

        //установить номер родительского элемента
        public void setParentId(int newParentId) {
            parentId = newParentId;
        }

        //получить название значения
        public String getValueText() {
            return valueText;
        }

        //установить название значения
        public void setValueText(String newValueText) {
            valueText = newValueText;
        }
    }
}