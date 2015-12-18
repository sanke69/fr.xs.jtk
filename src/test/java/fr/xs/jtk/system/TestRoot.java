package fr.xs.jtk.system;

import java.io.File;

public class TestRoot {
	
	public static void main(String... args) {
//		System.out.println( RootProcess.runtime().isExist("/home/sanke") );

		File f = new File("test.txt");
		System.out.println("Chemin absolu du fichier : " + f.getAbsolutePath());
		System.out.println("Nom du fichier : " + f.getName());

		for(File file : File.listRoots()) {
			System.out.println(file.getAbsolutePath());

			try {
				int i = 1;

				// On parcourt la liste des fichiers et répertoires
				for(File nom : file.listFiles()) {
					// S'il s'agit d'un dossier, on ajoute un "/"
					System.out.print("\t\t" + ((nom.isDirectory()) ? nom.getName() + "/" : nom.getName()));
					if((i % 4) == 0)
						System.out.print("\n");

					i++;
				}

				System.out.println("\n");
			} catch(NullPointerException e) {
				// L'instruction peut générer une NullPointerException
				// s'il n'y a pas de sous-fichier !
			}
		}

	}
}
