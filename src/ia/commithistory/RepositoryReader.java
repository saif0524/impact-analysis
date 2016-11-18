package ia.commithistory;

import java.io.IOException;

import javax.swing.JFileChooser;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;

public class RepositoryReader {

	private Repository repository;

	private Git git;

	public Repository getRepo() {
		return repository;
	}

	public void setRepo(Repository repo) {
		this.repository = repo;
	}

	public Repository repoReader() {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("/home"));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Select Git Rpository");

		int returnVal = chooser.showOpenDialog(null);

		if ((returnVal == JFileChooser.APPROVE_OPTION)) {
			if (chooser.getSelectedFile().getName().contains(".git")) {
				System.out.println("You chose to open directory: "
						+ chooser.getSelectedFile().getName());
			}

			else {
				System.out.println("You did not select any git repository");
				return null;
			}

		} else {
			System.out.println("Nothing selected");
			return null;
		}

		try {
			Repository repo = new FileRepository(chooser.getSelectedFile());
			Git git = new Git(repo);

			setRepo(repo);
			setGit(git);
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return getRepo();

	}

	public Git getGit() {
		return git;
	}

	public void setGit(Git git) {
		this.git = git;
	}

	
	public static void main(String[] args) throws AmbiguousObjectException, IOException{
		RepositoryReader rd = new RepositoryReader();
		
		rd.repoReader();
		ObjectId currentHead = rd.getRepo().resolve("HEAD^{tree}");
		System.out.println(rd.getGit());
		System.out.println("Current head: " + currentHead);
	;}
}
