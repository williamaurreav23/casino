package at.steiner.casino.domain.enumeration;

/**
 * The Transaction enumeration.
 */
public enum Transaction {
    START(0),
    ROULETTE(1),
    BLACK_JACK(2),
    POKER(3),
    STOCK(4),
    OTHER(5);

    private final int value;

    private Transaction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Transaction fromValue(int value) {
        switch (value) {
            case 0:
                return START;
            case 1:
                return ROULETTE;
            case 2:
                return BLACK_JACK;
            case 3:
                return POKER;
            case 4:
                return STOCK;
            default:
                return OTHER;
        }
    }
}
