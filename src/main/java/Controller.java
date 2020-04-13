import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Controller {
    public TextField input;
    public Button button;
    public Label label;

    public void initialize(){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String text = input.getText();
                int i = Integer.parseInt(text);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String fact = getFact(i);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    label.setText(fact);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
    }

    public String getFact(int i) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://numbersapi.com/"+i)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }

    }

}
