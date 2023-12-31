package org.dam.pojo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.dam.dao.ResourceDAO;
import org.dam.model.Resource;
import org.dam.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ResourcePojo implements ResourceDAO{

    @Override
    public void createResource(Resource resource) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(resource);
            transaction.commit();
        } catch (HibernateException exception){
            if(transaction != null) {
                transaction.rollback();
            }
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public List<Resource> listResources() {
        return null;
    }

    @Override
    public List<Resource> listResourcesByName(String name) {
        return null;
    }

    @Override
    public Resource getResourceById(Long idResource) {
        return null;
    }

    @Override
    public Resource getResourceByName(String name) {
        return null;
    }

    @Override
    public void updateResource(Resource resource) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(resource);
            transaction.commit();
        } catch (HibernateException exception){
            if(transaction != null) {
                transaction.rollback();
            }
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public void deleteResource(Long resourceId) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Resource resource = session.get(Resource.class, resourceId);
            if (resource.getId() == 0) {
                System.err.println("No existe el recurso con id " + resourceId);
            } else {
                session.remove(resource);
            }
            transaction.commit();
        } catch (HibernateException exception){
            if(transaction != null) {
                transaction.rollback();
            }
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public void createOrUpdateResource(Resource resource) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Resource> cr = cb.createQuery(Resource.class);
            Root<Resource> root = cr.from(Resource.class);
            cr.select(root).where(cb.equal(root.get("name"), resource.getName()));
            List<Resource> storedResources = session.createQuery(cr).getResultList();
            if(!storedResources.isEmpty()){
                Resource storedResource = storedResources.getFirst();
                storedResource.setAmount(resource.getAmount() + storedResource.getAmount());
                resource = storedResource;
            }
            session.merge(resource);
            transaction.commit();
        } catch (HibernateException exception){
            if(transaction != null) {
                transaction.rollback();
            }
            System.err.println(exception.getMessage());
        }
    }
}
