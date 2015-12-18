package fr.xs.jtk.graphics.fx;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import fr.xs.jtk.graphics.fx.ApplicationFX;

public class ConsoleFX extends ApplicationFX {

	private StackPane root     = null;
	private TextArea  textArea = null;

	@Override
    public Scene scene() {
		textArea = new TextArea();
		textArea.setEditable(false);
		MenuItem mClear = new MenuItem("Clear");
		mClear.setOnAction((ActionEvent event) -> textArea.clear());
		MenuItem mCopy = new MenuItem("Copy");
		mCopy.setOnAction((ActionEvent event) -> textArea.copy());
		MenuItem mSelectAll = new MenuItem("Select All");
		mSelectAll.setOnAction((ActionEvent event) -> textArea.selectAll());
		textArea.setContextMenu(new ContextMenu(mCopy, mSelectAll, mClear));

		root = new StackPane();
		root.getChildren().add(textArea);

		initialize();

    	return new Scene(root);
    }

	@Override
	public void run(String[] args) {
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



	public void initialize() {
		PrintStream printStream = new PrintStream(new FilterOutputStream(new ByteArrayOutputStream()) {
			public void write(byte b[]) throws IOException {
				textArea.appendText(new String(b));
			}

			public void write(byte b[], int off, int len) throws IOException {
				textArea.appendText(new String(b, off, len));
			}
		});

		System.setOut(printStream);
		System.setErr(printStream);
	}

	public void dispose() {
		System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(java.io.FileDescriptor.out), 128), true));
		System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream(java.io.FileDescriptor.err), 128), true));
	}

}
