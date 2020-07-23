package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.TagDTO;

import java.util.Collection;
import java.util.List;

public interface ITagService {
    List<TagDTO> getAll();
    List<TagDTO> getAllFromIdCollection(Collection<Long> requestId);
    TagDTO getById(Long id);
}
