/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.Admin;

import DAO.TbasignaturaJpaController;
import DAO.TbgrupoJpaController;
import DAO.TbgrupoxasignaturaxprofesorJpaController;
import DAO.TbprofesorJpaController;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import modelo.Tbasignatura;
import modelo.Tbgrupo;
import modelo.Tbgrupoxasignaturaxprofesor;
import modelo.Tbprofesor;

/**
 * FXML Controller class
 *
 * @author Cecilia Segura
 */
public class AsignarAsignaturaDocenteController implements Initializable {

      
    @FXML private AnchorPane aasignar;
    @FXML private Button generard;
    @FXML private Button generara;
    @FXML private Button asignar;
    @FXML private TextField cupo;
    @FXML private ComboBox<Integer> grupo;
    @FXML private ComboBox<String> asignatura;
    @FXML private ComboBox<String> profesor;
    
    List<Tbgrupo> resultsgrupo;
    List<Tbasignatura> resultsasignatura;
    List<Tbprofesor> resultsprofesor;
    List<Tbgrupoxasignaturaxprofesor> results;
  
     // Declaramos la tabla y las columnas
   @FXML
   private TableView tablaasignatura;
   private TableColumn titulo;
  

   // Datos docente
   ObservableList<ObservableList> tbprofesor;
   Tbprofesor prefesor  = new Tbprofesor();
   TbprofesorJpaController profesorbd = new TbprofesorJpaController(); 
   
    //Datoa Asignatura
   
   ObservableList<ObservableList> tbasignatura;
    
