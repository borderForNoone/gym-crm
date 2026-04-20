package org.gym.crm.facade;

import org.gym.crm.dto.request.TraineeRequestDto;
import org.gym.crm.dto.request.TrainerRequestDto;
import org.gym.crm.dto.request.TrainingRequestDto;
import org.gym.crm.dto.responce.TraineeResponseDto;
import org.gym.crm.dto.responce.TrainerResponseDto;
import org.gym.crm.dto.responce.TrainingResponseDto;
import org.gym.crm.mapper.TraineeMapper;
import org.gym.crm.mapper.TrainerMapper;
import org.gym.crm.mapper.TrainingMapper;
import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
import org.gym.crm.service.TraineeService;
import org.gym.crm.service.TrainerService;
import org.gym.crm.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GymFacadeTest {

    @Mock
    private TraineeService traineeService;
    @Mock
    private TrainerService trainerService;
    @Mock
    private TrainingService trainingService;
    @Mock
    private TraineeMapper traineeMapper;
    @Mock
    private TrainerMapper trainerMapper;
    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private GymFacade gymFacade;

    private Trainee trainee;
    private Trainer trainer;
    private Training training;
    private TraineeRequestDto traineeRequest;
    private TrainerRequestDto trainerRequest;
    private TrainingRequestDto trainingRequest;
    private TraineeResponseDto traineeResponse;
    private TrainerResponseDto trainerResponse;
    private TrainingResponseDto trainingResponse;

    @BeforeEach
    void setUp() {
        trainee = Trainee.builder()
                .firstName("John")
                .lastName("Smith")
                .username("John.Smith")
                .isActive(true)
                .build();

        TrainingType fitness = new TrainingType();
        fitness.setTrainingTypeName("FITNESS");

        trainer = Trainer.builder()
                .firstName("Jane")
                .lastName("Doe")
                .username("Jane.Doe")
                .isActive(true)
                .specialization(fitness)
                .build();

        training = Training.builder()
                .id(1L)
                .traineeId(1L)
                .trainerId(1L)
                .trainingName("Morning Workout")
                .trainingType(fitness)
                .trainingDate(LocalDate.of(2024, 1, 1))
                .trainingDuration(60)
                .build();

        traineeRequest = TraineeRequestDto.builder()
                .firstName("John")
                .lastName("Smith")
                .isActive(true)
                .build();

        trainerRequest = TrainerRequestDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .isActive(true)
                .specialization(fitness)
                .build();

        trainingRequest = TrainingRequestDto.builder()
                .traineeId(1L)
                .trainerId(1L)
                .trainingName("Morning Workout")
                .trainingType(fitness)
                .trainingDate(LocalDate.of(2024, 1, 1))
                .trainingDuration(60)
                .build();

        traineeResponse = TraineeResponseDto.builder()
                .username("John.Smith")
                .firstName("John")
                .lastName("Smith")
                .isActive(true)
                .build();

        trainerResponse = TrainerResponseDto.builder()
                .username("Jane.Doe")
                .firstName("Jane")
                .lastName("Doe")
                .isActive(true)
                .specialization(fitness)
                .build();

        trainingResponse = TrainingResponseDto.builder()
                .id(1L)
                .traineeId(1L)
                .trainerId(1L)
                .trainingName("Morning Workout")
                .trainingType(fitness)
                .trainingDate(LocalDate.of(2024, 1, 1))
                .trainingDuration(60)
                .build();
    }

    @Test
    void createTrainee_shouldMapAndDelegateToService() {
        when(traineeMapper.toEntity(traineeRequest)).thenReturn(trainee);
        when(traineeService.create(1L, trainee)).thenReturn(trainee);
        when(traineeMapper.toResponseDto(trainee)).thenReturn(traineeResponse);

        TraineeResponseDto result = gymFacade.createTrainee(1L, traineeRequest);

        assertEquals(traineeResponse, result);
        verify(traineeMapper).toEntity(traineeRequest);
        verify(traineeService).create(1L, trainee);
        verify(traineeMapper).toResponseDto(trainee);
    }

    @Test
    void getTrainee_shouldReturnDto_whenExists() {
        when(traineeService.findById(1L)).thenReturn(Optional.of(trainee));
        when(traineeMapper.toResponseDto(trainee)).thenReturn(traineeResponse);

        Optional<TraineeResponseDto> result = gymFacade.getTrainee(1L);

        assertTrue(result.isPresent());
        assertEquals(traineeResponse, result.get());
        verify(traineeService).findById(1L);
    }

    @Test
    void getTrainee_shouldReturnEmpty_whenNotExists() {
        when(traineeService.findById(99L)).thenReturn(Optional.empty());

        Optional<TraineeResponseDto> result = gymFacade.getTrainee(99L);

        assertTrue(result.isEmpty());
        verify(traineeService).findById(99L);
    }

    @Test
    void getAllTrainees_shouldReturnMappedList() {
        when(traineeService.findAll()).thenReturn(List.of(trainee));
        when(traineeMapper.toResponseDto(trainee)).thenReturn(traineeResponse);

        List<TraineeResponseDto> result = gymFacade.getAllTrainees();

        assertEquals(1, result.size());
        assertEquals(traineeResponse, result.getFirst());
        verify(traineeService).findAll();
    }

    @Test
    void updateTrainee_shouldMapAndDelegateToService() {
        when(traineeMapper.toEntity(traineeRequest)).thenReturn(trainee);
        when(traineeService.update(1L, trainee)).thenReturn(trainee);
        when(traineeMapper.toResponseDto(trainee)).thenReturn(traineeResponse);

        TraineeResponseDto result = gymFacade.updateTrainee(1L, traineeRequest);

        assertEquals(traineeResponse, result);
        verify(traineeMapper).toEntity(traineeRequest);
        verify(traineeService).update(1L, trainee);
        verify(traineeMapper).toResponseDto(trainee);
    }

    @Test
    void deleteTrainee_shouldDelegateToService() {
        gymFacade.deleteTrainee(1L);

        verify(traineeService).delete(1L);
    }

    @Test
    void createTrainer_shouldMapAndDelegateToService() {
        when(trainerMapper.toEntity(trainerRequest)).thenReturn(trainer);
        when(trainerService.create(1L, trainer)).thenReturn(trainer);
        when(trainerMapper.toResponseDto(trainer)).thenReturn(trainerResponse);

        TrainerResponseDto result = gymFacade.createTrainer(1L, trainerRequest);

        assertEquals(trainerResponse, result);
        verify(trainerMapper).toEntity(trainerRequest);
        verify(trainerService).create(1L, trainer);
        verify(trainerMapper).toResponseDto(trainer);
    }

    @Test
    void getTrainer_shouldReturnDto_whenExists() {
        when(trainerService.findById(1L)).thenReturn(Optional.of(trainer));
        when(trainerMapper.toResponseDto(trainer)).thenReturn(trainerResponse);

        Optional<TrainerResponseDto> result = gymFacade.getTrainer(1L);

        assertTrue(result.isPresent());
        assertEquals(trainerResponse, result.get());
        verify(trainerService).findById(1L);
    }

    @Test
    void getTrainer_shouldReturnEmpty_whenNotExists() {
        when(trainerService.findById(99L)).thenReturn(Optional.empty());

        Optional<TrainerResponseDto> result = gymFacade.getTrainer(99L);

        assertTrue(result.isEmpty());
        verify(trainerService).findById(99L);
    }

    @Test
    void getAllTrainers_shouldReturnMappedList() {
        when(trainerService.findAll()).thenReturn(List.of(trainer));
        when(trainerMapper.toResponseDto(trainer)).thenReturn(trainerResponse);

        List<TrainerResponseDto> result = gymFacade.getAllTrainers();

        assertEquals(1, result.size());
        assertEquals(trainerResponse, result.getFirst());
        verify(trainerService).findAll();
    }

    @Test
    void updateTrainer_shouldMapAndDelegateToService() {
        when(trainerMapper.toEntity(trainerRequest)).thenReturn(trainer);
        when(trainerService.update(1L, trainer)).thenReturn(trainer);
        when(trainerMapper.toResponseDto(trainer)).thenReturn(trainerResponse);

        TrainerResponseDto result = gymFacade.updateTrainer(1L, trainerRequest);

        assertEquals(trainerResponse, result);
        verify(trainerMapper).toEntity(trainerRequest);
        verify(trainerService).update(1L, trainer);
        verify(trainerMapper).toResponseDto(trainer);
    }

    @Test
    void createTraining_shouldMapAndDelegateToService() {
        when(trainingMapper.toEntity(trainingRequest)).thenReturn(training);
        when(trainingService.create(training)).thenReturn(training);
        when(trainingMapper.toResponseDto(training)).thenReturn(trainingResponse);

        TrainingResponseDto result = gymFacade.createTraining(trainingRequest);

        assertEquals(trainingResponse, result);
        verify(trainingMapper).toEntity(trainingRequest);
        verify(trainingService).create(training);
        verify(trainingMapper).toResponseDto(training);
    }

    @Test
    void getTraining_shouldReturnDto_whenExists() {
        when(trainingService.findById(1L)).thenReturn(Optional.of(training));
        when(trainingMapper.toResponseDto(training)).thenReturn(trainingResponse);

        Optional<TrainingResponseDto> result = gymFacade.getTraining(1L);

        assertTrue(result.isPresent());
        assertEquals(trainingResponse, result.get());
        verify(trainingService).findById(1L);
    }

    @Test
    void getTraining_shouldReturnEmpty_whenNotExists() {
        when(trainingService.findById(99L)).thenReturn(Optional.empty());

        Optional<TrainingResponseDto> result = gymFacade.getTraining(99L);

        assertTrue(result.isEmpty());
        verify(trainingService).findById(99L);
    }

    @Test
    void getAllTrainings_shouldReturnMappedList() {
        when(trainingService.findAll()).thenReturn(List.of(training));
        when(trainingMapper.toResponseDto(training)).thenReturn(trainingResponse);

        List<TrainingResponseDto> result = gymFacade.getAllTrainings();

        assertEquals(1, result.size());
        assertEquals(trainingResponse, result.getFirst());
        verify(trainingService).findAll();
    }
}