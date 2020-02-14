import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.collections.ObservableList;
import javafx.stage.StageStyle;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.Insets;
import javafx.util.Duration;


public class plan extends Application {
    String eURL = "file:///D:/JavaLab/secretLoveWord.mp3";
    String imVURL = "file:///D:/JavaLab/volume1.png";
    String imBURL = "file:///D:/JavaLab/back1.png";
    String imSURL = "file:///D:/JavaLab/secretImg.png";
    //String imBBURL= "file:///D:/JavaLab/star.jpg";
    String name = "秘语 (电影《秘果》悸动版推广曲)\n欧阳娜娜 / 陈飞宇";
    Slider sVolume = new Slider();
    Button bt0 = new Button(" || ");   //播放暂停键
    Button bt1 = new Button("<<");  //重播
    Button bt2 = new Button("");    //退出
    Label label = new Label(name,new ImageView(imSURL));

    private double xOffSet = 0;
    private double yOffSet = 0;

    @Override
    public void start(Stage stage) {
        /**
         * 核心布局
         */
        Pane pane = new Pane();  //“画布”面板
        Polygon pg = new Polygon(); //心
        Circle circle = new Circle(680, 240, 10); //圆
        Arc el = new Arc(680, 240, 150, 150, 210, 120); //圆的规矩
        Arc el2 = new Arc(680, 240, 150, 150, 210, 120);  //心的轨迹
        Line line = new Line(); //系球的线

        //x^2+y^2+a*x=a*sqrt(x^2+y^2)

        //初始化pg 绘制心
        ObservableList<Double> heartPoint = pg.getPoints();
        double cQ = 500;
        double cM = 260;
        for (double y = 1.3f; y >= -1.1f; y -= 0.06f) {
            int t = 0;
            for (double x = -1.1f; x <= 1.1f; x += 0.025f) {
                if (1.0 >= x * x + (5.0 * y / 4.0 - Math.sqrt(Math.abs(x))) * (5.0 * y / 4.0 - Math.sqrt(Math.abs(x)))) {
                    heartPoint.add(cQ + cM * x);
                    heartPoint.add(cQ + cM * y - 200);
                }
            }
        }
        pg.setRotate(180);
        pg.setFill(null);
        pg.setStroke(Color.PINK);
        //初始化椭圆
        el.setStroke(Color.color(Math.random(), Math.random(), Math.random(), 0));
        el.setFill(null);
        //初始化圆
        circle.setStroke(Color.BLACK);
        circle.setFill(null);
        //初始化线
        line.setStartX(680);
        line.setStartY(240);
        line.setStroke(Color.BLACK);
        line.endXProperty().bind(circle.translateXProperty().add(circle.getCenterX())); //绑定线和圆
        line.endYProperty().bind(circle.translateYProperty().add(circle.getCenterY()));

        //淡入淡出效果
        FadeTransition ft = new FadeTransition(Duration.millis(3000));
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.setNode(pg);  //心
        ft.play();

        FadeTransition ft2 = new FadeTransition(Duration.millis(6000));
        ft2.setFromValue(1.0);
        ft2.setToValue(0.0);
        ft2.setNode(line); //线
        ft2.play();

        FadeTransition ft3 = new FadeTransition(Duration.millis(6000));
        ft3.setFromValue(1.0);
        ft3.setToValue(0.0);
        ft3.setNode(circle); //圆
        ft3.play();

        //动画效果
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(2000));
        pt.setPath(el);
        pt.setNode(circle);  //圆
        pt.setCycleCount(Animation.INDEFINITE);
        pt.setAutoReverse(true);
        pt.play();

        PathTransition pt2 = new PathTransition();
        pt2.setDuration(Duration.millis(2000));
        pt2.setPath(el2);
        pt2.setNode(pg);  //多边形
        pt2.setCycleCount(Animation.INDEFINITE);
        pt2.setAutoReverse(true);
        pt2.play();

        //设置pane
        pane.setCache(true);
        pane.setStyle("-fx-border-color: PINK ");
        // label.setGraphic(new ImageView(new Image(imBBURL)));
        pane.getChildren().addAll(el, circle, line, pg);
        //pane.setStyle("-fx-background:transparent;");

        //播放器
        Media media = new Media(eURL);
        MediaPlayer mPlayer = new MediaPlayer(media);
        mPlayer.setAutoPlay(true);
        sVolume.setValue(30);   //播放器音量条
        mPlayer.volumeProperty().bind(sVolume.valueProperty().divide(100));

        //按钮的样式和事件委托
        bt0.setPrefSize(65, 50);
        bt0.setStyle("-fx-background-color: linear-gradient(to right,#00fffc,#fff600) ;-fx-background-radius: 25 ;-fx-border-radius: 25");
        bt1.setStyle("-fx-background-color: linear-gradient(to right,#00fffc,#fff600) ;-fx-background-radius: 25 ;-fx-border-radius: 25");
        bt0.setOnAction(e -> {
            if (bt0.getText().equals(" |> ")) {
                mPlayer.play();
                pt.play();  pt2.play(); ft.play(); ft2.play(); ft3.play();
                bt0.setText(" | | ");
            } else {
                mPlayer.pause();
                pt.pause(); pt2.pause(); ft.pause(); ft2.pause(); ft3.pause();
                bt0.setText(" |> ");
            }
        });
        bt1.setOnAction(e -> {
            mPlayer.seek(Duration.ZERO);
        });
        bt2.setPrefSize(10, 3);
        bt2.setStyle("-fx-background-color: pink ;-fx-background-radius: 10 ;-fx-border-radius: 10");
        bt2.setOnAction(e->stage.close());

        /**
         * 上端布局
         */
        HBox hB0 = new HBox(30);
        hB0.setMargin(bt2,new Insets(20));
        hB0.setPrefHeight(50);
        hB0.setAlignment(Pos.CENTER_LEFT);
        hB0.getChildren().add(bt2);
        hB0.setStyle("-fx-background-color: linear-gradient(to right,#00fffc,#fff600) ;");

        /**
         * 下端布局
         */
        HBox hB = new HBox(30);
        hB.setPrefHeight(60);
        hB.setAlignment(Pos.CENTER);
        ImageView iV1 = new ImageView(new Image(imVURL));
        ImageView iV2 = new ImageView(new Image(imBURL));
        iV2.setSmooth(true);
        hB.getChildren().addAll(label,bt1, bt0, iV1, sVolume, iV2);
        label.setMaxWidth(175);       //歌曲信息
        hB.setMargin(label,new Insets(1,100,1,20));

        /**
         * 根布局
         */
        BorderPane rootBP = new BorderPane();
        rootBP.setCenter(pane);
        rootBP.setTop(hB0);
        rootBP.setBottom(hB);
        //鼠标拖动
        // Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        rootBP.setOnMousePressed(event -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        rootBP.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffSet);
            stage.setY(event.getScreenY() - yOffSet);
        });
        //场景、舞台布置
        Scene scene = new Scene(rootBP, 1360, 800);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.show();
    }
}