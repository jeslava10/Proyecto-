/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.Admin;

import DAO.TbprofesorJpaController;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import modelo.Tbprofesor;

/**
 * FXML Controller class
 *
 * @author Cecilia Segura
 */
public class GestionDocenteController implements Initializable {

   @FXML private Button crearbt;
   @FXML private Button modificarbt;
   @FXML private Button consultarbt;
   @FXML private Button limpiar;
     
    // Declaramos los textfileds
    @FXML private TextField nombretf;
    @FXML private TextField apellidotf;
    @FXML private TextField codigotf;
    @FXML private TextField telefonotf;
    @FXML private TextField emailtf;
    @FXML private TextField direcciontf;
    
    
    
   // Declaramos la tabla y las columnas
   @FXML
   private TableView tabladocente;
   private TableColumn titulo;
   
   //Otros
   ObservableList<ObservableList> tbprofesor;
   Tbprofesor prefesor  = new Tbprofesor();
   TbprofesorJpaController profesorbd = new TbprofesorJpaController(); 
   
       
      @FXML 
      public void crear (ActionEvent event) throws Exception {
        
        Tbprofesor nuevo= new Tbprofesor();
        nuevo.setCedula(new BigDecimal(codigotf.getText()));
        nuevo.setNombre(nombretf.getText());
        nuevo.setApellido(apellidotf.getText());
        nuevo.setEmail(emailtf.getText());
        nuevo.setDireccion(direcciontf.getText());
        nuevo.setTelefono(Integer.parseInt(telefonotf.getText()));
       
        profesorbd.create(nuevo);
        cargarDatosTabla();
        limpiar();
          

    }
    
   @FXML
    public void modificar (ActionEvent event) throws Exception {
        
        EntityManager em = profesorbd.getEntityManager();
        Tbprofesor mdocente = new Tbprofesor();
        mdocente.setCedula(new BigDecimal(codigotf.getText()));
        TypedQuery<Tbprofesor> query = em.createNamedQuery("Tbprofesor.findByCedula", Tbprofesor.class);
        mdocente = query.setParameter("cedula", mdocente.getCedula()).getSingleResult();
        mdocente.setNombre(nombretf.getText());
        mdocente.setApellido(apellidotf.getText());
        mdocente.setEmail(emailtf.getText());
        mdocente.setDireccion(direcciontf.getText());
        mdocente.setTelefono(Integer.parseInt(telefonotf.getText()));
        
     
        profesorbd.edit(mdocente);
        cargarDatosTabla();
        limpiar();
        
         em.close();
       
    }
    
      @FXML
    public void seleccionar(MouseEvent event) {
        tabladocente.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                if (tabladocente != null) {
                    
                    String[] seleccionado = tabladocente.getSelectionModel().getSelectedItems().get(0).toString().split(",");
                    System.out.println(seleccionado[0].substring(1));
                    int id = Integer.parseInt(seleccionado[0].substring(1));
                    System.out.print(id);
                    EntityManager em = profesorbd.getEntityManager();
                    TypedQuery<Tbprofesor> query = em.createNamedQuery("Tbprofesor.findByCedula", Tbprofesor.class);
                    Tbprofesor profesor = query.setParameter("cedula", Integer.valueOf(id)).getSingleResult();
                    codigotf.setText(profesor.getCedula().toString());
                    nombretf.setText(profesor.getNombre());
                    apellidotf.setText(profesor.getApellido());
                      String email = profesor.getEmail() == null ? profesor.getEmail()+"": profesor.getEmail();
String direccion = profesor.getDireccion() == null ? profesor.getDireccion()+"": profesor.getDireccion();
 String telefono = profesor.getTelefono() == null ? profesor.getTelefono()+"": profesor.getTelefono().toString();
                    telefonotf.setText(telefono);
                    emailtf.setText(email);
                    direcciontf.setText(direccion);
                     
                   
                    
                }

               
            }

        });
         
    }
    
     
  
    private void cargarDatosTabla() {
        tabladocente.getColumns().clear();
        EntityManager em = profesorbd.getEntityManager();
        TypedQuery<Tbprofesor> query = em.createNamedQuery("Tbprofesor.findAll", Tbprofesor.class);
        List<Tbprofesor> results = query.getResultList();

        String[] titulos = {
            "Cedula",
            "Nombre",
            "Apellido",
            "Correo",
            "Direccion",
            "Telefono",
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
            tabladocente.getColumns().addAll(titulo);
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
            String email = profe.getEmail() == null ? profe.getEmail()+"": profe.getEmail();
            row.add(email);
            String direccion = profe.getDireccion() == null ? profe.getDireccion()+"": profe.getDireccion();
            row.add(direccion);
             String telefono = profe.getTelefono() == null ? profe.getTelefono()+"": profe.getTelefono().toString();
            row.add(telefono);
           tbprofesor.addAll(row);
        }
        tabladocente.setItems(tbprofesor);
       
        em.close();

    }



    public void limpiar() {
    nombretf.setText("");
    apellidotf.setText("");
    codigotf.setText("");
    telefonotf.setText("");
    emailtf.setText("");
    direcciontf.setText("");
    }
   
      @FXML
    public void limpiar (ActionEvent event) throws Exception {
   nombretf.setText("");
    apellidotf.setText("");
    codigotf.setText("");
    telefonotf.setText("");
    emailtf.setText("");
    direcciontf.setText("");
//       @FXML private Button limpiar;
       
    }
    
    @FXML
   public void consultar (ActionEvent event) throws Exception {
       
        Tbprofesor pre = new Tbprofesor();
                   pre.setCedula(new BigDecimal(codigotf.getText()));

                  
                    EntityManager em = profesorbd.getEntityManager();
                    TypedQuery<Tbprofesor> query = em.createNamedQuery("Tbprofesor.findByCedula", Tbprofesor.class);
                    Tbprofesor profesor= query.setParameter("cedula",pre.getCedula()).getSingleResult();
                    codigotf.setText(profesor.getCedula().toString());
                    nombretf.setText(profesor.getNombre());
                    apellidotf.setText(profesor.getApellido());
                     String email = profesor.getEmail() == null ? profesor.getEmail()+"": profesor.getEmail();
String direccion = profesor.getDireccion() == null ? profesor.getDireccion()+"": profesor.getDireccion();
 String telefono = profesor.getTelefono() == null ? profesor.getTelefono()+"": profesor.getTelefono().toString();
                    telefonotf.setText(telefono );
                    emailtf.setText(email );
                    direcciontf.setText(direccion);
       
        
        em.close();
     
   }
   
   
    
   
   
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
      cargarDatosTabla();
    }    
    
}
