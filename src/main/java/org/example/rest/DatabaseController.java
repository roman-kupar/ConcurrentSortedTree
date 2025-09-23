package org.example.rest;

import org.example.cst.ConcurrentSortedTree;
import org.example.cst.IConcurrentSortedTree;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/db")
public class DatabaseController {

    private final IConcurrentSortedTree tree = new ConcurrentSortedTree();

    @PostMapping("/put")
    public String put(@RequestParam String key, @RequestParam String value) {
        var prev = tree.put(
                key.getBytes(StandardCharsets.UTF_8),
                value.getBytes(StandardCharsets.UTF_8)
        );
        return prev.map(b -> new String(b, StandardCharsets.UTF_8)).orElse("null");
    }

    @GetMapping("/get/{key}")
    public ResponseEntity<String> get(@PathVariable String key) {
        return tree.get(key.getBytes(StandardCharsets.UTF_8))
                .map(b -> ResponseEntity.ok(new String(b, StandardCharsets.UTF_8)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
