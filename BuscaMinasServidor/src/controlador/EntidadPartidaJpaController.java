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
import entidad.EntidadPartida;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import modelo.Cuenta;
import modelo.Partida;

/**
 *
 * @author fywer
 */
public class EntidadPartidaJpaController implements Serializable {

    public EntidadPartidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EntidadPartida entidadPartida) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        EntidadCuenta entidadCuentaOrphanCheck = entidadPartida.getEntidadCuenta();
        if (entidadCuentaOrphanCheck != null) {
            EntidadPartida oldEntidadPartidaOfEntidadCuenta = entidadCuentaOrphanCheck.getEntidadPartida();
            if (oldEntidadPartidaOfEntidadCuenta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The EntidadCuenta " + entidadCuentaOrphanCheck + " already has an item of type EntidadPartida whose entidadCuenta column cannot be null. Please make another selection for the entidadCuenta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EntidadCuenta entidadCuenta = entidadPartida.getEntidadCuenta();
            /*
            if (entidadCuenta != null) {
                entidadCuenta = em.getReference(entidadCuenta.getClass(), entidadCuenta.getUsuario());
                entidadPartida.setEntidadCuenta(entidadCuenta);
            }
            */
            em.persist(entidadPartida);
            /*
            if (entidadCuenta != null) {
                entidadCuenta.setEntidadPartida(entidadPartida);
                entidadCuenta = em.merge(entidadCuenta);
            }
            */
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEntidadPartida(entidadPartida.getCuentaUsuario()) != null) {
                throw new PreexistingEntityException("EntidadPartida " + entidadPartida + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EntidadPartida entidadPartida) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EntidadPartida persistentEntidadPartida = em.find(EntidadPartida.class, entidadPartida.getCuentaUsuario());
            EntidadCuenta entidadCuentaOld = persistentEntidadPartida.getEntidadCuenta();
            EntidadCuenta entidadCuentaNew = entidadPartida.getEntidadCuenta();
            List<String> illegalOrphanMessages = null;
            if (entidadCuentaNew != null && !entidadCuentaNew.equals(entidadCuentaOld)) {
                EntidadPartida oldEntidadPartidaOfEntidadCuenta = entidadCuentaNew.getEntidadPartida();
                if (oldEntidadPartidaOfEntidadCuenta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The EntidadCuenta " + entidadCuentaNew + " already has an item of type EntidadPartida whose entidadCuenta column cannot be null. Please make another selection for the entidadCuenta field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (entidadCuentaNew != null) {
                entidadCuentaNew = em.getReference(entidadCuentaNew.getClass(), entidadCuentaNew.getUsuario());
                entidadPartida.setEntidadCuenta(entidadCuentaNew);
            }
            entidadPartida = em.merge(entidadPartida);
            if (entidadCuentaOld != null && !entidadCuentaOld.equals(entidadCuentaNew)) {
                entidadCuentaOld.setEntidadPartida(null);
                entidadCuentaOld = em.merge(entidadCuentaOld);
            }
            if (entidadCuentaNew != null && !entidadCuentaNew.equals(entidadCuentaOld)) {
                entidadCuentaNew.setEntidadPartida(entidadPartida);
                entidadCuentaNew = em.merge(entidadCuentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = entidadPartida.getCuentaUsuario();
                if (findEntidadPartida(id) == null) {
                    throw new NonexistentEntityException("The entidadPartida with id " + id + " no longer exists.");
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
            EntidadPartida entidadPartida;
            try {
                entidadPartida = em.getReference(EntidadPartida.class, id);
                entidadPartida.getCuentaUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entidadPartida with id " + id + " no longer exists.", enfe);
            }
            EntidadCuenta entidadCuenta = entidadPartida.getEntidadCuenta();
            if (entidadCuenta != null) {
                entidadCuenta.setEntidadPartida(null);
                entidadCuenta = em.merge(entidadCuenta);
            }
            em.remove(entidadPartida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EntidadPartida> findEntidadPartidaEntities() {
        return findEntidadPartidaEntities(true, -1, -1);
    }

    public List<EntidadPartida> findEntidadPartidaEntities(int maxResults, int firstResult) {
        return findEntidadPartidaEntities(false, maxResults, firstResult);
    }

    private List<EntidadPartida> findEntidadPartidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EntidadPartida.class));
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

    public EntidadPartida findEntidadPartida(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EntidadPartida.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntidadPartidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EntidadPartida> rt = cq.from(EntidadPartida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //bucar la partida en función del la cuenta
    public Partida buscarPartida(Partida partida) {
        EntityManager em = getEntityManager();
        try {
            EntidadPartida entidadPartida= (EntidadPartida) em.createNamedQuery("EntidadPartida.findByCuentaUsuario")
                    .setParameter("cuentaUsuario", partida.getCuentaUsuario()).getSingleResult();
            return entidadPartida.obtenerPartida();
        } catch (NoResultException ex) {
            System.err.println("LA BASE DE DATOS NO HA ENCONTRA RESULTADOS");
        } catch (NullPointerException ex) {
            System.err.println("LA BASE DE DATOS TIENE ENTIDADES NO ASOCIADAS");
        }
        return null;
    }
}
