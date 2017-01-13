package ch.template.service;

import java.util.ArrayList;
import java.util.List;

import ch.template.domain.TemplateDto;

public class TemplateService {
    private final List<TemplateDto> dtos;

    public TemplateService() {
        this.dtos = new ArrayList<>();
    }

    public void add(TemplateDto templateDto) {
        this.dtos.add(templateDto);
    }

    public List<TemplateDto> getElements() {
        return dtos;
    }
}
