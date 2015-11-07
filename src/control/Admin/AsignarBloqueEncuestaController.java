/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.Admin;

import DAO.TbbloqueJpaController;
import DAO.TbbloquesxencuestaJpaController;
import DAO.TbencuestaJpaController;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import modelo.Tbbloque;
import modelo.Tbbloquesxencuesta;
import modelo.Tbencuesta;

/**
 * FXML Controller class
 *
 * @author Cecilia Segura
 */
public class AsignarBloqueEncuestaController implements Initializable {

 // Declaramos los botones
    @FXML private Button asignarbt;
    
    
       
   // Declaramos la tabla y las columnas
   @FXML
   private TableView tablaencuesta;
   private TableColumn titulo;
   
 //Declaramos combobox
   
    private ComboBox<String> cencuesta;
    private ComboBox<String> cbloque;
 
       
    
    // datos encuesta
   ObservableList<ObservableList> tbencuesta;
    @FXML
    ComboBox<?> encuesta;
   TbencuestaJpaController encuestabd = new TbencuestaJpaController(); 
    
    
   
    
   //datos bloque
    ObservableList<ObservableList> tbbloque;
    @FXML
    ComboBox<?> bloque;
   TbbloqueJpaController bloquebd = new TbbloqueJpaController(); 
   
   //dato encuesta x bloques
   Tbbloquesxencuesta bxq = new Tbbloquesxencuesta();
   TbbloquesxencuestaJpaController bxebd = new TbbloquesxencuestaJpaController();
   ObservableList<ObservableList> tbbloquexencuesta;
 
    
   
   // fecha 
   
