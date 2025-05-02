package s25.cs151.application.model.sort;

import java.util.List;

public interface EntrySort<T> {
    List<T> readAndSort(String filepath);
}
