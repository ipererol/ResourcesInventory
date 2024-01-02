package org.dam.pojo;

import jakarta.persistence.NoResultException;
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
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Resource> cr = cb.createQuery(Resource.class);
            Root<Resource> root = cr.from(Resource.class);
            cr.select(root);
            return session.createQuery(cr).getResultList();
        } catch (HibernateException hibernateException){
            System.err.println(hibernateException.getMessage());
            return null;
        }
    }

    @Override
    public List<Resource> listResourcesByName(String name) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Resource> cr = cb.createQuery(Resource.class);
            Root<Resource> root = cr.from(Resource.class);
            cr.select(root).where(cb.like(root.get("name"), "%"+name+"%"));
            return session.createQuery(cr).getResultList();
        } catch (HibernateException hibernateException){
            System.err.println(hibernateException.getMessage());
            return null;
        }
    }

    @Override
    public Resource getResourceById(Long idResource) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Resource.class, idResource);
        } catch (HibernateException hibernateException){
            System.err.println(hibernateException.getMessage());
            return null;
        }
    }

    @Override
    public Resource getResourceByName(String name) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Resource> cr = cb.createQuery(Resource.class);
            Root<Resource> root = cr.from(Resource.class);
            cr.select(root);
            cr.select(root).where(cb.equal(root.get("name"), name));
            return session.createQuery(cr).getSingleResult();
        } catch (HibernateException hibernateException){
            System.err.println(hibernateException.getMessage());
            return null;
        } catch (NoResultException e) {
            System.err.println("No hay " + name);
            return null;
        }
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
            Resource storedResource = this.getResourceByName(resource.getName());
            if(storedResource != null){
                storedResource.setAmount(resource.getAmount() + storedResource.getAmount());
                resource = storedResource;
            }
            session.merge(resource);
            System.out.println("Se ha adquirido " + resource.getAmount() + " de " + resource.getName() + " hay " + (storedResource != null ? storedResource.getAmount() : 0));
            transaction.commit();
        } catch (HibernateException exception){
            if(transaction != null) {
                transaction.rollback();
            }
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public void updateOrDeleteResource(Resource resource) {
        System.out.println("aqui");
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Resource storedResource = this.getResourceByName(resource.getName());
            if(storedResource == null){
                System.err.println("El recurso " + resource.getName() + " no existe");
            } else {
                if (storedResource.getAmount() > resource.getAmount()) {
                    storedResource.setAmount(storedResource.getAmount() - resource.getAmount());
                    session.merge(storedResource);
                    System.out.println("Se ha utilizado " + resource.getAmount() + " de " + resource.getName() + " quedan " + storedResource.getAmount());
                } else if (storedResource.getAmount() == resource.getAmount()) {
                    session.remove(storedResource);
                } else {
                    System.err.println("No se pueden utilizar " + resource.getAmount() + " de " + resource.getName() + " porque solo quedan " + storedResource.getAmount());
                }
            }
            transaction.commit();
        }catch (HibernateException exception){
            if(transaction != null) {
                transaction.rollback();
            }
            System.err.println(exception.getMessage());
        }
    }
}
