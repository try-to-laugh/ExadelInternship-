package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.TagDTO;

import java.util.List;

public interface ITagService {
    List<TagDTO> getAll();
    TagDTO getById(Long id);
}
