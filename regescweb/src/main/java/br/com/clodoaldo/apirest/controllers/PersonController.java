
package br.com.clodoaldo.apirest.controllers;


import br.com.clodoaldo.apirest.dto.RequisicaoFormPerson;
import br.com.clodoaldo.apirest.dto.RequisicaoFormPhone;
import br.com.clodoaldo.apirest.models.Person;
import br.com.clodoaldo.apirest.models.TipoPhone;

import br.com.clodoaldo.apirest.models.Phone;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "/persons")
public class PersonController {

    @Autowired
    private br.com.clodoaldo.apirest.repositories.PersonRepository PersonRepository;
    @Autowired
    private br.com.clodoaldo.apirest.repositories.PhoneRepository PhoneRepository;

    @GetMapping("")
    public ModelAndView index() {
        System.out.println("lista persons ");
       List<Person> persons = this.PersonRepository.findAll();

        ModelAndView mv = new ModelAndView("persons/index");
        mv.addObject("persons", persons);

        return mv;
    }

     @GetMapping("/new")
     public ModelAndView nnew(RequisicaoFormPerson requisicao) {
        ModelAndView mv = new ModelAndView("persons/new");
        return mv;
    }


    @PostMapping("")
    public ModelAndView create(@Valid RequisicaoFormPerson requisicao, BindingResult bindingResult) {
        System.out.println(requisicao);
        if (bindingResult.hasErrors()) {
            System.out.println("\n************* TEM ERROS ***************\n");
            ModelAndView mv = new ModelAndView("persons/new");
            return mv;
        }
        else {
            Person Person = requisicao.toPerson();
            this.PersonRepository.save(Person);

            return new ModelAndView("redirect:/persons/" );
        }
    }


    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {
        System.out.println("requisicao show " + id );
        Optional<Person> optional = this.PersonRepository.findById(id);

        if (optional.isPresent()) {
            Person Person = optional.get();
            System.out.println("id show " + Person.getId() );
            System.out.println("nome show " + Person.getNome());

            ModelAndView mv = new ModelAndView("persons/show");
           // List <Phone>  Phone = Person.getPhone();
            List<Phone> Phone = PhoneRepository.findAllBytbPersonId(id);
            mv.addObject("Person", Person);
            mv.addObject("Phone", Phone);
            return mv;
        }
        // não achou um registro na tabela Person com o id informado
        else {
            System.out.println("$$$$$$$$$$$ NÃO ACHOU O Person DE ID "+ id + " $$$$$$$$$$$");
            return this.retornaErroPerson("SHOW ERROR: Person #" + id + " não encontrado no banco!");
        }


    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, RequisicaoFormPerson requisicao) {
        Optional<Person> optional = this.PersonRepository.findById(id);

        if (optional.isPresent()) {
            Person Person = optional.get();
            requisicao.fromPerson(Person);

            ModelAndView mv = new ModelAndView("persons/edit");
            mv.addObject("PersonId", Person.getId());
            return mv;
        }
        else {
            System.out.println("$$$$$$$$$$$ NÃO ACHOU O Person DE ID "+ id + " $$$$$$$$$$$");
            return this.retornaErroPerson("EDIT ERROR: Person #" + id + " não encontrado no banco!");
        }
    }


    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid RequisicaoFormPerson requisicao, BindingResult bindingResult) {
        System.out.println("Atualizar");

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("persons/" + id +"edit");
            mv.addObject("PersonId", id);
            return mv;
        }
        else {
            Optional<Person> optional = this.PersonRepository.findById(id);

            if (optional.isPresent()) {
                Person Person = requisicao.toPerson(optional.get());
                this.PersonRepository.save(Person);
           //     + Person.getId()
                return new ModelAndView("redirect:/persons/" );
            }
            else {
                System.out.println("############ NÃO ACHOU O Person DE ID "+ id + " ############");
                return this.retornaErroPerson("UPDATE ERROR: Person #" + id + " não encontrado no banco!");
            }
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("redirect:/persons");

        List<Phone> Phone = PhoneRepository.findAllBytbPersonId(id);
        if (Phone != null) {
            mv = this.retornaErroPerson("DELETE ERROR: Person #" + id +
                    " Existe(m) telefones para esta pessoa!");
            return mv;
        }

        try {
            this.PersonRepository.deleteById(id);
            mv.addObject("mensagem", "person #" + id + " deletado com sucesso!");
            mv.addObject("erro", false);
        }
        catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            mv = this.retornaErroPerson("DELETE ERROR: Person #" + id + " não encontrado no banco!");
        }

        return mv;
    }

