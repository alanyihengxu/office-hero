package s25.cs151.application.controller.sort;

import java.util.List;

public interface EntrySort<T> {
    List<T> readAndSort(String filepath);
}
