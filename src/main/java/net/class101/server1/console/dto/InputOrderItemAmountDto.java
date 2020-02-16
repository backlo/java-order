package net.class101.server1.console.dto;

public class InputOrderItemAmountDto {
    private final int itemAmount;

    public InputOrderItemAmountDto(String itemAmount) {
        this.itemAmount = checkValidAmount(itemAmount);
    }

    private int checkValidAmount(String itemAmount) {
        try {
            return Integer.parseInt(itemAmount);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("잘못 입력하였습니다. 다시 입력해주세요!");
        }
    }

    public int getItemAmount() {
        return itemAmount;
    }
}
