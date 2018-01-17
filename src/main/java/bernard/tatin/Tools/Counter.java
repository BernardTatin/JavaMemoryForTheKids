package bernard.tatinrecommendations

class Counter {
    private int current = -1;
    private int max;

    public Counter(int theMax) {
        max = theMax;
    }

    public int getValue() {
        current++;
        if (current >= max) {
            current = 0;
        }
        return current;
    }

//    public int reset() {
//        current = 0;
//        return current;
//    }
}