   TbgrupoxasignaturaxprofesorJpaController gpabd = new TbgrupoxasignaturaxprofesorJpaController(); 
   Tbgrupoxasignaturaxprofesor gpa = new   Tbgrupoxasignaturaxprofesor();
   TbasignaturaJpaController materiabd = new TbasignaturaJpaController(); 
   Date fecha = new Date();
   DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cargarDatosTabla();
        cargarcombogrupo();
        cargarcomboasignatura();
        cargarcomboprofesor();
      
    }    

    public void cargarDatosTabla() {
        tablaasignatura.getColumns().clear();
        EntityManager em = gpabd.getEntityManager();
        TypedQuery<Tbgrupoxasignaturaxprofesor> query = em.createNamedQuery("Tbgrupoxasignaturaxprofesor.findAll", Tbgrupoxasignaturaxprofesor.class);
        results = query.getResultList();

        String[] titulos = {
            "Codigo",
            "Nombre",
            "Cedula",
            "Profesor",
            "Grupo",
            "Cupo",
            
            
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
            tablaasignatura.getColumns().addAll(titulo);
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

        tbasignatura = FXCollections.observableArrayList();
        for (Tbgrupoxasignaturaxprofesor gap : results) {
            ObservableList<String> row = FXCollections.observableArrayList();
            String codigo = gap.getIdasignatura().getCodigo();
            row.add(codigo);
            String nombre = gap.getIdasignatura().getNombre();
            row.add(nombre);
            String nombrep = gap.getCedula().getNombre();
            String apellidop = gap.getCedula().getApellido();
            row.add(gap.getCedula().getCedula().toString());
            row.add(nombrep + apellidop);
            row.add(gap.getIdgrupo().getIdentificacion().toString());
            row.add(gap.getCupo() + "");
          
            
           tbasignatura.addAll(row);
        }
        tablaasignatura.setItems(tbasignatura);
        em.close();
    }
      
    @FXML
    public void generard (ActionEvent event) throws Exception {
      tablaasignatura.getColumns().clear();
        EntityManager em = profesorbd.getEntityManager();
        TypedQuery<Tbprofesor> query = em.createNamedQuery("Tbprofesor.findAll", Tbprofesor.class);
        List<Tbprofesor> results = query.getResultList();

        String[] titulos = {
            "Cedula",
            "Nombre",
            "Apellido",
           
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
            tablaasignatura.getColumns().addAll(titulo);
//             Asignamos un tamaño a ls columnnas
            titulo.setMinWidth(160);

//             Centrar los datos de la tabla
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

        tbprofesor = FXCollections.observableArrayList();
        for (Tbprofesor profe : results) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(profe.getCedula().toString());
            row.add(profe.getNombre());
            row.add(profe.getApellido());
            
           tbprofesor.addAll(row);
        }
        tablaasignatura.setItems(tbprofesor);
       
        em.close();

    }
   
    @FXML
   public void generara (ActionEvent event) throws Exception {
       tablaasignatura.getColumns().clear();
        EntityManager em = materiabd.getEntityManager();
        TypedQuery<Tbasignatura> query = em.createNamedQuery("Tbasignatura.findAll", Tbasignatura.class);
        List<Tbasignatura> results = query.getResultList();

        String[] titulos = {
            "Codigo",
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
            tablaasignatura.getColumns().addAll(titulo);
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

        tbasignatura = FXCollections.observableArrayList();
        for (Tbasignatura asig : results) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(asig.getCodigo());
            row.add(asig.getNombre());
            row.add(df1.format(asig.getFechacreacion()));
            System.out.print(asig.getFechacreacion());
            String fecha = asig.getFechamodificacion() == null ? asig.getFechamodificacion()+"":df1.format(asig.getFechamodificacion());
            row.add(fecha);
           tbasignatura.addAll(row);
        }
        tablaasignatura.setItems(tbasignatura);
        em.close();
    }
   
   


 @FXML
   public void asignar (ActionEvent event) throws Exception {
       
        if(profesor.getValue() != null && !cupo.getText().isEmpty() && asignatura.getValue() != null && grupo.getValue() != null){
            Tbgrupoxasignaturaxprofesor gpa = new   Tbgrupoxasignaturaxprofesor();
            Tbasignatura  asi = new Tbasignatura();
            Tbprofesor  pro  = new Tbprofesor();
            Tbgrupo grup = new Tbgrupo();
       
            for(int i=0;i<resultsgrupo.size();i++){
                if(resultsgrupo.get(i).getIdentificacion() == grupo.getValue()){
                    grup = resultsgrupo.get(i);
                }
            }
        
            for(int i=0;i<resultsprofesor.size();i++){
                if(resultsprofesor.get(i).getCedula().toString().equals(profesor.getValue())){
                    pro = resultsprofesor.get(i);
                }
            }
        
            for(int i=0;i<resultsasignatura.size();i++){
                if(resultsasignatura.get(i).getCodigo().equals(asignatura.getValue())){
                    asi = resultsasignatura.get(i);
                }
            }
        
            Boolean existencia = false;
            for(int i=0;i<results.size();i++){
                if(results.get(i).getCedula().getCedula().toString().equals(pro.getCedula().toString()) && results.get(i).getIdgrupo().getIdentificacion() == grup.getIdentificacion() && results.get(i).getIdasignatura().getCodigo().equals(asi.getCodigo())){
                    existencia = true;
                }
            }
        
            if(existencia == false){
                gpa.setCedula(pro);
                gpa.setIdasignatura(asi);
                gpa.setIdgrupo(grup);
                gpa.setCupo(Integer.parseInt(cupo.getText()));
                gpabd.create(gpa);
            }
            cargarDatosTabla();
        }
        
        limpiar();
    
   }
   
   
    public void cargarcombogrupo() {
          
        TbgrupoJpaController grupobd = new TbgrupoJpaController();


        EntityManager em = grupobd.getEntityManager();
        TypedQuery<Tbgrupo> query = em.createNamedQuery("Tbgrupo.findAll", Tbgrupo.class);

        resultsgrupo = query.getResultList();

        em.close();
        for(int i=0;i<resultsgrupo.size();i++){
           grupo.getItems().add(resultsgrupo.get(i).getIdentificacion());
        }
    }
    
    public void cargarcomboprofesor(){
        TbprofesorJpaController profesorbd = new TbprofesorJpaController();


        EntityManager em = profesorbd.getEntityManager();
        TypedQuery<Tbprofesor> query = em.createNamedQuery("Tbprofesor.findAll", Tbprofesor.class);

        resultsprofesor = query.getResultList();

        em.close();
        for(int i=0;i<resultsprofesor.size();i++){
           profesor.getItems().add(resultsprofesor.get(i).getCedula().toString());
        }
        
    }
    
    public void cargarcomboasignatura(){
        TbasignaturaJpaController asignaturabd = new TbasignaturaJpaController();


        EntityManager em = profesorbd.getEntityManager();
        TypedQuery<Tbasignatura> query = em.createNamedQuery("Tbasignatura.findAll", Tbasignatura.class);

        resultsasignatura = query.getResultList();

        em.close();
        for(int i=0;i<resultsasignatura.size();i++){
           asignatura.getItems().add(resultsasignatura.get(i).getCodigo());
        }
    }
    
    
    public void limpiar() {
       asignatura.setValue(null);
       profesor.setValue(null);
       grupo.setValue(null);
       cupo.setText("");
       
    }

}




