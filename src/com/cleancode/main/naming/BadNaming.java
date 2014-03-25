package com.cleancode.main.naming;

import java.util.*;

/**
 * Created by mwittman on 25/03/2014.
 */
public class BadNaming {
    static final float MN_QUANT_FLOAT = 0.0f;

    public static void main(String[] args) {
        String p1 = "P1307";
        String p2 = "P038833";

        POListCollection POListCollection = new POListCollection();
        POListCollection.addPord(p1, 5);
        POListCollection.addPord(p2, 10);

        SpecReduPr specReduPr = new SpecReduPr();
        specReduPr.setDscAmntPU(p1, 6, 0.5f);
        specReduPr.setDscAmntPU(p2, 12, 0.8f);

        List<COItem> coItemCollection = new ArrayList<>();
        coItemCollection.add(new COItem(p1, 6));
        coItemCollection.add(new COItem(p2, 11));

        int t = BadNaming.convertPOToPPU(POListCollection, specReduPr, coItemCollection);
        System.out.println("T === " + t);
    }

    public static class NSPException extends RuntimeException {
        public NSPException(String pn) {
            super("NaP: " + pn);
        }
    }

    public static class POListCollection {
        Map<String, Integer> POPLs = new HashMap<>();

        public void addPord(String pnuc, int pppu) {
            POPLs.put(pnuc, pppu);
        }

        public int getPPU(String pn) {
            if (!POPLs.containsKey(pn)) {
                throw new NSPException(pn);
            }
            return POPLs.get(pn);
        }
    }

    public static class SpecReduPr {
        Map<String, Float> prPercDi = new HashMap<>();
        Map<String, Integer> minPQ = new HashMap<>();

        public void setDscAmntPU(String pn, int q, float rft) {
            prPercDi.put(pn, rft);
            minPQ.put(pn, q);
        }

        public float getDscP(COItem coItem) {
            if (!prPercDi.containsKey(coItem.pn) ||
                    coItem.tots < minPQ.get(coItem.pn)) {
                return MN_QUANT_FLOAT;
            }
            return prPercDi.get(coItem.pn);
        }
    }

    public static class COItem {
        public COItem(String pn2, int tots) {
            this.pn = pn2;
            this.tots = tots;
        }

        public int tots;
        public String pn;
    }

    public static int convertPOToPPU(
            POListCollection POListCollection,
            SpecReduPr specReduPr,
            Collection<COItem> collection) {

        int t = 0;

        for (COItem COItem : collection) {
            int c = COItem.tots * POListCollection.getPPU(COItem.pn);
            float dp = specReduPr.getDscP(COItem);
            if (dp > 0) {
                c *= 1.0 - dp;
            }
            t += c;
        }
        return t;
    }
}