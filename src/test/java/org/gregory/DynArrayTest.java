package org.gregory;

import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

class DynArrayTest {

    DynArray arrayStringEmpty;
    DynArray arrayString;
    DynArray arrayIntEmpty;
    DynArray arrayInt;
    DynArray arrayDouble;
    DynArray arrayDoubleEmpty;

    @BeforeEach
    void setUp() {
        arrayStringEmpty = new DynArray<String>(String.class);
        arrayString = new DynArray<String>(String.class);

        arrayIntEmpty = new DynArray<Integer>(Integer.class);
        arrayInt = new DynArray<Integer>(Integer.class);

        arrayDoubleEmpty = new DynArray<Double>(Double.class);
        arrayDouble = new DynArray<Double>(Double.class);

        arrayInt.appendArr(
                0, 1, 2, 3, 4
        );

        arrayString.appendArr(
                "ноль",
                "один",
                "два",
                "три",
                "четыре"
        );

        arrayDouble.appendArr(
                0.0,
                1.1,
                2.2,
                3.3,
                4.4
        );
    }

    @AfterEach
    void afterAll() {
        arrayStringEmpty = null;
        arrayString = null;

        arrayIntEmpty = null;
        arrayInt = null;

        arrayDoubleEmpty = null;
        arrayDouble = null;
    }

    @Test
    void getItem() {
        assertThat("ноль", is(arrayString.getItem(0)));
        assertThat("ноль", is(not(arrayString.getItem(4))));
        assertThat("четыре", is(arrayString.getItem(4)));
    }


