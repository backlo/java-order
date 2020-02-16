package net.class101.server1.console.dto;

public class InputOrderItemIdDto {
    private final int itemId;

    public InputOrderItemIdDto(String itemId) {
        this.itemId = checkValidId(itemId);
    }

    private int checkValidId(String itemId) {
        if (itemId.isEmpty()) {
            return -1;
        }
        try {
            return Integer.parseInt(itemId);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("잘못 입력하였습니다. 다시 입력해주세요!");
        }
    }

    public int getItemId() {
        return itemId;
    }
}
