package br.com.ti_knology.config;

import br.com.ti_knology.DTO.UserUpdateDTO;
import br.com.ti_knology.enums.ServicesType;
import br.com.ti_knology.enums.Status;
import br.com.ti_knology.model.*;
import br.com.ti_knology.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class StartBd implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public void run(String... args) {
        // Verifica se já existem categorias no banco de dados
        if (categoryRepository.count() == 0) {
            // Adiciona categorias
            Category category1 = new Category("Desenvolvimento", "Serviço de desenvolvimento de software ou aplicação");
            Category category2 = new Category("Consultoria", "Serviço de auxílio ou apoio em projetos já em andamento");

            categoryRepository.saveAll(Arrays.asList(category1, category2));
            System.out.println("Categorias adicionadas ao banco de dados.");
        }

        if (serviceRepository.count() == 0) {
            // Obtém as categorias
            Category categoryDev = categoryRepository.findByName("Desenvolvimento");
            Category categoryConsultoria = categoryRepository.findByName("Consultoria");

            // Adiciona serviços
            serviceRepository.insertService(ServicesType.DESENVOLVIMENTO_DE_SOFTWARE.getValue(), Status.ANALISE.name(), 5000.00F, 30, null, categoryDev.getId());
            serviceRepository.insertService(ServicesType.CONSULTORIA_EM_TI.getValue(), Status.ANALISE.name(), 3000.00F, 15, null, categoryConsultoria.getId());
            serviceRepository.insertService(ServicesType.SOLUCOES_EM_NUVEM.getValue(), Status.ANALISE.name(), 4000.00F, 20, null, categoryConsultoria.getId());
            serviceRepository.insertService(ServicesType.DESENVOLVIMENTO_PERSONALIZADO.getValue(), Status.ANALISE.name(), 8000.00F, 45, null, categoryDev.getId());
            serviceRepository.insertService(ServicesType.CONSULTORIA_TRANSFORMACAO.getValue(), Status.ANALISE.name(), 7000.00F, 25, null, categoryConsultoria.getId());
            serviceRepository.insertService(ServicesType.INTELIGENCIA_ARTIFICIAL.getValue(), Status.ANALISE.name(), 12000.00F, 60, null, categoryDev.getId());
            serviceRepository.insertService(ServicesType.SOLUCAO_IOT.getValue(), Status.ANALISE.name(), 9000.00F, 50, null, categoryConsultoria.getId());
            serviceRepository.insertService(ServicesType.SOLUCOES_EMBARCADAS.getValue(), Status.ANALISE.name(), 700000.00F, 365, null, categoryConsultoria.getId());

            System.out.println("Serviços adicionados ao banco de dados.");
        }

        if (userRepository.count() <= 1){
            /*
            userData.cpf();
            userData.name();
            userData.email();
            userData.phone();
            userData.birthdate();
            userData.password();
             */
            //Cria os usuários
            User user1 = new User(new UserUpdateDTO("Davi Aleixo", "davi.davi@davi.com", "daviA@100", "81912344321", "11111111101", new Date(2000-11-18)));
            User user2 = new User(new UserUpdateDTO("Rafael Lancry", "rafa.rafa@rafa.com", "rafaL@100", "81943211234", "22222222202", new Date(2000-10-18)));
            User user3 = new User(new UserUpdateDTO("Julia Calado", "julia.julia@julia.com", "juliaC@100", "81912341234", "33333333303", new Date(2000-9-18)));
            User user4 = new User(new UserUpdateDTO("Vinicius Paz", "vini.vini@vini.com", "viniP@100", "81943214321", "44444444404", new Date(2000-8-18)));
            User user5 = new User(new UserUpdateDTO("Guilherme Marinho", "gui.gui@gui.com", "guiM@100", "81912121212", "55555555505", new Date(2000-7-18)));
            User user6 = new User(new UserUpdateDTO("Patrick Costa", "pat.pat@pat.com", "patC@100", "81912312312", "66666666606", new Date(2000-6-18)));
            userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5, user6));

            //Cria os carrinhos dos usuários
            Cart cart1 = new Cart(user1);
            Cart cart2 = new Cart(user2);
            Cart cart3 = new Cart(user3);
            Cart cart4 = new Cart(user4);
            Cart cart5 = new Cart(user5);
            Cart cart6 = new Cart(user6);
            cartRepository.saveAll(Arrays.asList(cart1, cart2, cart3, cart4, cart5, cart6));
        }
    }
}