package br.com.locadora.ator;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AtorService {
	@Autowired
	private AtorRepository repository;
	
	public List<Ator> getAllAtor() {
		return repository.findAll(Sort.by("nome").ascending());
	}

	public Ator getAtorById (Long id) {
		return repository.getReferenceById(id);
	}
	
	public Ator save(Ator ator) {
		if (ator.getNome() == null || ator.getNome().trim().isEmpty()) {
			return null; // Não salva atores sem nome
		}
		return repository.save(ator);

	}

	public Ator findByNome(String nome) {
		// TODO Auto-generated method stub
		return repository.findByNome(nome);
	}
}
