package net.class101.server1.console.dto;

import net.class101.server1.console.dto.exception.NoMatchTopMenuInitialsException;

public class InputTopMenuDto {
    private static final String ORDER_STRING = "o";
    private static final String QUIT_STRING = "q";

    private final String topMenu;

    public InputTopMenuDto(String topMenu) {
        this.topMenu = checkValidTopMenu(topMenu);
    }

    private String checkValidTopMenu(String topMenu) {
        if (!ORDER_STRING.equals(topMenu) && !QUIT_STRING.equals(topMenu)) {
            throw new NoMatchTopMenuInitialsException();
        }

        return topMenu;
    }

    public String getTopMenu() {
        return topMenu;
    }
}
