package seedu.duke.commands;

import seedu.duke.Timetable;

public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";

    @Override
    public String execute(Timetable timetable) {
        for (int i = 0; i < timetable.size(); i++) {
            timetable.remove(i);
        }
        return printClearConfirmation();
    }

    private static String printClearConfirmation() {
       return "Your whole timetable has been cleared";
    }

}
