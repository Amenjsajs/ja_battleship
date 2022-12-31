package battleship;

public enum ErrorMsg {
    ERROR_WRONG_LOCATION("Error! Wrong ship location!"),
    ERROR_WRONG_LENGTH("Error! Wrong length of the Submarine!"),
    ERROR_TOO_CLOSE("Error! You placed it too close to another one."),
    ERROR_WRONG_COORDINATES("Error! You entered the wrong coordinates!");

    private String msg;

    ErrorMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
