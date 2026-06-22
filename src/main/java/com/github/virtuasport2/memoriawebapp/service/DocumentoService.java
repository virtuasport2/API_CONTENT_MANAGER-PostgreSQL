package com.github.virtuasport2.memoriawebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.virtuasport2.memoriawebapp.dto.DocumentoRequest;
import com.github.virtuasport2.memoriawebapp.enums.Stato;
import com.github.virtuasport2.memoriawebapp.model.Documento;
import com.github.virtuasport2.memoriawebapp.model.TipoDocumento;
import com.github.virtuasport2.memoriawebapp.model.Utente;
import com.github.virtuasport2.memoriawebapp.repository.ArticoloRepository;
import com.github.virtuasport2.memoriawebapp.repository.DocumentoRepository;
import com.github.virtuasport2.memoriawebapp.repository.TipoDocumentoRepository;
import com.github.virtuasport2.memoriawebapp.repository.UtenteRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private UtenteRepository utenteRepo;

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepo;

    private final DocumentoRepository documentoRepository;

    public DocumentoService(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    public List<Documento> getAllDocumenti() {
        return documentoRepository.findAll();
    }

    public List<Documento> findAll() {
        return documentoRepository.findAll();
    }

    public Optional<Documento> getDocumentoById(Long id) {
        return documentoRepository.findById(id);

    }

    public Documento saveDocumento(DocumentoRequest request) {

        if (request.getTipoDocumentoId() == null) {
            throw new RuntimeException("tipoDocumentoId mancante");
        }

        TipoDocumento td = tipoDocumentoRepo.findById(request.getTipoDocumentoId())
                .orElseThrow(() -> new RuntimeException("Tipo documento non trovato"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Utente u = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Documento d = new Documento();
        d.setNome(request.getNome());
        d.setTipo(request.getTipo());
        d.setDescrizione(request.getDescrizione());
        d.setStrutturaJson(request.getStrutturaJson());

        d.setCreatoDa(u);
        d.setTipoDocumento(td);
        d.setStato(Stato.bozza);

        return documentoRepository.save(d);
    }

    public void deleteDocumento(Long id) {
        documentoRepository.deleteById(id);

    }

    public Documento updateDocumento(Long id, DocumentoRequest request) {

        Documento existing = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento non trovato"));

        existing.setNome(request.getNome());
        existing.setTipo(request.getTipo());
        existing.setDescrizione(request.getDescrizione());
        existing.setStrutturaJson(request.getStrutturaJson());

        if (request.getTipoDocumentoId() != null) {
            TipoDocumento td = tipoDocumentoRepo.findById(request.getTipoDocumentoId())
                    .orElseThrow(() -> new RuntimeException("Tipo documento non trovato"));
            existing.setTipoDocumento(td);
        }

        return documentoRepository.save(existing);
    }
}
