package com.yaremax.busticketbooth_tech.mappers;

import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.dto.RouteDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
@AllArgsConstructor
public class RouteDtoMapper {

    public Route toEntity(RouteDto routeDto) {
        Route route = new Route();
        route.setName(routeDto.getName());

        return route;
    }

}
