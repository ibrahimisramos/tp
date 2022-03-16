package seedu.duke;

import seedu.duke.commands.*;
import seedu.duke.exceptions.InvalidDayException;
import seedu.duke.exceptions.MissingValueException;
import seedu.duke.exceptions.InvalidTimeException;
import seedu.duke.exceptions.InvalidModeException;

import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.duke.common.ErrorMessages.ERROR_INVALID_INDEX_FORMAT;
import static seedu.duke.common.ErrorMessages.ERROR_MISSING_PARAMETERS;
import static seedu.duke.common.ErrorMessages.ERROR_MISSING_VALUES;
import static seedu.duke.common.ErrorMessages.ERROR_INVALID_DAY;
import static seedu.duke.common.ErrorMessages.ERROR_INVALID_TIME;
import static seedu.duke.common.ErrorMessages.ERROR_INVALID_MODE;

public class Parser {
    private final String command;
    private final String arguments;
    public static Logger logger = Logger.getLogger(Parser.class.getName());

    private static final int NAME_INDEX = 0;
    private static final int TITLE_INDEX = 1;
    private static final int DAY_INDEX = 2;
    private static final int STARTTIME_INDEX = 3;
    private static final int ENDTIME_INDEX = 4;
    private static final int MODE_INDEX = 5;
    private static final String[] HEADINGS = {"n/", "l/", "d/", "st/", "et/", "m/"};

    public Parser(String input) {
        this.command = getCommandFromInput(input);
        this.arguments = getArgumentsFromInput(input);
    }

    public Command parseCommand() {
        switch (command) {
        case AddCommand.COMMAND_WORD:
            return prepareAdd();
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case DeleteCommand.COMMAND_WORD:
            return prepareDelete();
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();
        default:
            return new HelpCommand();
        }
    }

    public Command prepareAdd() {
        try {
            String[] eventDescription = splitArguments();
            checkValidityOfArguments(eventDescription);
            String day = eventDescription[DAY_INDEX].toLowerCase();
            int startTime = Integer.parseInt(eventDescription[STARTTIME_INDEX]);
            int endTime = Integer.parseInt(eventDescription[ENDTIME_INDEX]);
            String mode = eventDescription[MODE_INDEX].toLowerCase();
            checkDay(day);
            checkTime(startTime, endTime);
            checkMode(mode);
            String name = eventDescription[NAME_INDEX];
            String title = eventDescription[TITLE_INDEX];
            return new AddCommand(name, title, day, startTime, endTime, mode);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException npe) {
            return new CommandResult(ERROR_MISSING_PARAMETERS);
        } catch (MissingValueException mve) {
            return new CommandResult(ERROR_MISSING_VALUES);
        } catch (InvalidTimeException | NumberFormatException ite) {
            return new CommandResult(ERROR_INVALID_TIME);
        } catch (InvalidDayException ide) {
            return new CommandResult(ERROR_INVALID_DAY);
        } catch (InvalidModeException ime) {
            return new CommandResult(ERROR_INVALID_MODE);
        }
    }

    private void checkTime(int startTime, int endTime) throws InvalidTimeException {
        int startMinutes = startTime % 100;
        int endMinutes = endTime % 100;
        boolean invalidMinutes = startMinutes >= 60 || endMinutes >= 60;
        int startHours = startTime / 100;
        int endHours = endTime / 100;
        boolean invalidHours = startHours >= 24 || endHours >= 24;
        if (invalidMinutes || invalidHours || startTime > endTime) {
            throw new InvalidTimeException();
        }
    }

    private void checkValidityOfArguments(String[] eventDescription) throws MissingValueException {
        for (int i = 0; i < MODE_INDEX; i++) {
            if (eventDescription[i].length() == 0) {
                throw new MissingValueException();
            }
        }
    }

    private void checkMode(String mode) throws InvalidModeException {
        if (mode.equals("online") || mode.equals("physical")) {
            return;
        }
        throw new InvalidModeException();
    }

    private void checkDay(String day) throws InvalidDayException {
        switch (day) {
        case "monday":
        case "tuesday":
        case "wednesday":
        case "thursday":
        case "friday":
        case "saturday":
        case "sunday":
            break;
        default:
            throw new InvalidDayException();
        }
    }

    /**
     * Try to parse the delete command to see if index has been done.
     *
     */
    public Command prepareDelete() {
        try {
            int index = Integer.parseInt(arguments);
            return new DeleteCommand(index);
        } catch (NumberFormatException nfe) {
            System.out.println(ERROR_INVALID_INDEX_FORMAT);
            logger.log(Level.INFO, "Invalid index to delete Error detected.");
            return new HelpCommand();
        }
    }

    private String getCommandFromInput(String input) {
        return input.split(" ")[0].trim().toLowerCase();
    }

    private String getArgumentsFromInput(String input) {
        String str = "";
        int spaceIndex = input.trim().indexOf(" ");
        if (spaceIndex != -1) {
            str = input.substring(spaceIndex + 1).trim();
        }
        return str;
    }

    private String[] splitArguments() {
        String[] eventDescription = new String[6];
        int index = -1;
        for (String str : arguments.split(" ")) {
            if (checkHeadings(str) == -1) {
                eventDescription[index] += " " + str;
                eventDescription[index] = eventDescription[index].trim();
            } else {
                index = checkHeadings(str);
                eventDescription[index] = str.substring(str.indexOf("/") + 1);
            }
        }
        return eventDescription;
    }

    private int checkHeadings(String str) {
        for (int i = 0; i < HEADINGS.length; i++) {
            if (str.contains(HEADINGS[i])) {
                return i;
            }
        }
        return -1;
    }
}
