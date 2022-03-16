package seedu.duke.commands;

import seedu.duke.Timetable;

public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";

    @Override
    public String execute(Timetable timetable) {
        timetable.clear();
        return printClearConfirmation();
    }

    private static String printClearConfirmation() {
        return "Your whole timetable has been cleared";
    }

}
