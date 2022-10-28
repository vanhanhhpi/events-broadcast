package hpilib.coreu.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import hpilib.coreu.utils.model.NanoRandomType;

import java.util.Random;

public class NanoUtils {

    public static String randomId(NanoRandomType.RandomType type, Integer size) {// QueryParam cho phép
        // chọn biến enum nào
        // muốn ra
        String id = ""; // Integer hàm như int nhưng có thể null
        Random random = new Random();
        if (size == null) {
            size = 20;
        }
        if (type == null)
            type = NanoRandomType.RandomType.NUMBER_STRING;
        switch (type) {
            case NUMBER_STRING:
                char[] alphabet = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
                id = NanoIdUtils.randomNanoId(random, alphabet, size);
                break;
            case STRING:
                char[] alphabetString = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                        'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
                id = NanoIdUtils.randomNanoId(random, alphabetString, size);
                break;
            case NUMBER:
                char[] number = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
                id = NanoIdUtils.randomNanoId(random, number, size);
                break;
            default:
                break;
        }
        return id;
    }
}
