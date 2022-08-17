import React, { useState, useEffect } from "react";
import ProjectDataService from "../../services/ProjectService";

const Project = props => {
  const initialProjectState = {
    id: null,
    title: "",
    description: "",
    startDate: "",
    endDate: ""
  };
  const [currentProject, setCurrentProject] = useState(initialProjectState);
  const [message, setMessage] = useState("");

  const getProject = id => {
    ProjectDataService.get(id)
      .then(response => {
        console.log(response.data);
        setCurrentProject(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  useEffect(() => {
    getProject(props.match.params.id);
  }, [props.match.params.id]);

  const handleInputChange = event => {
    const { name, value } = event.target;
    setCurrentProject({ ...currentProject});
  };

  const updateProject = () => {
    ProjectDataService.update(currentProject.id, currentProject)
      .then(response => {
        console.log(response.data);
        setMessage("The project was updated successfully!");
      })
      .catch(e => {
        console.log(e);
      });
  };

  const deleteProject = () => {
    ProjectDataService.remove(currentProject.id)
      .then(response => {
        console.log(response.data);
        props.history.push("/projects");
      })
      .catch(e => {
        console.log(e);
      });
  };

  return (
    <div>
      {currentProject ? (
        <div className="edit-form">
          <h4>Project</h4>
          <form>
            <div className="form-group">
              <label htmlFor="title">Title</label>
              <input
                type="text"
                className="form-control"
                id="title"
                name="title"
                value={currentProject.title}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="description">Description</label>
              <input
                type="text"
                className="form-control"
                id="description"
                name="description"
                value={currentProject.description}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="startDate">Start Date</label>
              <input
                type="text"
                className="form-control"
                id="startDate"
                name="startDate"
                value={currentProject.startDate}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="endDate">End Date</label>
              <input
                type="text"
                className="form-control"
                id="endDate"
                name="endDate"
                value={currentProject.endDate}
                onChange={handleInputChange}
              />
            </div>
          </form>

          <button className="btn btn-danger" onClick={deleteProject}>
            Delete
          </button>

          <button type="submit" className="btn btn-success" onClick={updateProject}>
            Update
          </button>
          
          <p>{message}</p>
        </div>
      ) : (
        <div>
          <br />
          <p>Please click on a Project...</p>
        </div>
      )}
    </div>
  );
};

export default Project;