package account.services;

//@Component
public class EntityService {
/*    private  EntityManager entityManager;



   // @Autowired
    public EntityService(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }


    public void insertEntities(Object o) {
        entityManager.getTransaction().begin();

  *//*      Animal catLeo = new Animal("cat", "Leo", false);
        Animal dogCharlie = new Animal("dog", "Charlie", true);
        Animal dogBella = new Animal("dog", "Bella", false);

        Person catLover1 = new Person("James", 8);
        Person catLover2 = new Person("Mary", 6);
        Person dogLover1 = new Person("John", 4);

        catLeo.setPeopleInContact(Set.of(catLover1, catLover2));
        dogCharlie.getPeopleInContact().add(dogLover1);
        dogBella.getPeopleInContact().add(dogLover1);

        catLover1.getAnimalsInContact().add(catLeo);
        catLover2.getAnimalsInContact().add(catLeo);
        dogLover1.setAnimalsInContact(Set.of(dogCharlie, dogBella));

        entityManager.persist(catLeo);
        entityManager.persist(dogCharlie);
        entityManager.persist(dogBella);*//*

        entityManager.persist(o);

        entityManager.getTransaction().commit();
        entityManager.clear();
    }*/
}