     Date fecha = new Date();
   DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT);
   
   // lista
    List< Tbbloquesxencuesta> results;
    List<Tbencuesta> resultencuesta;
    List< Tbbloque> resultbloque;
    @FXML
    private Button encuestatabt;
    @FXML
    private Button bloquebt;
   
   
    public void generarb (ActionEvent event) throws Exception {
    tablaencuesta.getColumns().clear();
        EntityManager em = bloquebd.getEntityManager();
        TypedQuery<Tbbloque> query = em.createNamedQuery("Tbbloque.findAll", Tbbloque.class);
        List<Tbbloque> results = query.getResultList();

        String[] titulos = {
            "Numero",
            "bloque",
            "Fecha De Creacion",
            "Fecha De Modificacion",
        };

        for (int i = 0; i < titulos.length; i++) {
            final int j = i;
            this.titulo = new TableColumn(titulos[i]);
            this.titulo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {
                    return new SimpleStringProperty((String) parametro.getValue().get(j));
                }
            });
            tablaencuesta.getColumns().addAll(titulo);
            // Asignamos un tamaño a ls columnnas
            titulo.setMinWidth(120);

            // Centrar los datos de la tabla
            titulo.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                @Override
                public TableCell<String, String> call(TableColumn<String, String> p) {
                    TableCell cell = new TableCell() {
                        @Override
                        protected void updateItem(Object t, boolean bln) {
                            if (t != null) {
                                super.updateItem(t, bln);
                                setText(t.toString());
                                setAlignment(Pos.CENTER); //Setting the Alignment
                            }
                        }
                    };
                    return cell;
                }
            });
        }

        tbbloque = FXCollections.observableArrayList();
        for (Tbbloque bloq : results) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(bloq.getId().toString());
            row.add(bloq.getNombre());
            row.add(df1.format(bloq.getFechacreacion()));
            System.out.print(bloq.getFechacreacion());
            String fecha = bloq.getFechamodificacion() == null ? bloq.getFechamodificacion()+"":df1.format(bloq.getFechamodificacion());
            row.add(fecha);
           tbbloque.addAll(row);
        }
        tablaencuesta.setItems(tbbloque);
        em.close();
    }

    
    public void generare (ActionEvent event) throws Exception {
    tablaencuesta.getColumns().clear();
        EntityManager em = encuestabd.getEntityManager();
        TypedQuery<Tbencuesta> query = em.createNamedQuery("Tbencuesta.findAll", Tbencuesta.class);
        List<Tbencuesta> results = query.getResultList();

        String[] titulos = {
            "Numero",
            "Nombre",
            "Fecha De Creacion",
            "Fecha De Modificacion",
        };

        for (int i = 0; i < titulos.length; i++) {
            final int j = i;
            this.titulo = new TableColumn(titulos[i]);
            this.titulo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {
                    return new SimpleStringProperty((String) parametro.getValue().get(j));
                }
            });
            tablaencuesta.getColumns().addAll(titulo);
            // Asignamos un tamaño a ls columnnas
            titulo.setMinWidth(120);

            // Centrar los datos de la tabla
            titulo.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                @Override
                public TableCell<String, String> call(TableColumn<String, String> p) {
                    TableCell cell = new TableCell() {
                        @Override
                        protected void updateItem(Object t, boolean bln) {
                            if (t != null) {
                                super.updateItem(t, bln);
                                setText(t.toString());
                                setAlignment(Pos.CENTER); //Setting the Alignment
                            }
                        }
                    };
                    return cell;
                }
            });
        }

        tbencuesta = FXCollections.observableArrayList();
        for (Tbencuesta encuesta1 : results) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(encuesta1.getId().toString());
            row.add(encuesta1.getNombre());
            row.add(df1.format(encuesta1.getFechacreacion()));
           
            String fecha = encuesta1.getFechamodificacion() == null ? encuesta1.getFechamodificacion()+"":df1.format(encuesta1.getFechamodificacion());
            row.add(fecha);
           tbencuesta.addAll(row);
        }
        tablaencuesta.setItems(tbencuesta);
        em.close();
    }
   
    @FXML
   public void asignar (ActionEvent event) throws Exception {
       
        if(cencuesta.getValue() != null  && cencuesta.getValue() != null ){
           
            Tbencuesta encu = new Tbencuesta();
            Tbbloque blo  = new Tbbloque();
            Tbbloquesxencuesta bxe = new  Tbbloquesxencuesta();
       
            for(int i=0;i<resultbloque.size();i++){
                if(resultbloque.get(i).getId().toString().equals(cbloque.getValue())){
                    blo = resultbloque.get(i);
                }
            }
        
            for(int i=0;i<resultencuesta.size();i++){
                if(resultencuesta.get(i).getId().toString().equals(cencuesta.getValue())){
                    encu = resultencuesta.get(i);
                }
            }
        
            Boolean existencia = false;
            for(int i=0;i<results.size();i++){
                if(results.get(i).getIdbloque().getId().toString().equals(blo.getId().toString()) && results.get(i).getIdencuesta().getId().toString().equals(encu.getId())){
                    existencia = true;
                }
            }
        
            if(existencia == false){
                bxe.setIdbloque(blo);
                bxe.setIdencuesta(encu);
                
                bxebd.create(bxe);
            }
//            cargarDatosTabla();
        }
        
        limpiar();
    
   }
   
   
   
 
    
      public void cargarcomboencuesta() {
              


        EntityManager em = encuestabd.getEntityManager();
        TypedQuery<Tbencuesta> query = em.createNamedQuery("Tbencuesta.findAll", Tbencuesta.class);

        resultencuesta = query.getResultList();

        em.close();
        for(int i=0;i<resultencuesta.size();i++){
           cencuesta.getItems().add(resultencuesta.get(i).getId().toString());
        }
    }
    
   
    
    public void cargarcombobloque (){
        
        EntityManager em = bloquebd.getEntityManager();
        TypedQuery<Tbbloque> query = em.createNamedQuery("Tbbloque.findAll", Tbbloque.class);

        resultbloque = query.getResultList();

        em.close();
        for(int i=0;i<resultbloque.size();i++){
           cbloque.getItems().add(resultbloque.get(i).getId().toString());
        }
    }
    
    
    public void limpiar() {
       cbloque.setValue(null);
       cencuesta.setValue(null);
       
       
    }
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cargarcombobloque ();
        cargarcomboencuesta ();
        
    }    

    @FXML
    private void seleccionarencuesta(MouseEvent event) {
    }

    @FXML
    private void genarare(ActionEvent event) {
    }

    @FXML
    private void genararb(ActionEvent event) {
    }
    
}
