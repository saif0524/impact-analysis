package ia.historycollector;

import java.io.File;
import java.util.Calendar;

public interface IFileHistory {

    public File getFile() throws Exception;

    public String getAuthor();

    public String getComment();

    public Calendar getCommitDate();

}
