package fr.xs.jtk.graphics.fx.api.widgets;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

import fr.xs.jtk.graphics.fx.ApplicationFX;
import fr.xs.jtk.graphics.fx.api.abstracts.PanelFX;

public class PlayerFX implements PanelFX {
	private static final String mount_point = "/home/sanke/Musique";
	
	private static final int PLAY            =  0;
	private static final int STOP            =  1;
	private static final int REVERSE         =  2;
	private static final int PREVIOUS        =  3;
	private static final int SLOWER          =  4;
	private static final int FASTER          =  5;
	private static final int NEXT            =  6;
	private static final int STEPBACKWARD    =  7;
	private static final int REFRESH         =  8;
	private static final int STEPFORWARD     =  9;
	private static final int LOOP            = 10;
	private static final int PLAYLIST        = 11;
	private static final int LOOPOREPEATMODE = 12;
	private static final int RECORDOFF       = 13;

	private static final int PAUSE           = 14;
	private static final int VERSE           = 15;
	private static final int RECORDON        = 16;

	private boolean playing   = false;
	private boolean recording = false;
	
	private Duration A, B;

	public static void main(String[] args) {
		ApplicationFX.threadedFX(PlayerFX.class);

		ApplicationFX.waitForKillMainProcess();
	}
	
	public PlayerFX() {
		prepareIcons();
		
		prepareScene();

		slider.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.RIGHT) {
				double newVal = slider.getValue() + 1;
				if (newVal <= slider.getMax())
					slider.setValue(newVal);
				event.consume();
			} else if (event.getCode() == KeyCode.LEFT) {
				double sVal = slider.getValue();
				long newVal = (long) (slider.getValue() - 1);
				while((sVal == newVal))
					newVal--;
				if (newVal >= slider.getMin())
					slider.setValue(newVal);
				event.consume();
			}
		});

		buttons[PLAY].setOnAction((event) -> {
			if(playing = !playing) {
				buttons[PLAY].setGraphic(icons[PAUSE]);
		        mediaView.getMediaPlayer().play();
			} else {
				buttons[PLAY].setGraphic(icons[PLAY]);
		        mediaView.getMediaPlayer().pause();
			}
		});

		buttons[STOP].setOnAction((event) -> {
			if(playing) {
				buttons[PLAY].setGraphic(icons[PLAY]);
		        mediaView.getMediaPlayer().stop();
		        playing = false;
			}
		});

	    buttons[FASTER].setOnAction((event) -> {
			mediaView.getMediaPlayer().setRate(mediaView.getMediaPlayer().getRate() + 0.5);
	    });

	    buttons[SLOWER].setOnAction((event) -> {
			mediaView.getMediaPlayer().setRate(mediaView.getMediaPlayer().getRate() - 0.5);
	    });

		buttons[PLAYLIST].setOnMouseClicked(new EventHandler<MouseEvent>() {
			FileChooser chooser;
			File file;
			ObservableList<Object> array;
		    ListView list = new ListView();

			@Override
			public void handle(MouseEvent t) {
				chooser = new FileChooser();
				ExtensionFilter mp3 = new ExtensionFilter("MP3 Files(*.mp3)", "*.mp3");
				ExtensionFilter aac = new ExtensionFilter("AAC Files(*.aac)", "*.aac");
				chooser.getExtensionFilters().addAll(mp3, aac);
				file = chooser.showOpenDialog(null);
				if(file == null)
					return ;
				String fi = file.getAbsoluteFile().toURI().toString();
				String name = file.getName().toString();
				list.getItems().add(name);
				array = FXCollections.observableArrayList();
				array.addAll(fi);
				System.out.println(array);
			}
		});

	    buttons[PREVIOUS].setOnAction((event) -> {
	        MediaPlayer curPlayer  = mediaView.getMediaPlayer();

	        MediaPlayer nextPlayer = players.get(players.indexOf(curPlayer) - 1 >= 0 ? players.indexOf(curPlayer) - 1 : players.size() - 1);
	        mediaView.setMediaPlayer(nextPlayer);
	        curPlayer.currentTimeProperty().removeListener(progressChangeListener);
	        curPlayer.stop();
	        nextPlayer.play();
		    setCurrentlyPlaying(mediaView.getMediaPlayer());
	    });

	    buttons[NEXT].setOnAction((event) -> {
	        MediaPlayer curPlayer  = mediaView.getMediaPlayer();
	        MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
	        mediaView.setMediaPlayer(nextPlayer);
	        curPlayer.currentTimeProperty().removeListener(progressChangeListener);
	        curPlayer.stop();
	        nextPlayer.play();
		    setCurrentlyPlaying(mediaView.getMediaPlayer());
	    });

	    buttons[RECORDOFF].setOnAction((event) -> {
			if(recording = !recording)
				buttons[RECORDOFF].setGraphic(icons[RECORDON]);
			else
				buttons[RECORDOFF].setGraphic(icons[RECORDOFF]);
	    });

	    buttons[STEPBACKWARD].setOnAction((event) -> {
			A = mediaView.getMediaPlayer().getCurrentTime();
	    });
	    buttons[STEPFORWARD].setOnAction((event) -> {
			B = mediaView.getMediaPlayer().getCurrentTime();
	    });
	    buttons[LOOP].setOnAction((event) -> {
	    	System.out.println(A + " - " + B);
			mediaView.getMediaPlayer().setStartTime(A);
			mediaView.getMediaPlayer().setStopTime(B);
	    });

		preparePlaylist(mount_point);
		prepareMediaView();

		System.out.println(mediaView.getMediaPlayer().getCurrentTime());

	    // start playing the first track.
	    mediaView.setMediaPlayer(players.get(0));
