package com.example.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {
    private List<String> productosList = new ArrayList<>();

    @PostMapping("/productos")
    public ResponseEntity<String> respuestaOk(@RequestBody List<String> productos) {
        System.out.println(productos);
        PrinterDemo imprimir = new PrinterDemo(productosList);
        imprimir.print();
        return ResponseEntity.ok("{ \"message\": \"OK\" }");
    }

    @GetMapping("/productos")
    public List<String> getProductos() {
        return productosList;
    }
}
