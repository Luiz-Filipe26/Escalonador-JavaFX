package com.mycompany.escalonador.controle;

import java.io.IOException;

import com.mycompany.escalonador.core.DesenhoGrafico;
import com.mycompany.escalonador.core.Escalonador;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/**
 * Classe principal que inicia a aplicação JavaFX Gerenciador Paginas.
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Carrega o arquivo FXML e configura a cena
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/mycompany/escalonador/view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Escalonador de Tarefas");
        
        EscalonadorController escalonadorController = EscalonadorController.getInstancia();
        escalonadorController.setFXMLController(fxmlLoader.getController());
        
        Canvas canvasGraficos = (Canvas) root.lookup("#canvasGrafico");
        GraphicsContext gc = canvasGraficos.getGraphicsContext2D();
        DesenhoGrafico desenhoGrafico = new DesenhoGrafico(gc);
        escalonadorController.setDesenhoGrafico(desenhoGrafico);
        
        
        //Adicionar listener
        stage.setOnCloseRequest(event -> {
        	if(Escalonador.getInstancia() != null && Escalonador.getInstancia().isAlive()) {
                Escalonador.getInstancia().parar();
            }
            Platform.exit();
        });
        
        // Exibe o palco
        stage.show();
    }
}
 