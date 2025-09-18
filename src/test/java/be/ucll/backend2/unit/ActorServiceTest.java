package be.ucll.backend2.unit;

import be.ucll.backend2.exception.ActorNotFoundException;
import be.ucll.backend2.model.Actor;
import be.ucll.backend2.repository.ActorRepository;
import be.ucll.backend2.service.ActorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {
    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorService actorService;

    @Test
    public void givenActorExistsWithId_whenUpdateActor_thenActorIsUpdated() throws ActorNotFoundException {
        // Given
        final var actorInDb = new Actor("Jos Bosmans");
        actorInDb.setId(1L);
        Mockito.when(actorRepository.findById(1L)).thenReturn(Optional.of(actorInDb));
        Mockito.when(actorRepository.save(Mockito.any(Actor.class))).thenAnswer(i -> i.getArgument(0));

        // When
        final var updatedActor = actorService.updateActor(1L, new Actor("Clement Peerens"));

        // Then
        Assertions.assertEquals("Clement Peerens", updatedActor.getName());
        Mockito.verify(actorRepository).save(updatedActor);
    }
}
