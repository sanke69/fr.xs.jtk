package fr.xs.jtk.graphics.fx.api.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.xs.jtk.graphics.fx.api.abstracts.PanelFX;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;

public class DatabaseFX<T> implements PanelFX {
    Class<T>          clazz  = null;
	TableView<T>      table  = null;
    ObservableList<T> data   = null;
	List<T>           buffer = null;

    public DatabaseFX(Class<T> _model, T[] _data, DatabaseColumnFX<?>... _models) {
//    	setInstance(this);

    	clazz  = _model;
    	buffer = Collections.synchronizedList(new ArrayList<T>());

        table = new TableView<T>();

        for(DatabaseColumnFX<?> dbCol : _models) {
            TableColumn<T, String> column = new TableColumn<>(dbCol.label);

            column.setPrefWidth(dbCol.width);
//          column.prefWidthProperty().bind(table.widthProperty().divide(4));
			column.setCellValueFactory((CellDataFeatures<T, String> p) -> {
				try {
					return new ReadOnlyStringWrapper((String) dbCol.method.invoke(p.getValue(), (Object[]) null));
				} catch (Exception e) {
					e.printStackTrace();
					return new ObservableValue<String>() {
						@Override public String getValue() { return null; }
						@Override public void   addListener(ChangeListener<? super String> listener) {}
						@Override public void   removeListener(ChangeListener<? super String> listener) {}
						@Override public void   addListener(InvalidationListener listener) {}
						@Override public void   removeListener(InvalidationListener listener) {}
					};
				}
			});

            table.getColumns().add(column);
        }

        table.setEditable(true);

        
        data = FXCollections.observableArrayList();

        table.setItems(data);
        if(_data != null)
	        for(T m : _data)
	        	addEntry(m);

//    	Platform.runLater(() -> updateMethod() );
//      Executors.newCachedThreadPool().execute(updateTask); 
    }

	@Override
	public Parent getParent() {
		return table;
	}

    public synchronized void addEntry(T _entry) {
//    	buffer.add(_entry);
    	data.add(_entry);
    	
	    TableColumn<T, String> column =  (TableColumn<T, String>) table.getColumns().get(0);
	    
	    List<String> columnData = new ArrayList<>();
	    for (T item : table.getItems()) {
	        columnData.add(column.getCellObservableValue(item).getValue());
	    }
	    System.out.println(columnData);
    }
    
    

	void updateMethod() {
    	List<T> tempList = new ArrayList<T>();

		if(!buffer.isEmpty()) {	
			List<T> someList = buffer;

			if(!someList.isEmpty())
				for (T object : someList)
					tempList.add(object);

			table.setItems( data = FXCollections.observableArrayList(tempList) );
			buffer.clear();
		}

		System.out.println(buffer.size());
		System.out.println("datasz= " + data.size());

		try {
			Thread.sleep(1000);
			Platform.runLater(() -> updateMethod() );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	Task<Void> updateTask = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
        	updateMethod();
            return null;
        }
    };

}