    @Test
    void getItem_NullElement() {
        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> arrayString.getItem(100),
                "index 100 does not exist"
        );

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> arrayStringEmpty.getItem(0),
                "Array is empty"
        );
    }

    @Test
    void append() {
        DynArray arrayIntActual = new DynArray<Integer>(Integer.class) {{
            append(0);
            append(1);
            append(2);
            append(3);
            append(4);
        }};

        assertThat(arrayIntActual, is(new DynArray<Integer>(Integer.class) {{
            append(0);
            append(1);
            append(2);
            append(3);
            append(4);
        }}));

        assertThat(arrayIntActual, is(not(new DynArray<Integer>(Integer.class) {{
            append(0);
            append(1);
            append(2);
        }})));

        assertThat(arrayIntActual, is(not(new DynArray<Integer>(Integer.class) {{
            append(0);
            append(1);
            append(2);
            append(7000);
            append(4);

        }})));

        arrayIntActual.appendArr(
                5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
        );

        assertThat(arrayIntActual.getCapacity(), is(16));
        assertThat(arrayIntActual.getCount(), is(16));


        arrayIntActual.appendArr(16);

        assertThat(arrayIntActual.getCapacity(), is(32));
        assertThat(arrayIntActual.getCount(), is(17));

    }


    @Test
    void insertNull() {
        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> arrayString.insert("word", 100),
                "index 100 does not exist"
        );

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> arrayStringEmpty.insert("wor1d", 100),
                "Array is empty"
        );

    }

    @Test
    void insertFirstElement_NullArray() {
        DynArray arrayActual = new DynArray<String>(String.class);
        arrayActual.insert("qwerty", 0);

        DynArray arrayExpected = new DynArray<String>(String.class);
        arrayExpected.append("qwerty");

        assertThat(arrayActual, is(arrayExpected));
        assertThat(arrayActual, is(not(arrayStringEmpty)));
        assertThat(arrayActual.getItem(0), is("qwerty"));
    }

    @Test
    void insertFirstElement() {
        DynArray arrayExpected = new DynArray<String>(String.class);
        arrayExpected.appendArr(
                "НОЛЬ",
                "ноль",
                "один",
                "два",
                "три",
                "четыре"
        );

        arrayString.insert("НОЛЬ", 0);

        assertThat(arrayString, is(arrayExpected));
        assertThat(arrayString.getCapacity(), is(16));
        assertThat(arrayString.getCount(), is(6));
    }


    @Test
    void insertEnd() {

        double val = .1;
        for (int i = 5; i < 17; i++) {
            if (i == 10) {
                val *= val;
            }
            arrayDouble.insert(i + i * val, i);
        }

        assertThat(arrayDouble.getCount(), is(17));
        assertThat(arrayDouble.getCapacity(), is(32));

        arrayDouble.remove(16);

        assertThat(arrayDouble.getCount(), is(16));
        assertThat(arrayDouble.getCapacity(), is(21));

        arrayDouble.remove(15);

        assertThat(arrayDouble.getCount(), is(15));
        assertThat(arrayDouble.getCapacity(), is(21));

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> arrayIntEmpty.remove(15),
                "Array is empty"
        );

        DynArray doubleDynArray = new DynArray<Double>(Double.class);

        val = .1;
        for (int i = 0; i < 15; i++) {
            if (i == 10) {
                val *= val;
            }
            doubleDynArray.append(i + i * val);
        }
        assertThat(arrayDouble, is(not(doubleDynArray)));
        doubleDynArray.makeArray(21);
        assertThat(arrayDouble, is(doubleDynArray));
    }

    @Test
    void insertStringEnd() {
        DynArray arrayExpected = new DynArray<String>(String.class);
        arrayExpected.appendArr(
                "ноль",
                "один",
                "два",
                "три",
                "четыре",
                "ПЯТЬ"
        );

        arrayString.insert("ПЯТЬ", 5);

        assertThat(arrayString, is(arrayExpected));
        assertThat(arrayString.getCapacity(), is(16));
        assertThat(arrayString.getCount(), is(6));

        arrayString.insert("",6);
        arrayString.insert("",7);
        arrayString.insert("",8);
        arrayString.insert("",9);
        arrayString.insert("",10);
        arrayString.insert("",11);
        arrayString.insert("",12);
        arrayString.insert("",13);
        arrayString.insert("",14);
        arrayString.insert("",15);

        assertThat(arrayString.getCapacity(), is(16));
        assertThat(arrayString.getCount(), is(16));

        arrayString.insert("",16);
        assertThat(arrayString.getCapacity(), is(32));
        assertThat(arrayString.getCount(), is(17));

        arrayString.remove(0);
        assertThat(arrayString.getCapacity(), is(21));
        assertThat(arrayString.getCount(), is(16));
        assertThat(arrayString.getItem(4), is("ПЯТЬ"));
    }

    @Test
    void remove_NullElementOrIndexOfBounds() {
        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> arrayInt.remove(100),
                "index 100 does not exist"
        );

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> arrayIntEmpty.remove(0),
                "Array is empty"
        );
    }

    @Test
    public void remove_FirstElement_1Element() {
        DynArray dynArrayActual = new DynArray<Integer>(Integer.class);
        dynArrayActual.append(0);
        dynArrayActual.remove(0);

        assertThat(dynArrayActual, is(arrayIntEmpty));
    }

    @Test
    public void remove_FirstElement_ManyElements() {
        DynArray dynArrayActual = new DynArray<Integer>(Integer.class);
        dynArrayActual.appendArr(0, 1, 2, 3);
        dynArrayActual.remove(0);

        DynArray dynArrayExpected = new DynArray<Integer>(Integer.class);
        dynArrayExpected.appendArr(1, 2, 3);

        assertThat(dynArrayActual, is(dynArrayExpected));
    }

    @Test
    public void remove_MiddleElement() {
        DynArray dynArrayActual = new DynArray<Integer>(Integer.class);
        dynArrayActual.appendArr(0, 1, 2, 3, 4);
        dynArrayActual.remove(2);

        DynArray dynArrayExpected = new DynArray<Integer>(Integer.class);
        dynArrayExpected.appendArr(0, 1, 3, 4);

        assertThat(dynArrayActual, is(dynArrayExpected));
        assertThat(dynArrayActual.getCount(), is(4));
        assertThat(dynArrayActual.getCapacity(), is(16));
    }

    @Test
    public void remove_EndElement() {

        double val = .1;
        for (int i = 5; i < 17; i++) {
            if (i == 10) {
                val *= val;
            }
            arrayDouble.append(i + i * val);
        }

        assertThat(arrayDouble.getCount(), is(17));
        assertThat(arrayDouble.getCapacity(), is(32));

        arrayDouble.remove(16);

        assertThat(arrayDouble.getCount(), is(16));
        assertThat(arrayDouble.getCapacity(), is(21));

        arrayDouble.remove(15);

        assertThat(arrayDouble.getCount(), is(15));
        assertThat(arrayDouble.getCapacity(), is(21));

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> arrayIntEmpty.remove(15),
                "Array is empty"
        );

        DynArray doubleDynArray = new DynArray<Double>(Double.class);

        val = .1;
        for (int i = 0; i < 15; i++) {
            if (i == 10) {
                val *= val;
            }
            doubleDynArray.append(i + i * val);
        }
        assertThat(arrayDouble, is(not(doubleDynArray)));
        doubleDynArray.makeArray(21);
        assertThat(arrayDouble, is(doubleDynArray));
    }

    @Test
    public void remove_EndElement_DecreaseCapacity() {
        DynArray dynArrayActual = new DynArray<Integer>(Integer.class);
        dynArrayActual.appendArr(0, 1, 2, 3, 4);
        dynArrayActual.remove(4);

        DynArray dynArrayExpected = new DynArray<Integer>(Integer.class);
        dynArrayExpected.appendArr(0, 1, 2, 3);

        assertThat(dynArrayActual, is(dynArrayExpected));
        assertThat(dynArrayActual.getCount(), is(4));
        assertThat(dynArrayActual.getCapacity(), is(16));
    }

    /*
    Вставка / удаление:

    Вид списка:
    1. Пустой список
    2. 1 элемент
    3. Много элементов

    Место вставки / удаления:
    1. Первый элемент
    2. Последний
    3. Середина

    Наполненность:
    1. Много места
    2. Нужно расширять / убавлять (проверять краевые условия)

    Тип массива:
    1. String
    2. Integer
    3. Double
    */
}