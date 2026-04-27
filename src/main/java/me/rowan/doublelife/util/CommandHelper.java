package me.rowan.doublelife.util;

import java.util.ArrayList;
import java.util.List;

public class CommandHelper {

    public static List<String> getAdministrativeCommands() {
        List<String> arguments = new ArrayList<>();
        arguments.add("help");
        arguments.add("distributeplayers");
        arguments.add("lives");
        arguments.add("pair");
        arguments.add("randomizepairs");
        arguments.add("setup");
        arguments.add("unpair");
        arguments.add("reload");
        arguments.add("config");
        return arguments;
    }

    public static String toLivesString(int livesNum) {
        return livesNum == 1 ? "life" : "lives";
    }

}
