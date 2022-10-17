/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.connectingdb;

import com.example.connectingdb.exceptions.IllegalOrphanException;
import com.example.connectingdb.exceptions.NonexistentEntityException;
import com.example.connectingdb.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author USER DJOGJA
 */
public class KueJpaController implements Serializable {

    public KueJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example_connectingdb_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public KueJpaController() {
    }

    
    
    public void create(Kue kue) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi transaksi = kue.getTransaksi();
            if (transaksi != null) {
                transaksi = em.getReference(transaksi.getClass(), transaksi.getKodeTransaksi());
                kue.setTransaksi(transaksi);
            }
            em.persist(kue);
            if (transaksi != null) {
                Kue oldKodeKueOfTransaksi = transaksi.getKodeKue();
                if (oldKodeKueOfTransaksi != null) {
                    oldKodeKueOfTransaksi.setTransaksi(null);
                    oldKodeKueOfTransaksi = em.merge(oldKodeKueOfTransaksi);
                }
                transaksi.setKodeKue(kue);
                transaksi = em.merge(transaksi);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKue(kue.getKodeKue()) != null) {
                throw new PreexistingEntityException("Kue " + kue + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kue kue) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kue persistentKue = em.find(Kue.class, kue.getKodeKue());
            Transaksi transaksiOld = persistentKue.getTransaksi();
            Transaksi transaksiNew = kue.getTransaksi();
            List<String> illegalOrphanMessages = null;
            if (transaksiOld != null && !transaksiOld.equals(transaksiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Transaksi " + transaksiOld + " since its kodeKue field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (transaksiNew != null) {
                transaksiNew = em.getReference(transaksiNew.getClass(), transaksiNew.getKodeTransaksi());
                kue.setTransaksi(transaksiNew);
            }
            kue = em.merge(kue);
            if (transaksiNew != null && !transaksiNew.equals(transaksiOld)) {
                Kue oldKodeKueOfTransaksi = transaksiNew.getKodeKue();
                if (oldKodeKueOfTransaksi != null) {
                    oldKodeKueOfTransaksi.setTransaksi(null);
                    oldKodeKueOfTransaksi = em.merge(oldKodeKueOfTransaksi);
                }
                transaksiNew.setKodeKue(kue);
                transaksiNew = em.merge(transaksiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = kue.getKodeKue();
                if (findKue(id) == null) {
                    throw new NonexistentEntityException("The kue with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kue kue;
            try {
                kue = em.getReference(Kue.class, id);
                kue.getKodeKue();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kue with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Transaksi transaksiOrphanCheck = kue.getTransaksi();
            if (transaksiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Kue (" + kue + ") cannot be destroyed since the Transaksi " + transaksiOrphanCheck + " in its transaksi field has a non-nullable kodeKue field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(kue);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kue> findKueEntities() {
        return findKueEntities(true, -1, -1);
    }

    public List<Kue> findKueEntities(int maxResults, int firstResult) {
        return findKueEntities(false, maxResults, firstResult);
    }

    private List<Kue> findKueEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kue.class));
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

    public Kue findKue(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kue.class, id);
        } finally {
            em.close();
        }
    }

    public int getKueCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kue> rt = cq.from(Kue.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
