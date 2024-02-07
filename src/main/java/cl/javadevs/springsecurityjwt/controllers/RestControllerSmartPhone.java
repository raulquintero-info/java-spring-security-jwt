package cl.javadevs.springsecurityjwt.controllers;

import cl.javadevs.springsecurityjwt.dtos.SmartPhoneDto;
import cl.javadevs.springsecurityjwt.models.SmartPhone;
import cl.javadevs.springsecurityjwt.services.SmartPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/celular/")
@CrossOrigin("*")
public class RestControllerSmartPhone {
    private SmartPhoneService phoneService;

    public RestControllerSmartPhone(SmartPhoneService phoneService) {
        this.phoneService = phoneService;
    }

    //Petición para crear un  celular
    @PostMapping(value = "crear", headers = "Accept=application/json")
    public void crearCelular(@RequestBody SmartPhone smartPhone) {
        phoneService.crear(smartPhone);
    }

    //Petición para obtener todos los celulares en la BD
    @GetMapping(value = "listar", headers = "Accept=application/json")
    public ResponseEntity<?> /*List<SmartPhone>*/ listarCelulares() {
    	
    	List<SmartPhone> phoneList= null;
    	
    	Map<String, Object> response = new HashMap<>();
    	
    	try {
            
    		phoneList = phoneService.readAll();

    	}catch(DataAccessException e) {
    		response.put("message", "Error en la Base de datos");
    		response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));    		
    		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    	}
    	
    	response.put("message", "Listado de telefonos");
		response.put("phoneList", phoneList);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        

    }

    //Petición para obtener celular mediante "ID"
    @GetMapping(value = "listarId/{id}", headers = "Accept=application/json")
    public Optional<SmartPhone> obtenerCelularPorId(@PathVariable Long id) {
        return phoneService.readOne(id);
    }

    //Petición para actualizar un celular
    @PutMapping(value = "actualizar", headers = "Accept=application/json")
    public void actualizarCelular(@RequestBody SmartPhone smartPhone) {
        phoneService.update(smartPhone);
    }

    //Petición para eliminar un celular por "Id"
    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public void eliminarCelular(@PathVariable Long id) {
        phoneService.delete(id);
    }
}