//	    mediaView.getMediaPlayer().play();
//	    setCurrentlyPlaying(mediaView.getMediaPlayer());
	}

	public Parent getParent() {
		return parent;
	}

	long nbIteration = 100000000000000000l;

	Parent 				 parent;
	private Slider       slider;
	private ButtonBase[] buttons;
	private ProgressBar  progress;
	private Label        currentlyPlaying;
	private ChangeListener<Duration> progressChangeListener;

	private ImageView 	 icons[]; 

	private List<MediaPlayer> players;
    private MediaView mediaView;

	public void prepareIcons() {
		String[] iconsName = new String[] { "Play", "Stop", "Reverse", 
											 "Previous", "Slower", "Faster", "Next", "Step_backward", 
											 "Refresh", "Step_forward", "Loop_A_B", "PlayList", "Loop_or_Repeat_mode", 
											 "RecordOff", "Pause", "Verse", "RecordOn" };

		icons = new ImageView[iconsName.length];
		for (int i = 0; i < iconsName.length; i++)
			icons[i] = new ImageView(new Image(getClass().getResourceAsStream("/res/playerfx/" + iconsName[i] + ".png")));
	}

	private void prepareSlider() {
		slider = new Slider(0, nbIteration, 5);
		slider.setMajorTickUnit(nbIteration);
		slider.valueProperty().addListener((ov, oldVal, newVal) -> {
			long lNewVal = newVal.longValue();
			if(lNewVal != oldVal.longValue())
				System.out.println(lNewVal);				
		});

		slider.setBlockIncrement(1);
		slider.setSnapToTicks(true);
		if(nbIteration > Integer.MAX_VALUE) {
			slider.setMinorTickCount(Integer.MAX_VALUE);
		} else
			slider.setMinorTickCount((int) nbIteration - 1);
//		slider.setShowTickLabels(true);
//		slider.setShowTickMarks(true);
		
	}
	private void prepareButtons() {
		int[] space      = new int[] { 3, 7, 10, 13 };
		int   indexSpace = 0;

		String[] buttonName = new String[] { "Play", "Stop", "Reverse", 
											 "Previous", "Slower", "Faster", "Next", "Step_backward", 
											 "Refresh", "Step_forward", "Loop_A_B", "PlayList", "Loop_or_Repeat_mode", "RecordOff" };

		buttons = new ButtonBase[buttonName.length];

		for (int i = 0; i < buttonName.length; i++) {
			ButtonBase button = (i == 11 || i == 12) ? new ToggleButton(null, icons[i]) : new Button(null, icons[i]);
			int size = i == 0 ? 34 : 26;
			button.setMaxSize(size, size);
			button.setMinSize(size, size);
			if(indexSpace < space.length && i == space[indexSpace]){
				HBox.setMargin(button, new Insets(0, 0, 0, 10));
				indexSpace++;
			}
			button.setTooltip(new Tooltip(buttonName[i].replace("_or_", "/").replace("_", " ")));
			buttons[i] = button;
			if (i == PLAYLIST)
				((ToggleButton) button).setSelected(true);
			else if (i == LOOPOREPEATMODE)
				((ToggleButton) button).setSelected(true);
		}
	}
	private void prepareProgress() {
		progress = new ProgressBar();
		progress.setPrefWidth(128);
	}
	private void prepareScene() {
		prepareSlider();
		prepareButtons();
		prepareProgress();

		currentlyPlaying = new Label();

		HBox hbox = new HBox(2);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(buttons);
		HBox boxText = new HBox();
		boxText.getChildren().addAll(currentlyPlaying, new Label(" - 00:00:00"));
		boxText.setPrefWidth(300);
		boxText.setMaxWidth(Double.MAX_VALUE);
		HBox statusBar = new HBox();
		statusBar.getChildren().addAll(boxText, progress);
		
//		boxText.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
		boxText.setStyle("-fx-border-width: 1px; -fx-border-color: #969696 #DCDCDC #DCDCDC #969696;");
//		Color c = Color.decode("0xFF0096");
//		boxText.setStyle("-fx-effect: innershadow(three-pass-box, gray, 12 , 0.5, 1, 1);");

		parent = new VBox(10);

		((VBox) parent).getChildren().addAll(slider, hbox, statusBar);
		((VBox) parent).setPadding(new Insets(5, 5, 5, 5));
	}


	public void preparePlaylist(String _path) {
		final File dir = new File(_path);
		if(!dir.exists() || !dir.isDirectory()) {
			System.out.println("Cannot find music source directory: " + dir);
			Platform.exit();
		}

		players = new ArrayList<MediaPlayer>();
		for(String file : dir.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".mp3");
				}
			})) {
			System.out.println("Creating player for: " + file);
			MediaPlayer player = new MediaPlayer(new Media("file:///" + (dir + "\\" + file).replace("\\", "/").replaceAll(" ", "%20")));
			player.setOnError(() -> System.out.println("Media error occurred: " + player.getError()));

			players.add(player);
		}

		if(players.isEmpty()) {
			System.out.println("No audio found in " + dir);
			Platform.exit();
		}

	}

	private void prepareMediaView() {
		mediaView = new MediaView(players.get(0));

		mediaView.mediaPlayerProperty().addListener(
				new ChangeListener<MediaPlayer>() {
					@Override
					public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) {
						setCurrentlyPlaying(newPlayer);
					}
				});

		for (int i = 0; i < players.size(); i++) {
			MediaPlayer player = players.get(i);
			MediaPlayer nextPlayer = players.get((i + 1) % players.size());
			player.setOnEndOfMedia(() -> {
				player.currentTimeProperty().removeListener(progressChangeListener);
				mediaView.setMediaPlayer(nextPlayer);
				nextPlayer.play();
			});
		}

		mediaView.mediaPlayerProperty().addListener(
				new ChangeListener<MediaPlayer>() {
					@Override
					public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) {
						setCurrentlyPlaying(newPlayer);
					}
				});

	}

	private void setCurrentlyPlaying(final MediaPlayer newPlayer) {
		progress.setProgress(0);
		progressChangeListener = new ChangeListener<Duration>() {
			@Override
			public void changed(
					ObservableValue<? extends Duration> observableValue,
					Duration oldValue, Duration newValue) {
				progress.setProgress(1.0
						* newPlayer.getCurrentTime().toMillis()
						/ newPlayer.getTotalDuration().toMillis());
			}
		};
		newPlayer.currentTimeProperty().addListener(progressChangeListener);

		String source = newPlayer.getMedia().getSource();
		source = source.substring(0, source.length() - ".mp4".length());
		source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
		currentlyPlaying.setText("Now Playing: " + source + "(" + mediaView.getMediaPlayer().getTotalDuration() + ")");

		slider.setMax(mediaView.getMediaPlayer().getTotalDuration().toMillis());
		slider.setMajorTickUnit(mediaView.getMediaPlayer().getTotalDuration().toMillis());
		slider.setValue(0);
		
	}

}


