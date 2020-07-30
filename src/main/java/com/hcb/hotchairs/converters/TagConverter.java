package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.TagDTO;
import com.hcb.hotchairs.entities.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class TagConverter {
    public TagDTO toDTO(Tag tag){
        if(Objects.isNull(tag)){
            return null;
        }

        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());

        return tagDTO;
    }

    public Tag fromDTO(TagDTO tagDTO) {
        if (Objects.isNull(tagDTO)) {
            return null;
        }

        return new Tag(tagDTO.getId(), tagDTO.getName(), new ArrayList<>());
    }
}
