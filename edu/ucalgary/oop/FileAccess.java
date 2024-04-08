package edu.ucalgary.oop;

import java.util.ArrayList;
import java.nio.file.*;

public interface FileAccess {
    public ArrayList<String> readFileLines(Path URL);
}
