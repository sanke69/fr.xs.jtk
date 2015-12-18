package fr.xs.jtk.graphics.fx.api.database;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;

public class TreeDatabaseFX<T> extends TreeTableView<T> {
    Class<T> clazz;

    TreeItem<T>      root = new TreeItem<T>();

    public TreeDatabaseFX(Class<T> _model, T[] _data, DatabaseColumnFX<?>... _models) {
    	super();

    	clazz = _model;

    	try {
			root = new TreeItem<T>( clazz.newInstance() );
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

        setRoot(root);
        setShowRoot(false);
//		table.setPrefWidth(750);

        if(_models != null) {
	        for(DatabaseColumnFX<?> dbCol : _models) {
	            TreeTableColumn<T, String> column = new TreeTableColumn<>(dbCol.label);
	            column.setPrefWidth(dbCol.width);
//            column.prefWidthProperty().bind(table.widthProperty().divide(4));
				column.setCellValueFactory((CellDataFeatures<T, String> p) -> {
					try {
						return new ReadOnlyStringWrapper((String) dbCol.method.invoke(p.getValue().getValue(), null));
					} catch (Exception e) {
						e.printStackTrace();
						return new ObservableValue<String>() {
							@Override public void removeListener(InvalidationListener listener) {}
							@Override public void addListener(InvalidationListener listener) {}
							@Override public void removeListener(ChangeListener<? super String> listener) {}
							@Override public String getValue() { return null; }
							@Override public void addListener(ChangeListener<? super String> listener) {}
						};
					}
				});

	            getColumns().add(column);
	        }
        }

        if(_data != null)
	        for(T m : _data)
	        	addEntry(m);
    }
    
    public void addColumn(DatabaseColumnFX<?>... _models) {
        for(DatabaseColumnFX<?> dbCol : _models) {
            TreeTableColumn<T, String> column = new TreeTableColumn<>(dbCol.label);
            column.setPrefWidth(dbCol.width);
//            column.prefWidthProperty().bind(table.widthProperty().divide(4));
			column.setCellValueFactory((CellDataFeatures<T, String> p) -> {
				try {
					return new ReadOnlyStringWrapper((String) dbCol.method.invoke(p.getValue().getValue(), null));
				} catch (Exception e) {
					e.printStackTrace();
					return new ObservableValue<String>() {
						@Override public void removeListener(InvalidationListener listener) {}
						@Override public void addListener(InvalidationListener listener) {}
						@Override public void removeListener(ChangeListener<? super String> listener) {}
						@Override public String getValue() { return null; }
						@Override public void addListener(ChangeListener<? super String> listener) {}
					};
				}
			});

            getColumns().add(column);
        }
    }
    
    public void clear() {
    	try {
			root = new TreeItem<T>( clazz.newInstance() );
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

        setRoot( root );
    }

	public TreeTableView<T> getTable() {
		return this;
	}


    public void addEntry(T _entry) {
	    root.getChildren().add( new TreeItem<T>(_entry) );
    }

    public void addItem(TreeItem<T> _item) {
	    root.getChildren().add( _item );
    }

}
