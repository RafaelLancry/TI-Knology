package br.com.ti_knology.config;

import br.com.ti_knology.enums.Status;
import br.com.ti_knology.model.Category;
import br.com.ti_knology.repository.CategoryRepository;
import br.com.ti_knology.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StartBd implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ServiceRepository serviceRepository;

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
            serviceRepository.insertService("Desenvolvimento de Software", Status.ANALISE.name(), 5000.00F, 30, null, categoryDev.getId());
            serviceRepository.insertService("Consultoria em TI", Status.ANALISE.name(), 3000.00F, 15, null, categoryConsultoria.getId());
            serviceRepository.insertService("Soluções em Nuvem", Status.ANALISE.name(), 4000.00F, 20, null, categoryConsultoria.getId());
            serviceRepository.insertService("Desenvolvimento de Software Personalizado", Status.ANALISE.name(), 8000.00F, 45, null, categoryDev.getId());
            serviceRepository.insertService("Consultoria em Transformação Digital", Status.ANALISE.name(), 7000.00F, 25, null, categoryConsultoria.getId());
            serviceRepository.insertService("Inteligência Artificial e Aprendizado de Máquina", Status.ANALISE.name(), 12000.00F, 60, null, categoryDev.getId());
            serviceRepository.insertService("Soluções de Internet das Coisas (IoT)", Status.ANALISE.name(), 9000.00F, 50, null, categoryConsultoria.getId());

            System.out.println("Serviços adicionados ao banco de dados.");
        }
    }
}