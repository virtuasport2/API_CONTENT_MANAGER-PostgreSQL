package com.github.virtuasport2.memoriawebapp.service;

import com.github.virtuasport2.memoriawebapp.dto.ArticoloRequest;
import com.github.virtuasport2.memoriawebapp.model.Articolo;
import com.github.virtuasport2.memoriawebapp.model.Documento;
import com.github.virtuasport2.memoriawebapp.model.Utente;
import com.github.virtuasport2.memoriawebapp.repository.ArticoloRepository;
import com.github.virtuasport2.memoriawebapp.repository.UtenteRepository;
import com.github.virtuasport2.memoriawebapp.repository.DocumentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

@Service
public class ArticoloService {

    @Autowired
    private ArticoloRepository articoloRepository;

    @Autowired
    private UtenteRepository utenteRepo;

    @Autowired
    private DocumentoRepository documentoRepo;

    private final DataSource dataSource;

    public ArticoloService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Articolo> findAll() {
        return articoloRepository.findAll();
    }

    public Optional<Articolo> findById(Long id) {
        return articoloRepository.findById(id);
    }

    public Articolo save(Articolo articolo) {
        return articoloRepository.save(articolo);
    }

    public Articolo save(ArticoloRequest request) {

        try {
            System.out.println(
                    dataSource.getConnection().getMetaData().getURL());



        } catch (Exception e) {
            e.printStackTrace();
        }

        Utente u = utenteRepo.findById(request.getAutoreId())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Documento d = null;
        if (request.getDocumentoId() != null) {
            d = documentoRepo.findById(request.getDocumentoId())
                    .orElseThrow(() -> new RuntimeException("Documento non trovato"));
        }

        Articolo a = new Articolo();
        a.setTitolo(request.getTitolo());
        a.setSottotitolo(request.getSottotitolo());
        a.setTesto(request.getTesto());
        a.setCategoria(request.getCategoria());
        a.setAutore(u);
        a.setDocumento(d);

        return articoloRepository.save(a);
    }

    public void deleteById(Long id) {
        articoloRepository.deleteById(id);
    }
}
