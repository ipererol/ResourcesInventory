package org.dam.dao;

import org.dam.model.Resource;

import java.util.List;

public interface ResourceDAO {
    void createResource(Resource resource);
    List<Resource> listResources();
    List<Resource> listResourcesByName(String name);
    Resource getResourceById(Long idResource);
    Resource getResourceByName(String name);
    void updateResource(Resource resource);
    void deleteResource(Long resourceId);
    void createOrUpdateResource(Resource resource);
}
