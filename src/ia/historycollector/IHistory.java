package ia.historycollector;

import java.io.File;
import java.util.List;

public interface IHistory {

    public File getCurrentFile() throws Exception;

    public List<IFileHistory> getHistoryFiles() throws Exception;

}
