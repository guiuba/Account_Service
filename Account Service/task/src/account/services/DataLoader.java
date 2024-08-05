package account.services;

import account.dao.GroupRepository;

//@Component
public class DataLoader {
    private GroupRepository groupRepository;

//    @Autowired
    /*public DataLoader(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
        createRoles();
    }*/

/*    private void createRoles() {
        try {
          *//*  groupRepository.save(new Group("ROLE_ADMINISTRATIVE"));
            groupRepository.save(new Group("ROLE_BUSINESS"));*//*
            groupRepository.save(new Group("ROLE_ADMINISTRATOR"));
            groupRepository.save(new Group("ROLE_USER"));
            groupRepository.save(new Group("ROLE_ACCOUNTANT"));
        } catch (Exception e) {

        }
    }*/
}
