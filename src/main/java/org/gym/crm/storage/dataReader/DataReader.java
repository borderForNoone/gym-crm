package org.gym.crm.storage.dataReader;

import java.util.List;

public interface DataReader {
    List<String[]> readData(String source);
}
