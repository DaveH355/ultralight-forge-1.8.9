package examplemod.client.ultralight.opengl.util;

import com.labymedia.ultralight.input.UltralightKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.labymedia.ultralight.input.UltralightKey.*;


public class UltralightKeyMapper {
    //minecraft keys mapped to ultralight ones
    private static final Map<Integer, UltralightKey> keyMap = new ConcurrentHashMap<>();

    static {
        keyMap.put(57, SPACE);
        keyMap.put(40, OEM_7);
        keyMap.put(51, OEM_COMMA);
        keyMap.put(12, OEM_MINUS);
        keyMap.put(52, OEM_PERIOD);
        keyMap.put(53, OEM_2);
        keyMap.put(11, NUM_0);
        keyMap.put(2, NUM_1);
        keyMap.put(3, NUM_2);
        keyMap.put(4, NUM_3);
        keyMap.put(5, NUM_4);
        keyMap.put(6, NUM_5);
        keyMap.put(7, NUM_6);
        keyMap.put(8, NUM_7);
        keyMap.put(9, NUM_8);
        keyMap.put(10, NUM_9);
        keyMap.put(39, OEM_1);
        keyMap.put(13, OEM_PLUS);
        keyMap.put(30, A);
        keyMap.put(48, B);
        keyMap.put(46, C);
        keyMap.put(32, D);
        keyMap.put(18, E);
        keyMap.put(33, F);
        keyMap.put(34, G);
        keyMap.put(35, H);
        keyMap.put(23, I);
        keyMap.put(36, J);
        keyMap.put(37, K);
        keyMap.put(38, L);
        keyMap.put(50, M);
        keyMap.put(49, N);
        keyMap.put(24, O);
        keyMap.put(25, P);
        keyMap.put(16, Q);
        keyMap.put(19, R);
        keyMap.put(31, S);
        keyMap.put(20, T);
        keyMap.put(22, U);
        keyMap.put(47, V);
        keyMap.put(17, W);
        keyMap.put(45, X);
        keyMap.put(21, Y);
        keyMap.put(44, Z);
        keyMap.put(26, OEM_4);
        keyMap.put(43, OEM_5);
        keyMap.put(27, OEM_6);
        keyMap.put(41, OEM_3);
        keyMap.put(1, ESCAPE);
        keyMap.put(28, RETURN);
        keyMap.put(15, TAB);
        keyMap.put(14, BACK);
        keyMap.put(210, INSERT);
        keyMap.put(211, DELETE);
        keyMap.put(205, RIGHT);
        keyMap.put(203, LEFT);
        keyMap.put(208, DOWN);
        keyMap.put(200, UP);
        keyMap.put(201, PRIOR);
        keyMap.put(209, NEXT);
        keyMap.put(199, HOME);
        keyMap.put(207, END);
        keyMap.put(58, CAPITAL);
        keyMap.put(70, SCROLL);
        keyMap.put(69, NUMLOCK);
        keyMap.put(183, SNAPSHOT);
        keyMap.put(197, PAUSE);
        keyMap.put(59, F1);
        keyMap.put(60, F2);
        keyMap.put(61, F3);
        keyMap.put(62, F4);
        keyMap.put(63, F5);
        keyMap.put(64, F6);
        keyMap.put(65, F7);
        keyMap.put(66, F8);
        keyMap.put(67, F9);
        keyMap.put(68, F10);
        keyMap.put(87, F11);
        keyMap.put(88, F12);
        keyMap.put(100, F13);
        keyMap.put(101, F14);
        keyMap.put(102, F15);
        //f16-f24 unmapped
        keyMap.put(82, NUMPAD0);
        keyMap.put(79, NUMPAD1);
        keyMap.put(80, NUMPAD2);
        keyMap.put(81, NUMPAD3);
        keyMap.put(75, NUMPAD4);
        keyMap.put(76, NUMPAD5);
        keyMap.put(77, NUMPAD6);
        keyMap.put(71, NUMPAD7);
        keyMap.put(72, NUMPAD8);
        keyMap.put(73, NUMPAD9);
        keyMap.put(83, DECIMAL);
        keyMap.put(181, DIVIDE);
        keyMap.put(55, MULTIPLY);
        keyMap.put(74, SUBTRACT);
        keyMap.put(78, ADD);
        keyMap.put(54, SHIFT); // right shift
        keyMap.put(42, SHIFT); //left shift
        keyMap.put(29, CONTROL); // left control
        keyMap.put(157, CONTROL); // right control
        keyMap.put(56, MENU); // left alt
        keyMap.put(184, MENU); // right alt
        keyMap.put(219, LWIN);
        keyMap.put(220, RWIN);
    }

    public enum KeyType {
        CHAR,
        ACTION;
    }

    public static KeyType getKeyType(int key) {
        switch (key) {
            case 14: // backspace
            case 28: //enter
                return KeyType.ACTION;
            default:
                return KeyType.CHAR;
        }


    }

    public static UltralightKey getKey(int key) {
        return keyMap.getOrDefault(key, UNKNOWN);
    }
}
