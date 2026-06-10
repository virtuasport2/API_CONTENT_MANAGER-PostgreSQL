package com.github.virtuasport2.memoriawebapp.service;

import org.springframework.stereotype.Service;

import com.github.virtuasport2.memoriawebapp.model.Documento;
import com.github.virtuasport2.memoriawebapp.model.Utente;
import com.github.virtuasport2.memoriawebapp.repository.DocumentoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

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

    public Documento saveDocumento(Documento documento) {
        return documentoRepository.save(documento);
    }

    public void deleteDocumento(Long id) {
        documentoRepository.deleteById(id);

    }



}
