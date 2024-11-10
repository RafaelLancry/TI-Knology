package br.com.ti_knology.config;

import br.com.ti_knology.DTO.ServicesRequestsDTO;
import br.com.ti_knology.enums.ServicesType;
import br.com.ti_knology.enums.Status;

import java.util.ArrayList;
import java.util.List;

public class ServiceRequestCreation {
    public static List<ServicesRequestsDTO> getAllServicesTypes() {
        List<ServicesRequestsDTO> services = new ArrayList<ServicesRequestsDTO>();
        ServicesRequestsDTO request1 = new ServicesRequestsDTO(ServicesType.DESENVOLVIMENTO_DE_SOFTWARE.getId(), ServicesType.DESENVOLVIMENTO_DE_SOFTWARE.getValue(), Status.ANALISE, 5000.00F, 30, null);
        services.add(request1);
        ServicesRequestsDTO request2 = new ServicesRequestsDTO(ServicesType.CONSULTORIA_EM_TI.getId(), ServicesType.CONSULTORIA_EM_TI.getValue(), Status.ANALISE, 3000.00F, 15, null);
        services.add(request2);
        ServicesRequestsDTO request3 = new ServicesRequestsDTO(ServicesType.SOLUCOES_EM_NUVEM.getId(), ServicesType.SOLUCOES_EM_NUVEM.getValue(), Status.ANALISE, 4000.00F, 20, null);
        services.add(request3);
        ServicesRequestsDTO request4 = new ServicesRequestsDTO(ServicesType.DESENVOLVIMENTO_PERSONALIZADO.getId(), ServicesType.DESENVOLVIMENTO_PERSONALIZADO.getValue(), Status.ANALISE, 8000.00F, 45, null);
        services.add(request4);
        ServicesRequestsDTO request5 = new ServicesRequestsDTO(ServicesType.CONSULTORIA_TRANSFORMACAO.getId(), ServicesType.CONSULTORIA_TRANSFORMACAO.getValue(), Status.ANALISE, 7000.00F, 25, null);
        services.add(request5);
        ServicesRequestsDTO request6 = new ServicesRequestsDTO(ServicesType.INTELIGENCIA_ARTIFICIAL.getId(), ServicesType.INTELIGENCIA_ARTIFICIAL.getValue(), Status.ANALISE, 12000.00F, 60, null);
        services.add(request6);
        ServicesRequestsDTO request7 = new ServicesRequestsDTO(ServicesType.SOLUCAO_IOT.getId(), ServicesType.SOLUCAO_IOT.getValue(), Status.ANALISE, 9000.00F, 50, null);
        services.add(request7);
        ServicesRequestsDTO request8 = new ServicesRequestsDTO(ServicesType.SOLUCOES_EMBARCADAS.getId(), ServicesType.SOLUCOES_EMBARCADAS.getValue(), Status.ANALISE, 700000.00F, 365, null);
        services.add(request8);

        return services;
    }
}
