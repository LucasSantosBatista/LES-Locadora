package br.com.locadora.filme;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

}
