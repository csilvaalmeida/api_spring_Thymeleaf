package br.com.clodoaldo.apirest.controllers;



import br.com.clodoaldo.apirest.dto.RequisicaoFormPhone;
import br.com.clodoaldo.apirest.models.Phone;
import br.com.clodoaldo.apirest.models.TipoPhone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/phones")
public class PhoneController {

    @Autowired
    private br.com.clodoaldo.apirest.repositories.PhoneRepository PhoneRepository;
    @Autowired
    private br.com.clodoaldo.apirest.repositories.PersonRepository PersonRepository;

    @GetMapping("")
    public ModelAndView index() {
        System.out.println("lista Telefones ");
        List<Phone> Phone = this.PhoneRepository.findAll();
        ModelAndView mv = new ModelAndView("phones/index");
        mv.addObject("phone", Phone);

        return mv;
    }


    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, RequisicaoFormPhone requisicao) {
        Optional<Phone> optional = this.PhoneRepository.findById(id);

        if (optional.isPresent()) {
            Phone Phone = optional.get();
            requisicao.fromPhone(Phone);

            ModelAndView mv = new ModelAndView("phones/edit");
            mv.addObject("PhoneId", Phone.getId());
            mv.addObject("listaTipoTelefone", TipoPhone.values());
            return mv;

        }
        // não achou um registro na tabela Person com o id informado
        else {
            System.out.println("$$$$$$$$$$$ NÃO ACHOU O Phone DE ID " + id + " $$$$$$$$$$$");
            return this.retornaErroPhone("EDIT ERROR: Phone #" + id + " não encontrado no banco!");
        }
    }


    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid RequisicaoFormPhone requisicao, BindingResult bindingResult) {
        System.out.println("Atualizar");

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("phones/edit");
            mv.addObject("PhoneId", id);
            mv.addObject("listaTipoTelefone", TipoPhone.values());
            return mv;
        } else {
            Optional<Phone> optional = this.PhoneRepository.findById(id);
            if (optional.isPresent()) {
                Phone Phone = requisicao.toPhone(optional.get());
                this.PhoneRepository.save(Phone);
                return new ModelAndView("redirect:/phones/" + Phone.getId());
            }
            // não achou um registro na tabela Person com o id informado
            else {
                System.out.println("############ NÃO ACHOU O Telefone DE ID " + id + " ############");
                return this.retornaErroPhone("UPDATE ERROR: Telefone #" + id + " não encontrado no banco!");
            }
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("redirect:/phones");

        try {
            this.PhoneRepository.deleteById(id);
            mv.addObject("mensagem", "person #" + id + " deletado com sucesso!");
            mv.addObject("erro", false);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            mv = this.retornaErroPhone("DELETE ERROR: Person #" + id + " não encontrado no banco!");
        }

        return mv;
    }

    private ModelAndView retornaErroPhone(String msg) {
        ModelAndView mv = new ModelAndView("redirect:/p" +
                "hones");
        mv.addObject("mensagem", msg);
        mv.addObject("erro", true);
        return mv;
    }


    @PostMapping("/{id}/create")
    public ModelAndView createPhone(@PathVariable Long id, @Valid RequisicaoFormPhone requisicao,
                                    BindingResult bindingResult) {
        //    System.out.println(" Requisicao id = "  + id);
        System.out.println(" Telefones = " + requisicao);
        if (bindingResult.hasErrors()) {
            System.out.println("\n************* TEM ERROS  Telefones***************\n");

            ModelAndView mv = new ModelAndView("phones/new");
            mv.addObject("listaTipoTelefone", TipoPhone.values());
            return mv;
        } else {
            Phone Phone = requisicao.toPhone();
            this.PhoneRepository.save(Phone);

            return new ModelAndView("redirect:/phones/" + Phone.getId());
        }
    }

}