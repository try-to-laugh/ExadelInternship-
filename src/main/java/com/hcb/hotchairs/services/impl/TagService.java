package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.TagConverter;
import com.hcb.hotchairs.daos.TagDAO;
import com.hcb.hotchairs.dtos.TagDTO;
import com.hcb.hotchairs.services.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService implements ITagService {

    private final TagDAO tagDAO;
    private final TagConverter tagConverter;


    @Autowired
    public TagService(TagDAO tagDAO, TagConverter tagConverter){
        this.tagDAO = tagDAO;
        this.tagConverter = tagConverter;
    }

    @Override
    public List<TagDTO> getAll() {
        return tagDAO.findAll().stream().map(tagConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public TagDTO getById(Long id) {
        return tagConverter.toDTO(tagDAO.findById(id).orElse(null));
    }
}
