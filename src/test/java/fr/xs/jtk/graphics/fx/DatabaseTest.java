package fr.xs.jtk.graphics.fx;

import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import fr.xs.jtk.graphics.fx.ApplicationFX;
import fr.xs.jtk.graphics.fx.api.database.DatabaseColumnFX;
import fr.xs.jtk.graphics.fx.api.database.DatabaseFX;
import fr.xs.jtk.graphics.fx.nodes.DatabaseModel;

public class DatabaseTest extends ApplicationFX {
	DatabaseFX<DatabaseModel> theDatabaseView = null;

	@Override
    public Scene scene() {
		DatabaseColumnFX<?>[] viewModel = null;
		try {
			viewModel = new DatabaseColumnFX<?>[] {
					new DatabaseColumnFX<DatabaseModel>(DatabaseModel.class,  "name", 128, false, DatabaseModel.class.getMethod("getName", null)),
					new DatabaseColumnFX<DatabaseModel>(DatabaseModel.class, "value", 512, false, DatabaseModel.class.getMethod("getValue", null))
			};
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		theDatabaseView = new DatabaseFX<DatabaseModel>(DatabaseModel.class, null, viewModel);
		
	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(10, 10, 0, 10));

		grid.getChildren().add(theDatabaseView.getParent());
		
    	return new Scene(grid);
    }

	@Override
	public void run(String[] args) {
		System.out.println("FUCK OFF " + args);

    	DatabaseModel[] data = new DatabaseModel[] {
    			new DatabaseModel("1", "2"),
    			new DatabaseModel("2", "2"),
    			new DatabaseModel("3", "2"),
    			new DatabaseModel("4", "2"),
    			new DatabaseModel("5", "2"),
    			new DatabaseModel("6", "2"),
    			new DatabaseModel("7", "2")
    	};
 
        for(DatabaseModel e : data) {
        	theDatabaseView.addEntry(e);

			try {
				Thread.sleep(500);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
        }

        int i = 0;
        while(i>=0) {
        	theDatabaseView.addEntry(new DatabaseModel("test"+i++, "2"));

			try {
				Thread.sleep(500);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
        }

		int k = -1;
		while(true) {
			k = ((k + 1) % 3);
			System.out.println(k == 0 ? "TIC" : k == 1 ? "TAC" : "TOE");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

    public static void main(String[] args) {
    	launch( ApplicationFX.arguments = args );
    }

}
