public enum Numbers {
    ONE("one", 1),
    TWO("two", 2),
    THREE("three", 3),
    FOUR("four", 4),
    FIVE("five", 5),
    SIX("six", 6),
    SEVEN("seven", 7),
    EIGHT("eight", 8),
    NINE("nine", 9);
    private final String number;
    private final int value;

    private Numbers(String number, int value){
       this.number = number;
       this.value = value;
    }

    public String getNumber() {
        return number;
    }

    public int getValue() {
        return value;
    }

    public static Numbers fromCode(String code){
        for (var number : Numbers.values()){
            if (code.equals(number.getNumber())){
                return number;
            }
        }
        return null;
    }
}
