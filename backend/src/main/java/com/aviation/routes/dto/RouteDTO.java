package com.aviation.routes.dto;

import java.util.List;

public class RouteDTO {
    private List<RouteSegmentDTO> segments;

    public List<RouteSegmentDTO> getSegments() {
        return segments;
    }
    public void setSegments(List<RouteSegmentDTO> segments) {
        this.segments = segments;
    }
}
