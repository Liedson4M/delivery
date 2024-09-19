package br.edu.ifpr.trabalho.controller;

import br.edu.ifpr.trabalho.model.Produto;
import br.edu.ifpr.trabalho.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/categoria/{categoriaId}")
    public List<Produto> listarProdutosPorCategoria(@PathVariable Long categoriaId) {
        return produtoRepository.findByCategoriaId(categoriaId);
    }
}