//---------------------------------------------------------------------------

    @GetMapping("/phones/{id}/new")
    public ModelAndView newPhone(@PathVariable Long id, RequisicaoFormPerson requisicaoPerson,
                         RequisicaoFormPhone requisicao) {
        System.out.println("  Telefones = ");
        System.out.println(" Requisicao Telefones = " + requisicao);
        System.out.println(" Id = " + id);
        Arrays.stream(TipoPhone.values()).map(c -> " tipo  Telefones = " + c)
                .forEach(System.out::println);

        Optional<Person> optional = this.PersonRepository.findById(id);
        if (optional.isPresent()) {
            Person Person = optional.get();
            ModelAndView mv = new ModelAndView("phones/new");

            mv.addObject("Person", Person);
            mv.addObject("listaTipoTelefone", TipoPhone.values());
            System.out.println(" Id novamente MV DEPOIS ");
            Phone Phone = requisicao.toPhone();
            mv.addObject("Phone", Phone);
            return mv;
        }// não achou um registro na tabela Person com o id informado
        else {
            System.out.println("$$$$$$$$$$$ NÃO ACHOU O Phone DE ID " + id + " $$$$$$$$$$$");
            return this.retornaErroPerson("EDIT ERROR: Phone #" + id + " não encontrado no banco!");
        }

    }

    @PostMapping("/phones/{id}/create")
    public ModelAndView createPhone(@PathVariable Long id, @Valid RequisicaoFormPhone requisicao,
                                    BindingResult bindingResult) {

        System.out.println(" create Requisicao id = "  + id);
        System.out.println(" create Telefones = "  + requisicao);
        if (bindingResult.hasErrors()) {
            System.out.println("\n************* TEM ERROS  Telefones***************\n");

            ModelAndView mv = new ModelAndView("phones/new");
            mv.addObject("listaTipoTelefone", TipoPhone.values());
            Phone Phone = requisicao.toPhone();
            mv.addObject("Phone", Phone);

            Optional<Person> optional = this.PersonRepository.findById(id);

            if (optional.isPresent()) {
                Person Person = optional.get();
                mv.addObject("Person", Person);
            }

            return mv;
        }
        else {
            requisicao.settbPersonId(id);
            System.out.println(" create phone.gettbPersonId = " +
                    requisicao.gettbPersonId());

            Phone Phonesave = requisicao.toPhone();
            System.out.println(" create  2 Telefones = " + requisicao);
            this.PhoneRepository.save(Phonesave);

            System.out.println(" create  3 Telefones = " + requisicao);

            ModelAndView mv = new ModelAndView("persons/show");


            Optional<Person> optional = this.PersonRepository.findById(id);
            if (optional.isPresent()) {
                Person Person = optional.get();
                System.out.println(" Person = " + Person);
                List<Phone> Phone = PhoneRepository.findAllBytbPersonId(id);
                System.out.println("  Phone = " + Phone);


                mv.addObject("Person", Person);
                mv.addObject("Phone", Phone);
            }

            return mv;
        }
    }

    //  ----- Atualiza fone

    @GetMapping("/phones/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, RequisicaoFormPhone requisicao) {
        Optional<Phone> optional = this.PhoneRepository.findById(id);

        System.out.println( "--- Editar phone----" );
        System.out.println("--- O Phone DE ID = " + id + " ----");
        if (optional.isPresent()) {
            Phone Phone = optional.get();
            requisicao.fromPhone(Phone);

             ModelAndView mv = new ModelAndView("phones/edit");

        //    ModelAndView mv = new ModelAndView("phones/new");
            System.out.println("--- Phone id objeto = "  + Phone.getId());
            System.out.println("--- Phone id getNumeroPhone() = "
                    + Phone.getNumeroPhone());

            mv.addObject("numeroPhone", Phone.getNumeroPhone());
            mv.addObject("Phone", Phone);
            mv.addObject("PhoneId", Phone.getId());
            mv.addObject("listaTipoTelefone", TipoPhone.values());
            mv.addObject("PersonId", Phone.gettbPersonId());

            System.out.println("--- PersonId 1 = "  + Phone.gettbPersonId());

            Optional<Person> optionalPerson = this.PersonRepository
                    .findById(Phone.gettbPersonId());
            if (optionalPerson.isPresent()) {
                System.out.println("--- PersonId 2 = "  + Phone.gettbPersonId());
                Person Person = optionalPerson.get();
                mv.addObject("Person", Person);
                System.out.println("--- gravando Person= " );
            }

            System.out.println("--- enviando pagina= " );
            return mv;

        }
        // não achou um registro na tabela Person com o id informado
        else {
            System.out.println("$$$$$$$$$$$ NÃO ACHOU O Phone DE ID " + id + " $$$$$$$$$$$");
            return this.retornaErroPerson("EDIT ERROR: Phone #" + id + " não encontrado no banco!");
        }
    }


    @PostMapping("/phones/{id}")
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
                ModelAndView mv = new ModelAndView("persons/show" + Phone.getId());
                mv.addObject("PhoneId", Phone.getId());
                mv.addObject("PersonId", Phone.gettbPersonId());
                return mv;
            }
            // não achou um registro na tabela Person com o id informado
            else {
                System.out.println("############ NÃO ACHOU O Telefone DE ID " + id + " ############");
                return this.retornaErroPerson("UPDATE ERROR: Telefone #" + id + " não encontrado no banco!");
            }
        }
    }

