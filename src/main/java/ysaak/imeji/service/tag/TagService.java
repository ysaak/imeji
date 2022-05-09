package ysaak.imeji.service.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysaak.imeji.data.Tag;
import ysaak.imeji.data.TagType;
import ysaak.imeji.db.repository.TagRepository;
import ysaak.imeji.exception.ValidationException;
import ysaak.imeji.utils.CollectionUtils;
import ysaak.imeji.utils.StringUtils;
import ysaak.imeji.utils.Validator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getOrCreate(final List<String> tags) {

        List<String> tagsWithoutMetadata = tags.stream()
                .map(t -> StringUtils.substringAfterLast(t, ':'))
                .collect(Collectors.toList());

        List<Tag> result = this.tagRepository.findAllByNameIn(tagsWithoutMetadata);

        if (result.size() != tags.size()) {
            List<String> exitingNames = result.stream().map(Tag::getName).collect(Collectors.toList());

            List<String> tagsToCreate = new ArrayList<>();
            for (String tag : tags) {
                for (String existingTag : exitingNames) {
                    if (!(tag.equals(existingTag) || tag.endsWith(":" + exitingNames))) {
                        tagsToCreate.add(tag);
                    }
                }
            }

            result.addAll(massCreate(tagsToCreate));
        }

        return result;
    }

    private List<Tag> massCreate(List<String> names) {
        List<Tag> tags = new ArrayList<>(names.size());
        for (String name : names) {
            Tag tag = new Tag();
            tag.setCreationDate(LocalDateTime.now());

            String[] parts = name.split(":");
            if (parts.length == 1) {
                tag.setName(parts[0]);
                tag.setType(TagType.GENERAL);
            }
            else {
                tag.setName(parts[parts.length - 1]);
                tag.setType(TagType.fromMetadata(parts[0]));
            }

            if (validate(tag).isValid()) {
                tags.add(tag);
            }
        }

        return CollectionUtils.toList(tagRepository.saveAll(tags));
    }

    public Tag save(Tag tag) throws ValidationException {
        validate(tag).orElseThrow("Tag is not valid");

        return tagRepository.save(tag);
    }

    private Validator validate(Tag tag) {
        Validator validator = new Validator();

        // -- Name
        validator.notBlank(tag.getName(), "name", "Must not be blank");
        validator.regex(tag.getName(), "[a-zA-Z0-9_\\-()]+", "name", "Must contains only valid characters");
        validator.isFalse(tag.getName().startsWith("_") || tag.getName().endsWith("_"), "name", "Cannot begin or end with an underscore");
        validator.isFalse(tag.getName().contains("__"), "name", "Cannot contain consecutive underscores");

        // -- Type
        validator.notNull(tag.getType(), "type", "Must be selected");

        return validator;
    }
}
