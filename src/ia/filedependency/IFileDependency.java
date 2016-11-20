package ia.filedependency;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface IFileDependency {
	public HashMap<String, HashSet<String>> getFileDependency(List<File> listFile);
}
