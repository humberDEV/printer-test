package com.example.example;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://pasapalostpv.netlify.app/")
@RestController
@RequestMapping("/api")
public class Controller {
    private Map<String, Integer> productosList = new HashMap<>();

    @PostMapping("/comanda")
    public ResponseEntity<String> respuestaOk(@RequestBody Map<String, Object> orderData) {
        Map<String, Integer> productos = (Map<String, Integer>) orderData.get("productos");
        String table = (String) orderData.get("table");

        System.out.println("Productos: " + productos);
        GenerateOrder imprimir = new GenerateOrder(productos, table);
        imprimir.print();
        return ResponseEntity.ok("{ \"message\": \"OK\" }");
    }

    @GetMapping("/comanda")
    public Map<String, Integer> getProductos() {
        return productosList;
    }
}