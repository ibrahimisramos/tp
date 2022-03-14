package seedu.duke.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.Parser;
import seedu.duke.Timetable;
import seedu.duke.events.Lesson;
import seedu.duke.exceptions.DuplicateEventException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.ErrorMessages.ERROR_INDEX_OUT_OF_BOUND;
import static seedu.duke.ErrorMessages.ERROR_INVALID_INDEX_FORMAT;

public class ParserTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void prepareDeleteCommand_InvalidIndex_throwException() {
        String inputString = "delete hello";
        Parser parser = new Parser(inputString);
        parser.parseCommand();
        assertEquals(ERROR_INVALID_INDEX_FORMAT + "\r\n",outputStreamCaptor.toString());
    }
}
