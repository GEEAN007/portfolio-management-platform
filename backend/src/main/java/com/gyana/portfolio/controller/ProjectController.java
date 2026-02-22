package com.gyana.portfolio.controller;

import com.gyana.portfolio.entity.Project;
import com.gyana.portfolio.entity.User;
import com.gyana.portfolio.repository.ProjectRepository;
import com.gyana.portfolio.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{userId}")
    public Project createProject(@PathVariable Long userId, @RequestBody Project project) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        project.setUser(user);
        return projectRepository.save(project);
    }

    @GetMapping("/user/{userId}")
    public List<Project> getProjectsByUser(@PathVariable Long userId) {
        return projectRepository.findByUserId(userId);
    }

    @DeleteMapping("/{id}")
public void deleteProject(@PathVariable Long id) {
    projectRepository.deleteById(id);
   }

@PutMapping("/{id}")
public Project updateProject(@PathVariable Long id, @RequestBody Project updatedProject) {

    Project project = projectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found"));

    project.setTitle(updatedProject.getTitle());
    project.setDescription(updatedProject.getDescription());
    project.setTechStack(updatedProject.getTechStack());

    return projectRepository.save(project);
    }


}