package main.java.console;

import java.util.HashMap;
import java.util.Map;

/**
 * A konzol parancsok kezelését végző osztály.
 * Összeköti a felhasználói felületet a parancsok végrehajtásával.
 */
public class ConsoleHandler {

    public boolean abortProcess;
    private ConsoleGUI consoleGUI;
    private final Commands commands;
    private final Map<String, Command> commandMap;

    public ConsoleHandler() {
        this.commands = new Commands(this);
        this.abortProcess = false;
        this.commandMap = initializeCommands();
    }

    public void printToConsole(String message) {
        if (consoleGUI != null && consoleGUI.isVisible()) {
            consoleGUI.appendToConsole(message);
        }
    }

    /**
     * Inicializálja a parancsokat.
     * Beállítja a parancsokhoz tartozó végrehajtási logikát.
     * @return a parancsok térképe
     */
    private Map<String, Command> initializeCommands() {
        Map<String, Command> map = new HashMap<>();

        map.put("help", args -> {
            if (args.length < 2) {
                String helpText = getHelpText();
                printToConsole(helpText);
            } else {
                commands.printHelp(args[1]);
            }
        });

        map.put("reset", e -> {
            //RESTARTOLNI KELL A GAMET
            printToConsole("Game has been reset");
        });

        map.put("exit", e -> {
            abortProcess = true;
            if (consoleGUI != null) {
                consoleGUI.dispose();
            }
        });

        map.put("exit_game", e -> System.exit(0));

        map.put("remove", args -> {
            if (args.length == 2) {

            } else {
                printToConsole("Invalid format! Use 'help remove' for correct usage");
            }
        });

        map.put("save", args -> {
            if (args.length == 2) {
                commands.saveFile(args[1]);
            } else {
                printToConsole("Invalid format! Use 'help save' for correct usage");
            }
        });

        map.put("load", args -> {
            if (args.length == 2) {
                commands.loadFile(args[1]);
            } else {
                printToConsole("Invalid format! Use 'help load' for correct usage");
            }
        });

        map.put("set", args -> {
            switch (args.length) {
                case 4 -> {
                    if (args[1].equals("player")) {
                        commands.setGameValue();
                    } else if (args[1].equals("entity")) {
                    } else {
                    }
                }
                case 3 -> commands.setGameValue();
                default -> printToConsole("""
                    Invalid format for 'set' command.
                    Usage: set entity arg1 value
                    Entity types: *Enemy, Player
                    Arguments: speed, health, maxhealth""");
            }
        });

        map.put("get", args -> {
            switch (args.length) {
                case 3 -> {
                    switch (args[1]) {
                        default -> printToConsole("Unknown entity type! Use 'help' for available commands");
                    }
                }
                case 2 -> commands.getGameValue();
                default -> printToConsole("""
                    Invalid format for 'get' command.
                    Usage: get entity arg1
                    Entity types: *Enemy, Player
                    Arguments: speed, health""");
            }
        });

        map.put("add", args -> {
        });

        map.put("script", args -> {
            if (args.length == 2) {
                commands.runScript(args[1]);
            } else {
                printToConsole("Invalid format! Use 'help script' for correct usage");
            }
        });

        map.put("make", args -> {
            if (args.length == 2) {
                commands.createFile(args[1], consoleGUI);
            } else {
                printToConsole("Invalid format! Use 'help make' for correct usage");
            }
        });

        return map;
    }

    public void startConsoleInput() {
        if (consoleGUI == null) {
            consoleGUI = new ConsoleGUI(this);
        }
        consoleGUI.showConsole();
        consoleGUI.appendToConsole(getHelpText());
    }

    public void executeCommand(String input) {
        if (input.isEmpty()) return;
        String[] parts = input.trim().toLowerCase().split("\\s+");
        Command command = commandMap.get(parts[0]);
        if (command != null) {
            try {
                command.execute(parts);
            } catch (Exception exc) {
                printToConsole("Error executing command: " + exc.getMessage());
            }
        } else {
            printToConsole("Unknown command. Type 'help' for available commands.");
        }
    }

    private String getHelpText() {
        return """
                            Available commands:
            -------------------------------------------------------
            | help [command] : Show help for a specific command   |
            | reset          : Reset the game                     |
            | remove         : Remove entities                    |
            | save/load      : Save/Load game state               |
            | add            : Add entities or objects            |
            | script         : Run a script file                  |
            | make           : Create a new script file           |
            | exit           : Exit console mode                  |
            | exit_game      : Exit the game                      |
            -------------------------------------------------------
            Type 'help <command>' for detailed usage information.""";
    }
}