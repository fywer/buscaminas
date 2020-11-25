/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.EntidadCuenta;
import entidad.EntidadPerfil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author fywer
 */
public class EntidadPerfilJpaController implements Serializable {

    public EntidadPerfilJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EntidadPerfil entidadPerfil) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        EntidadCuenta entidadCuentaOrphanCheck = entidadPerfil.getEntidadCuenta();
        if (entidadCuentaOrphanCheck != null) {
            EntidadPerfil oldEntidadPerfilOfEntidadCuenta = entidadCuentaOrphanCheck.getEntidadPerfil();
            if (oldEntidadPerfilOfEntidadCuenta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The EntidadCuenta " + entidadCuentaOrphanCheck + " already has an item of type EntidadPerfil whose entidadCuenta column cannot be null. Please make another selection for the entidadCuenta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EntidadCuenta entidadCuenta = entidadPerfil.getEntidadCuenta();
            /*
            if (entidadCuenta != null) {
                entidadCuenta = em.getReference(entidadCuenta.getClass(), entidadCuenta.getUsuario());
                entidadPerfil.setEntidadCuenta(entidadCuenta);
            }
            */
            em.persist(entidadPerfil);
            /*
            if (entidadCuenta != null) {
                entidadCuenta.setEntidadPerfil(entidadPerfil);
                entidadCuenta = em.merge(entidadCuenta);
            }
            */
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEntidadPerfil(entidadPerfil.getCuentaUsuario()) != null) {
                throw new PreexistingEntityException("EntidadPerfil " + entidadPerfil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EntidadPerfil entidadPerfil) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EntidadPerfil persistentEntidadPerfil = em.find(EntidadPerfil.class, entidadPerfil.getCuentaUsuario());
            EntidadCuenta entidadCuentaOld = persistentEntidadPerfil.getEntidadCuenta();
            EntidadCuenta entidadCuentaNew = entidadPerfil.getEntidadCuenta();
            List<String> illegalOrphanMessages = null;
            if (entidadCuentaNew != null && !entidadCuentaNew.equals(entidadCuentaOld)) {
                EntidadPerfil oldEntidadPerfilOfEntidadCuenta = entidadCuentaNew.getEntidadPerfil();
                if (oldEntidadPerfilOfEntidadCuenta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The EntidadCuenta " + entidadCuentaNew + " already has an item of type EntidadPerfil whose entidadCuenta column cannot be null. Please make another selection for the entidadCuenta field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (entidadCuentaNew != null) {
                entidadCuentaNew = em.getReference(entidadCuentaNew.getClass(), entidadCuentaNew.getUsuario());
                entidadPerfil.setEntidadCuenta(entidadCuentaNew);
            }
            entidadPerfil = em.merge(entidadPerfil);
            if (entidadCuentaOld != null && !entidadCuentaOld.equals(entidadCuentaNew)) {
                entidadCuentaOld.setEntidadPerfil(null);
                entidadCuentaOld = em.merge(entidadCuentaOld);
            }
            if (entidadCuentaNew != null && !entidadCuentaNew.equals(entidadCuentaOld)) {
                entidadCuentaNew.setEntidadPerfil(entidadPerfil);
                entidadCuentaNew = em.merge(entidadCuentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = entidadPerfil.getCuentaUsuario();
                if (findEntidadPerfil(id) == null) {
                    throw new NonexistentEntityException("The entidadPerfil with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EntidadPerfil entidadPerfil;
            try {
                entidadPerfil = em.getReference(EntidadPerfil.class, id);
                entidadPerfil.getCuentaUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entidadPerfil with id " + id + " no longer exists.", enfe);
            }
            EntidadCuenta entidadCuenta = entidadPerfil.getEntidadCuenta();
            if (entidadCuenta != null) {
                entidadCuenta.setEntidadPerfil(null);
                entidadCuenta = em.merge(entidadCuenta);
            }
            em.remove(entidadPerfil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EntidadPerfil> findEntidadPerfilEntities() {
        return findEntidadPerfilEntities(true, -1, -1);
    }

    public List<EntidadPerfil> findEntidadPerfilEntities(int maxResults, int firstResult) {
        return findEntidadPerfilEntities(false, maxResults, firstResult);
    }

    private List<EntidadPerfil> findEntidadPerfilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EntidadPerfil.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public EntidadPerfil findEntidadPerfil(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EntidadPerfil.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntidadPerfilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EntidadPerfil> rt = cq.from(EntidadPerfil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
