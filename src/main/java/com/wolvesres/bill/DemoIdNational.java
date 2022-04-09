package com.wolvesres.bill;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author haotn
 */
public class DemoIdNational {

    public static void main(String[] args) {
        String id = "";
        for (int j = 0; j < 6; j++) {
            int random = (int) (Math.random() * 10 - 1);
            id += String.valueOf(random);
        }

        String[] listIsNotValidStrings = new String[]{"013", "016", "018", "021", "023", "028", "029", "032",
            "039", "041", "043", "047", "050", "053", "055", "057", "059",
            "061", "063", "065", "066", "069", "071", "073", "078", "081", "085", "088", "090"};

        List<String> listValid = new ArrayList<String>();
        for (int i = 1; i < 97; i++) {
            String nubString = "";
            if (i < 10) {
                if (i != 3 && i != 5 && i != 7 && i != 9) {
                    nubString = "00" + i;
                    listValid.add(nubString);
                }
            } else if (i >= 10) {
                boolean exist = false;
                for (int j = 0; j < listIsNotValidStrings.length; j++) {
                    nubString = "0" + i;
                    if (nubString.equals(listIsNotValidStrings[j])) {
                        exist = true;
                    }
                }
                if (!exist) {
                    listValid.add(nubString);
                }
            }
        }
        String baSoDau = "";

        while (baSoDau.length() == 0) {
            int random = (int) (Math.random() * 96 + 1);
            for (int i = 0; i < listValid.size(); i++) {
                String soString = "";
                if (random < 10) {
                    soString = "00" + random;
                    if (soString.equals(listValid.get(i))) {
                        baSoDau = soString;
                    }
                } else {
                    soString = "0" + random;
                    if (soString.equals(listValid.get(i))) {
                        baSoDau = soString;
                    }
                }
            }
        }

    }
}
