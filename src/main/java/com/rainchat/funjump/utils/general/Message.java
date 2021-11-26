package com.rainchat.funjump.utils.general;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Message {
    HELP("Messages.help", "Type &b/fjump help &7to look at the help."),
    NO_COMMAND_PERMISSION("Messages.no-command-permission", "You do not have permissions for that command."),
    NO_PERMISSION("Messages.no-permission", "You do not have permissions."),
    PLAYER_OFFLINE("Messages.player-offline", "The player &b{0} &7does not seem to be online."),

    WIN("Messages.win", "&7You &awon &7the jump! You score: &e{0}"),
    LOSE("Messages.lose", "&7You &clost &7the jump! You score: &e{0}"),
    SPEED("Messages.speed", "&7Platform speed increased: &a{0}&7!."),
    START("Messages.start", "&7The game will begin through."),
    PREPARE_TO_JUMP("Messages.prepare-to-jump", "&bPREPARE TO START!"),
    NOT_PLAYER("Messages.not-player", "&cYou must be a Player to do that!"),

    JOIN_ARENA("Messages.join", "&7Successfully joined &a{0}"),
    LEAVE_ARENA("Messages.leave","&aYou left the arena."),
    NOT_IN_ARENA("Messages.not-in-arena","&cYou are not in a jump arena, so you cannot leave one!"),
    REMOVE_ARENA("Messages.remove", "&7You remove arena &a{0}."),
    CREATE_ARENA("Messages.create", "&aSuccessfully created an arena! Do not forget to set platforms and failZone!"),
    NO_ARENA("Messages.no-arena", "&cThere is no arena called &e{0}&c!"),
    FULL_ARENA("Messages.full-arena", "&cThat arena is not available right now! (It is start already)"),
    ALREADY_IN_ARENA("Messages.already-in-arena", "&cYou are already in a arena! You cannot join another one yet. Finish your current arena first!"),

    PREFIX("Messages.prefix", "&7[&dFunJump&7]");

    private String path, def;
    private List<String> list;
    private static FileConfiguration configuration;

    Message(String path, String def) {
        this.path = path;
        this.def = def;
    }

    Message(String path, List<String> list) {
        this.path = path;
        this.list = list;
    }

    public String getDef() {
        return configuration.getString(path, def);
    }

    @Override
    public String toString() {
        return Chat.color(configuration.getString(path, def));
    }

    public List<String> toList() {
        return configuration.getStringList(path);
    }

    public static void setConfiguration(FileConfiguration configuration) {
        Message.configuration = configuration;
    }

    public String getPath() {
        return path;
    }

    public List<String> getList() {
        return list;
    }
}
