package org.gym.crm.facade;

import org.gym.crm.dto.request.TraineeRequestDto;
import org.gym.crm.dto.request.TrainerRequestDto;
import org.gym.crm.dto.request.TrainingRequestDto;
import org.gym.crm.dto.response.TraineeResponseDto;
import org.gym.crm.dto.response.TrainerResponseDto;
import org.gym.crm.dto.response.TrainingResponseDto;
import org.gym.crm.mapper.TraineeMapper;
import org.gym.crm.mapper.TrainerMapper;
import org.gym.crm.mapper.TrainingMapper;
import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
import org.gym.crm.model.User;
import org.gym.crm.service.TraineeService;
import org.gym.crm.service.TrainerService;
import org.gym.crm.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.gym.crm.util.TestConstants.DURATION;
import static org.gym.crm.util.TestConstants.FIRST_NAME;
import static org.gym.crm.util.TestConstants.FITNESS;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.LAST_NAME;
import static org.gym.crm.util.TestConstants.NON_EXISTING_ID;
import static org.gym.crm.util.TestConstants.TRAINER_FIRST_NAME;
import static org.gym.crm.util.TestConstants.TRAINER_LAST_NAME;
import static org.gym.crm.util.TestConstants.TRAINER_USERNAME;
import static org.gym.crm.util.TestConstants.TRAINING_DATE;
import static org.gym.crm.util.TestConstants.TRAINING_NAME;
import static org.gym.crm.util.TestConstants.USERNAME;
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
    private GymFacade facade;

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
        trainee = buildTrainee();
        trainer = buildTrainer();
        training = buildTraining();
        traineeRequest = buildTraineeRequest();
        trainerRequest = buildTrainerRequest();
        trainingRequest = buildTrainingRequest();
        traineeResponse = buildTraineeResponse();
        trainerResponse = buildTrainerResponse();
        trainingResponse = buildTrainingResponse();
    }

    @Test
    void createTrainee_shouldMapAndDelegateToService() {
        when(traineeMapper.toEntity(traineeRequest)).thenReturn(trainee);
        when(traineeService.create(trainee)).thenReturn(trainee);
        when(traineeMapper.toDto(trainee)).thenReturn(traineeResponse);

        TraineeResponseDto actual = facade.createTrainee(traineeRequest);

        assertEquals(traineeResponse, actual);
        verify(traineeMapper).toEntity(traineeRequest);
        verify(traineeService).create(trainee);
        verify(traineeMapper).toDto(trainee);
    }

    @Test
    void getTrainee_shouldReturnDto_whenExists() {
        when(traineeService.findById(ID)).thenReturn(Optional.of(trainee));
        when(traineeMapper.toDto(trainee)).thenReturn(traineeResponse);

        Optional<TraineeResponseDto> actual = facade.getTrainee(ID);

        assertTrue(actual.isPresent());
        assertEquals(traineeResponse, actual.get());
        verify(traineeService).findById(ID);
    }

    @Test
    void getTrainee_shouldReturnEmpty_whenNotExists() {
        when(traineeService.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        Optional<TraineeResponseDto> actual = facade.getTrainee(NON_EXISTING_ID);

        assertTrue(actual.isEmpty());
        verify(traineeService).findById(NON_EXISTING_ID);
    }

    @Test
    void getAllTrainees_shouldReturnMappedList() {
        when(traineeService.findAll()).thenReturn(List.of(trainee));
        when(traineeMapper.toDto(trainee)).thenReturn(traineeResponse);

        List<TraineeResponseDto> actual = facade.getAllTrainees();

        assertEquals(1, actual.size());
        assertEquals(traineeResponse, actual.getFirst());
        verify(traineeService).findAll();
    }

    @Test
    void updateTrainee_shouldMapAndDelegateToService() {
        when(traineeMapper.toEntity(traineeRequest)).thenReturn(trainee);
        when(traineeService.update(ID, trainee)).thenReturn(trainee);
        when(traineeMapper.toDto(trainee)).thenReturn(traineeResponse);

        TraineeResponseDto actual = facade.updateTrainee(ID, traineeRequest);

        assertEquals(traineeResponse, actual);
        verify(traineeMapper).toEntity(traineeRequest);
        verify(traineeService).update(ID, trainee);
        verify(traineeMapper).toDto(trainee);
    }

    @Test
    void deleteTrainee_shouldDelegateToService() {
        facade.deleteTrainee(ID);

        verify(traineeService).delete(ID);
    }

    @Test
    void createTrainer_shouldMapAndDelegateToService() {
        when(trainerMapper.toEntity(trainerRequest)).thenReturn(trainer);
        when(trainerService.create(trainer)).thenReturn(trainer);
        when(trainerMapper.toDto(trainer)).thenReturn(trainerResponse);

        TrainerResponseDto actual = facade.createTrainer(ID, trainerRequest);

        assertEquals(trainerResponse, actual);
        verify(trainerMapper).toEntity(trainerRequest);
        verify(trainerService).create(trainer);
        verify(trainerMapper).toDto(trainer);
    }

    @Test
    void getTrainer_shouldReturnDto_whenExists() {
        when(trainerService.findById(ID)).thenReturn(Optional.of(trainer));
        when(trainerMapper.toDto(trainer)).thenReturn(trainerResponse);

        Optional<TrainerResponseDto> actual = facade.getTrainer(ID);

        assertTrue(actual.isPresent());
        assertEquals(trainerResponse, actual.get());
        verify(trainerService).findById(ID);
    }

    @Test
    void getTrainer_shouldReturnEmpty_whenNotExists() {
        when(trainerService.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        Optional<TrainerResponseDto> actual = facade.getTrainer(NON_EXISTING_ID);

        assertTrue(actual.isEmpty());
        verify(trainerService).findById(NON_EXISTING_ID);
    }

    @Test
    void getAllTrainers_shouldReturnMappedList() {
        when(trainerService.findAll()).thenReturn(List.of(trainer));
        when(trainerMapper.toDto(trainer)).thenReturn(trainerResponse);

        List<TrainerResponseDto> actual = facade.getAllTrainers();

        assertEquals(1, actual.size());
        assertEquals(trainerResponse, actual.getFirst());
        verify(trainerService).findAll();
    }

    @Test
    void updateTrainer_shouldMapAndDelegateToService() {
        when(trainerMapper.toEntity(trainerRequest)).thenReturn(trainer);
        when(trainerService.update(ID, trainer)).thenReturn(trainer);
        when(trainerMapper.toDto(trainer)).thenReturn(trainerResponse);

        TrainerResponseDto actual = facade.updateTrainer(ID, trainerRequest);

        assertEquals(trainerResponse, actual);
        verify(trainerMapper).toEntity(trainerRequest);
        verify(trainerService).update(ID, trainer);
        verify(trainerMapper).toDto(trainer);
    }

    @Test
    void createTraining_shouldMapAndDelegateToService() {
        when(trainingMapper.toEntity(trainingRequest)).thenReturn(training);
        when(trainingService.create(training)).thenReturn(training);
        when(trainingMapper.toDto(training)).thenReturn(trainingResponse);

        TrainingResponseDto actual = facade.createTraining(trainingRequest);

        assertEquals(trainingResponse, actual);
        verify(trainingMapper).toEntity(trainingRequest);
        verify(trainingService).create(training);
        verify(trainingMapper).toDto(training);
    }

    @Test
    void getTraining_shouldReturnDto_whenExists() {
        when(trainingService.findById(ID)).thenReturn(Optional.of(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingResponse);

        Optional<TrainingResponseDto> actual = facade.getTraining(ID);

        assertTrue(actual.isPresent());
        assertEquals(trainingResponse, actual.get());
        verify(trainingService).findById(ID);
    }

    @Test
    void getTraining_shouldReturnEmpty_whenNotExists() {
        when(trainingService.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        Optional<TrainingResponseDto> actual = facade.getTraining(NON_EXISTING_ID);

        assertTrue(actual.isEmpty());
        verify(trainingService).findById(NON_EXISTING_ID);
    }

    @Test
    void getAllTrainings_shouldReturnMappedList() {
        when(trainingService.findAll()).thenReturn(List.of(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingResponse);

        List<TrainingResponseDto> actual = facade.getAllTrainings();

        assertEquals(1, actual.size());
        assertEquals(trainingResponse, actual.getFirst());
        verify(trainingService).findAll();
    }

    private TrainingType fitnessType() {
        TrainingType fitness = new TrainingType();

        fitness.setTrainingTypeName(FITNESS);

        return fitness;
    }

    private Trainee buildTrainee() {
        return Trainee.builder()
                .user(User.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .username(USERNAME)
                        .isActive(true)
                        .build())
                .build();
    }

    private Trainer buildTrainer() {
        return Trainer.builder()
                .user(User.builder()
                        .firstName(TRAINER_FIRST_NAME)
                        .lastName(TRAINER_LAST_NAME)
                        .username(TRAINER_USERNAME)
                        .isActive(true)
                        .build())
                .specialization(fitnessType())
                .build();
    }

    private Training buildTraining() {
        return Training.builder()
                .id(ID)
                .traineeId(ID)
                .trainerId(ID)
                .trainingName(TRAINING_NAME)
                .trainingType(fitnessType())
                .trainingDate(TRAINING_DATE)
                .trainingDuration(DURATION)
                .build();
    }

    private TraineeRequestDto buildTraineeRequest() {
        return TraineeRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .active(true)
                .build();
    }

    private TrainerRequestDto buildTrainerRequest() {
        return TrainerRequestDto.builder()
                .firstName(TRAINER_FIRST_NAME)
                .lastName(TRAINER_LAST_NAME)
                .isActive(true)
                .specialization(fitnessType().getTrainingTypeName())
                .build();
    }

    private TrainingRequestDto buildTrainingRequest() {
        return TrainingRequestDto.builder()
                .traineeId(ID)
                .trainerId(ID)
                .trainingName(TRAINING_NAME)
                .trainingTypeId(ID)
                .trainingDate(TRAINING_DATE)
                .trainingDuration(DURATION)
                .build();
    }

    private TraineeResponseDto buildTraineeResponse() {
        return TraineeResponseDto.builder()
                .username(USERNAME)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .build();
    }

    private TrainerResponseDto buildTrainerResponse() {
        return TrainerResponseDto.builder()
                .username(TRAINER_USERNAME)
                .firstName(TRAINER_FIRST_NAME)
                .lastName(TRAINER_LAST_NAME)
                .isActive(true)
                .specialization(fitnessType())
                .build();
    }

    private TrainingResponseDto buildTrainingResponse() {
        return TrainingResponseDto.builder()
                .id(ID)
                .traineeId(ID)
                .trainerId(ID)
                .trainingName(TRAINING_NAME)
                .trainingTypeName(fitnessType().getTrainingTypeName())
                .trainingDate(TRAINING_DATE)
                .trainingDuration(DURATION)
                .build();
    }
}