// ------------delete -------------


    @GetMapping("/phones/{id}/delete")
    public ModelAndView deletePhone(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("persons/show");
        Long idPerson = 0l;
        Optional<Phone> optionalPhone = this.PhoneRepository.findById(id);
        if (optionalPhone.isPresent()) {
            Phone phoneOptional = optionalPhone.get();
            idPerson = phoneOptional.gettbPersonId();
            Optional<Person> optional = this.PersonRepository.findById(idPerson);
            if (optional.isPresent()) {
                Person Person = optional.get();
                mv.addObject("Person", Person);
            }else{
                mv = this.retornaErroPerson("DELETE ERROR 1: Person #" + id + " não encontrado no banco!");

            }
        }else {
            mv = this.retornaErroPerson("DELETE ERROR 2: Person #" + id + " não encontrado no banco!");

        }

// deleta
        try {
            this.PhoneRepository.deleteById(id);
            List<Phone> Phone = PhoneRepository.findAllBytbPersonId(idPerson);
            mv.addObject("Phone", Phone);
            mv.addObject("mensagem", "person #" + id + " deletado com sucesso!");
            mv.addObject("erro", false);


        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            mv = this.retornaErroPerson("DELETE ERROR 3: Person #" + id + " não encontrado no banco!");
        }

        return mv;


        // List <Phone>  Phone = Person.getPhone();

    }

    private ModelAndView retornaErroPerson(String msg) {
        ModelAndView mv = new ModelAndView("redirect:/p" +
                "ersons");
        mv.addObject("mensagem", msg);
        mv.addObject("erro", true);
        return mv;
    }
}
