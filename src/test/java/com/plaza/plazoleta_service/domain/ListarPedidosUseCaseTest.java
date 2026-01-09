package com.plaza.plazoleta_service.domain;

import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.model.PlatoPedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.domain.usecase.ListarPedidosUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ListarPedidosUseCaseTest {

    private IPedidoPersistencePort pedidoPersistencePort;
    private ListarPedidosUseCase listarPedidosUseCase;
    private Random random;

    @BeforeEach
    void setUp() {
        pedidoPersistencePort = Mockito.mock(IPedidoPersistencePort.class);
        listarPedidosUseCase = new ListarPedidosUseCase(pedidoPersistencePort);
        random = new Random();
    }

    @Test
    void listarPedidosPorEstadoConDatosAleatorios() {


        List<Pedido> pedidos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Pedido pedido = new Pedido();
            pedido.setId((long) i + 1);
            pedido.setClienteId((long) (random.nextInt(10) + 1));
            pedido.setRestauranteId(1L);
            pedido.setEstado("PENDIENTE");


            List<PlatoPedido> platos = new ArrayList<>();
            int cantidadPlatos = random.nextInt(3) + 1; // 1 a 3 platos
            for (int j = 0; j < cantidadPlatos; j++) {
                PlatoPedido plato = new PlatoPedido();
                plato.setPlatoId((long) (random.nextInt(20) + 1));
                plato.setNombre("Plato " + plato.getPlatoId());
                plato.setCantidad(random.nextInt(3) + 1); // 1 a 3 unidades
                plato.setPrecio((long) (random.nextInt(50000) + 10000));
                platos.add(plato);
            }
            pedido.setPlatos(platos);
            pedidos.add(pedido);
        }

        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Pedido> pagePedidos = new PageImpl<>(pedidos, pageable, pedidos.size());


        when(pedidoPersistencePort.listarPedidosPorRestauranteYEstado(
                Mockito.eq(1L),
                Mockito.eq("PENDIENTE"),
                any(Pageable.class)
        )).thenReturn(pagePedidos);


        Page<Pedido> result = listarPedidosUseCase
                .listarPedidosPorRestauranteYEstado(1L, "PENDIENTE", page, size);

        assertEquals(pedidos.size(), result.getContent().size(), "Cantidad de pedidos no coincide");
        for (int i = 0; i < pedidos.size(); i++) {
            assertEquals(pedidos.get(i).getId(), result.getContent().get(i).getId(), "ID de pedido no coincide");
        }

        System.out.println("Pedidos listados correctamente con datos aleatorios:");
        result.getContent().forEach(System.out::println);
    }
}