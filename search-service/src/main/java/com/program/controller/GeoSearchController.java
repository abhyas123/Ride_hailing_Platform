package com.program.controller;

import com.program.dto.request.RouteSearchRequest;
import com.program.dto.response.RouteSearchResponse;
import com.program.service.GeoSearchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class GeoSearchController {

    private final GeoSearchService geoSearchService;

    public GeoSearchController(GeoSearchService geoSearchService) {
        this.geoSearchService = geoSearchService;
    }

    /**
     * POST /search/route
     * Rider searches pickup & drop
     */
    @PostMapping("/route")
    public ResponseEntity<RouteSearchResponse> searchRoute(
            @Valid @RequestBody RouteSearchRequest request) {

        RouteSearchResponse response =
                geoSearchService.searchRoute(request);

        return ResponseEntity.ok(response);
    }
}
