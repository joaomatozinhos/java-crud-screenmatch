package br.com.alura.screenmatch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.screenmatch.domain.filme.DadosAlteracaoFilme;
import br.com.alura.screenmatch.domain.filme.DadosCadastroFilme;
import br.com.alura.screenmatch.domain.filme.Filme;
import br.com.alura.screenmatch.domain.filme.FilmeRepository;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

	@Autowired
	private FilmeRepository repository;

	@GetMapping("/cadastro")
	public String carregaPaginaFormulario(Long id, Model model) {
		if (id != null) {
			Filme filme = repository.getReferenceById(id);
			model.addAttribute("filme", filme);
		}

		return "filmes/formulario";
	}

	@GetMapping("/listagem")
	public String carregaPaginaListagem(Model model) {
		model.addAttribute("lista", repository.findAll());

		return "filmes/listagem";
	}

	@PostMapping("/cadastro")
	@Transactional
	public String cadastraFilme(DadosCadastroFilme dados) {
		Filme filme = new Filme(dados);
		repository.save(filme);

		return "redirect:/filmes/listagem";
	}

	@PutMapping("/cadastro")
	@Transactional
	public String editaFilme(DadosAlteracaoFilme dados) {
		Filme filme = repository.getReferenceById(dados.id());
		filme.atualizaDados(dados);

		return "redirect:/filmes/listagem";
	}

	@DeleteMapping("/listagem")
	@Transactional
	public String removeFilme(Long id) {
		repository.deleteById(id);
		return "redirect:/filmes/listagem";
	}
}
