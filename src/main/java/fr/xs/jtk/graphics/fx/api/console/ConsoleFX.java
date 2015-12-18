package fr.xs.jtk.graphics.fx.api.console;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

public class ConsoleFX extends TextArea {

    public ConsoleFX() {
    	super();

		MenuItem mClear     = new MenuItem("Clear");
		MenuItem mCopy      = new MenuItem("Copy");
		MenuItem mSelectAll = new MenuItem("Select All");
		MenuItem mAttach    = new MenuItem("Attach");
		MenuItem mDetach    = new MenuItem("Detach");

		mClear     . setOnAction((ActionEvent event) -> clear());
		mCopy      . setOnAction((ActionEvent event) -> copy());
		mSelectAll . setOnAction((ActionEvent event) -> selectAll());
		mAttach    . setOnAction((ActionEvent event) -> attach());
		mDetach    . setOnAction((ActionEvent event) -> detach());

		setEditable(false);
		setContextMenu(new ContextMenu(mCopy, mSelectAll, mClear, mAttach, mDetach));

		attach();
    }

	public void attach() {
		PrintStream printStream = new PrintStream(new FilterOutputStream(new ByteArrayOutputStream()) {
			public void write(byte b[]) throws IOException {
				appendText(new String(b));
			}

			public void write(byte b[], int off, int len) throws IOException {
				appendText(new String(b, off, len));
			}
		});

		System.setOut(printStream);
		System.setErr(printStream);
	}

	public void detach() {
		System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(java.io.FileDescriptor.out), 128), true));
		System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream(java.io.FileDescriptor.err), 128), true));
	}

}
