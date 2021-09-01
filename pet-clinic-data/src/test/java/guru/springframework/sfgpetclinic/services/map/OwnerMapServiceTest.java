package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class OwnerMapServiceTest {

    private OwnerMapService ownerMapService;
    final Long ownerId = 1L;
    final String lastName = "Smith";

    @Mock
    private PetTypeService petTypeService;

    @Mock
    private PetService petService;

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerMapService.findAll();
        assertThat(ownerSet.size()).isEqualTo(1);
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);
        assertThat(owner.getId()).isEqualTo(ownerId);
    }

    @Test
    void saveExistingId() {
        Long id = 2L;
        Owner owner2 = Owner.builder().id(2L).build();
        Owner sawedOwner = ownerMapService.save(owner2);
        assertThat(sawedOwner.getId()).isEqualTo(id);
    }

    @Test
    void saveNoId() {
        Owner owner2 = Owner.builder().build();
        Owner sawedOwner = ownerMapService.save(owner2);
        assertThat(sawedOwner).isNotNull();
        assertThat(sawedOwner.getId()).isNotNull();
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));
        System.out.println(ownerMapService.findAll().size());
        assertThat(ownerMapService.findAll().size()).isEqualTo(0);
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);
        assertThat(ownerMapService.findAll().size()).isEqualTo(0);
    }

    @Test
    void findByLastName() {
        Owner smith = ownerMapService.findByLastName(lastName);
        assertThat(ownerId).isEqualTo(smith.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner smith = ownerMapService.findByLastName("foo");
        assertThat(smith).isNull();
    }
}