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
        addItem(new DummyItem("1","#Вопросы по поступлению ", 0));
        addItem(new DummyItem("\t1.1", "#Перед подачей документов", 0));
        addItem(new DummyItem("\t\t1.1.1", "С чего начать поступление в Вышку?",6));
        addItem(new DummyItem("\t\t1.1.2", "В Вышке есть бюджетные места?", 7));
        addItem(new DummyItem("\t1.2", "#Подача документов", 0));
        addItem(new DummyItem("\t\t1.2.1", "В какие сроки мне нужно подать документы?", 4));
        addItem(new DummyItem("\t\t1.2.2", "Как подать документы?", 5));
        addItem(new DummyItem("\t1.3", "#Конкурс", 0));
        addItem(new DummyItem("\t\t1.3.1", "Сколько мне нужно баллов?", 10));
        addItem(new DummyItem("\t\t1.3.2", "Сколько лет действует олимпиада?", 12));
        addItem(new DummyItem("\t\t1.3.3", "Какие документы нужно принести в приёмную комиссию, если я поступаю по олимпиаде?", 13));
        addItem(new DummyItem("\t\t1.3.4", "У меня есть две льготы в 100 баллов ЕГЭ. Могу ли я претендовать на каждую из них?", 14));
        addItem(new DummyItem("\t\t1.3.5", "У меня есть две льготы БВИ. Могу ли я претендовать на каждую из них?", 15));
        addItem(new DummyItem("2", "#5 Мифов о Вышке", 0));
        addItem(new DummyItem("\t2.1", "Миф № 1. Учат только экономике?", 17));
        addItem(new DummyItem("\t2.2", "Миф № 2: В этом университете учатся преимущественно москвичи?", 18));
        addItem(new DummyItem("\t2.3", "Миф № 3. Это платный вуз?", 19));
        addItem(new DummyItem("\t2.4", "Миф № 4. Вышка — это только дополнительное образование?", 20));
        addItem(new DummyItem("\t2.4", "Миф № 5. В Вышке одни «мажоры»?", 21));
        addItem(new DummyItem("3", "#Скидки на обучение", 0));
        addItem(new DummyItem("\t3.1", "Кто может рассчитывать на скидку в бакалавриате?", 23));
        addItem(new DummyItem("\t3.2", "Я выпускник лицея НИУ ВШЭ, полагается ли мне скидка?", 24));
        addItem(new DummyItem("4", "#Общежития НИУ ВШЭ", 0));
        addItem(new DummyItem("\t4.1", "Кому предоставляется общежития?", 26));
        addItem(new DummyItem("\t4.2", "Что такое Трилистник?", 27));
        addItem(new DummyItem("\t4.3", "Что такое Дубки?", 28));
        addItem(new DummyItem("\t4.4", "Что такое Шестерка?", 29));
        addItem(new DummyItem("5", "Что такое ЛМС?", 30));
        addItem(new DummyItem("6", "Как поучиться в иностранном вузе по обмену?", 31));
        addItem(new DummyItem("7", "Как выучить дополнительный иностранный язык?", 32));
        addItem(new DummyItem("8", "#Спорт", 0));
        addItem(new DummyItem("\t8.1", "В вышке есть спорт. секции?", 34));
        addItem(new DummyItem("\t\t8.1.1", "Что такое ССК?", 35));
    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.content, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem
    {
        public final String id;
        public final String content;
        public final int details;
        public int level;
        public DummyItem(String id, String content, final int details) {
            this.id = id;
            this.content = content;
            this.details = details;
            level = 0;
            for (int i = 0; i < id.length(); i++)
                if(id.charAt(i)== '\t')
                    level++;
        }
        private String answer;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}